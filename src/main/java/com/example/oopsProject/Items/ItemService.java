package com.example.oopsProject.Items;

import com.example.oopsProject.OutputClasses.ProductOutput;
import com.example.oopsProject.OutputClasses.UserOutput;
import com.example.oopsProject.UserClass.Role;
import com.example.oopsProject.UserClass.UserClass;
import com.example.oopsProject.UserClass.UserRepository;
import com.example.oopsProject.UserClass.addItemClass;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    @Autowired
    public ItemService(ItemRepository itemRepository,UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public List<ProductOutput> getItems(long id) {
        UserClass user = userRepository.findById(id).get();
        if((user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.MANAGER)) && user.isLoggedin()){
        List<ProductOutput> productOutputs = new ArrayList<>();
        for(ItemClass item:itemRepository.findAll()){


            ProductOutput productOutput = new ProductOutput(item.getItemId(),item.getItemName(),item.isInProduction(),item.getQty(),item.getCategory()
                    ,item.getImage(),item.getPrice(), item.getDeliveryWithin(), item.getOffer(),item.getOfferValidTill(),item.getDateAdded());
            productOutputs.add(productOutput);
        }
        return productOutputs;}
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unauthorized Access!");
    }

    public ResponseEntity<?> addItem(addItemClass additem) {
        UserClass buyer = userRepository.findById(additem.getUser_id()).get();
        if(buyer.getRole().equals(Role.MANAGER) && buyer.isLoggedin()){
        ItemClass item = new ItemClass(additem.getItemName(),true
                ,additem.getQty(),additem.getCategory(), additem.getPrice(), additem.getDeliveryWithin(),
                additem.getOffer(),additem.getOfferValidTill(),additem.getDateAdded());
        item.setDateAdded(LocalDate.now());
        itemRepository.save(item);
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else return new ResponseEntity<>("UNAUTHORIZEDACCESS",HttpStatus.BAD_REQUEST);
    }

    public void changeProductionStatus(long itemId,boolean status) {
        ItemClass item = itemRepository.findById(itemId).get();
        item.setInProduction(status);
        itemRepository.save(item);
    }

    public void changePrice(long itemId, int price) {
        ItemClass itemClass = itemRepository.findById(itemId).get();
        itemClass.setPrice(price);
        itemRepository.save(itemClass);
    }

    public void increaseQty(long itemId, int qty) {
        ItemClass itemClass = itemRepository.findById(itemId).get();
        itemClass.setQty(itemClass.getQty()+qty);
        itemRepository.save(itemClass);
    }

    public void decreaseQty(long itemId, int qty) {
        ItemClass itemClass = itemRepository.findById(itemId).get();
        itemClass.setQty(itemClass.getQty()-qty);
        itemRepository.save(itemClass);
    }




    public ResponseEntity<?> modifyItem(ItemClass item) {
        itemRepository.save(item);
        return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
    }

    public ProductOutput getItem(long itemid) {
        ItemClass item = itemRepository.findById(itemid).get();

        ProductOutput productOutput = new ProductOutput(item.getItemId(),item.getItemName(),item.isInProduction(),item.getQty(),item.getCategory()
                ,item.getImage(),item.getPrice(), item.getDeliveryWithin(), item.getOffer(),item.getOfferValidTill(),item.getDateAdded());

        return productOutput;
    }

    @Transactional
    public List<ItemClass> search(String searchname) {
        List<ItemClass> listofitems = itemRepository.findAll();
        List<ItemClass> searcheditems = new ArrayList<ItemClass>();
        System.out.println(listofitems);

        for(ItemClass item : listofitems){
            System.out.println("This is the ratio: for "+item.getItemName()+searchname+FuzzySearch.weightedRatio(item.getItemName(),searchname));
            if(FuzzySearch.weightedRatio(item.getItemName(),searchname)>50){
                searcheditems.add(item);
            }

            Collections.sort(searcheditems,new SortById(searchname));

        }

        return searcheditems;
    }

    @Transactional
    public ResponseEntity<?> deleteItem(long userid, long productid) {
        if((userRepository.findById(userid).get().getRole().equals(Role.MANAGER) || userRepository.findById(userid).get().getRole().equals(Role.ADMIN)) && userRepository.findById(userid).get().isLoggedin()){
            ItemClass item = itemRepository.findById(productid).get();
            itemRepository.delete(item);
            return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("UNAUTHORIZED METHOD",HttpStatus.BAD_REQUEST);
        }
    }
}

class SortById implements Comparator<ItemClass> {
    private String searchname;

    public SortById(String searchname) {
        this.searchname = searchname;
    }

    // Used for sorting in ascending order of ID
    public int compare(ItemClass a, ItemClass b)
    {
        return FuzzySearch.weightedRatio(b.getItemName(),searchname) - FuzzySearch.weightedRatio(a.getItemName(),searchname);
    }
}
