package com.example.oopsProject.Items;

import com.example.oopsProject.Ewallet.EWalletRepository;
import com.example.oopsProject.Ewallet.Ewallet;
import com.example.oopsProject.Mail.EmailDetails;
import com.example.oopsProject.Mail.EmailService;
import com.example.oopsProject.OrderSnapshot.OrderSnapshot;
import com.example.oopsProject.OrderSnapshot.OrderSnapshotRepository;
import com.example.oopsProject.OutputClasses.ProductOutput;
import com.example.oopsProject.ProductSnapshot.ProductSnapshot;
import com.example.oopsProject.ProductSnapshot.ProductSnapshotRepository;
import com.example.oopsProject.UserClass.*;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class ItemService extends EmailService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private final ProductSnapshotRepository productSnapshotRepository;
    private final EWalletRepository eWalletRepository;
    private final OrderSnapshotRepository orderSnapshotRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, UserRepository userRepository, EWalletRepository eWalletRepository, ProductSnapshotRepository productSnapshotRepository, OrderSnapshotRepository orderSnapshotRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.eWalletRepository = eWalletRepository;
        this.productSnapshotRepository = productSnapshotRepository;
        this.orderSnapshotRepository = orderSnapshotRepository;
    }

    public List<ProductOutput> getItems(long id) {
        UserClass user = userRepository.findById(id).get();
        if((user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.MANAGER)) && user.isLoggedin()){
        List<ProductOutput> productOutputs = new ArrayList<>();
        for(ItemClass item:itemRepository.findAll()){


            ProductOutput productOutput = new ProductOutput(item.getItemId(),item.getItemName(),item.isInProduction(),item.getQty(),item.getCategory()
                    ,item.getImage(),item.getPrice(), item.getDeliveryWithin(), item.getOffer(),item.getOfferValidTill(),item.getDateAdded(),item.getDescription());
            productOutputs.add(productOutput);
        }
        return productOutputs;}
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unauthorized Access!");
    }

    public long addItem(addItemClass additem) {
        UserClass buyer = userRepository.findById(additem.getUser_id()).get();
        if((buyer.getRole().equals(Role.MANAGER) || buyer.getRole().equals(Role.ADMIN)) && buyer.isLoggedin()){
        ItemClass item = new ItemClass(additem.getItemName(),true
                ,additem.getQty(),additem.getCategory(), additem.getPrice(), additem.getDeliveryWithin(),
                additem.getOffer(),additem.getOfferValidTill(),additem.getDateAdded(),additem.getDescription());
        item.setDateAdded(LocalDate.now());

        itemRepository.save(item);
        productSnapshotRepository.save(new ProductSnapshot(item));
            return item.getItemId();
        }
        else throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"UNAUTHORIZEDACCESS");
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




    public ResponseEntity<?> modifyItem(modifyItemClass addItemClass) {
        ItemClass itemfound = itemRepository.findById(addItemClass.getItemId()).get();
        UserClass userClass = userRepository.findById(addItemClass.getUser_id()).get();
        if((userClass.getRole().equals(Role.MANAGER) || userClass.getRole().equals(Role.ADMIN)) && userClass.isLoggedin()){
        if(itemfound!=null){
            itemfound.setQty(addItemClass.getQty());
            itemfound.setPrice(addItemClass.getPrice());
            itemfound.setItemName(addItemClass.getItemName());
            itemfound.setOffer(addItemClass.getOffer());
            itemfound.setDescription(addItemClass.getDescription());
            itemfound.setCategory(addItemClass.getCategory());
            productSnapshotRepository.save(new ProductSnapshot(itemfound));
            itemRepository.save(itemfound);
            return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
        }
        else return new ResponseEntity<>("Product already exists",HttpStatus.BAD_REQUEST);}
        else return new ResponseEntity<>("Unauthorized Access",HttpStatus.BAD_REQUEST);
    }

    public ProductOutput getItem(long itemid) {
        ItemClass item = itemRepository.findById(itemid).get();

        ProductOutput productOutput = new ProductOutput(item.getItemId(),item.getItemName(),item.isInProduction(),item.getQty(),item.getCategory()
                ,item.getImage(),item.getPrice(), item.getDeliveryWithin(), item.getOffer(),item.getOfferValidTill(),item.getDateAdded(),item.getDescription());

        return productOutput;
    }

    @Transactional
    public List<ItemClass> search(String searchname) {
        List<ItemClass> listofitems = itemRepository.findAll();
        List<ItemClass> searcheditems = new ArrayList<ItemClass>();
        System.out.println(listofitems);

        for(ItemClass item : listofitems){
            System.out.println("This is the ratio: for "+item.getItemName()+searchname+FuzzySearch.weightedRatio(item.getItemName(),searchname));
            if(FuzzySearch.weightedRatio(item.getItemName(),searchname)>50 || FuzzySearch.weightedRatio(item.getDescription(),searchname)>50){
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

    public ResponseEntity<?> buyItem(long userid, long productid, int qtybought) {
        UserClass user = userRepository.findById(userid).get();
        ItemClass item = itemRepository.findById(productid).get();
        Ewallet eWallet = eWalletRepository.findByowner(user).get();
        List<Optional<UserClass>> admin = userRepository.findByRole(Role.ADMIN);
        System.out.println("THIS IS QTY BOUGHt");
        if(user.getRole().equals(Role.CUSTOMER) && user.isLoggedin()){
            if(user.getEwallet().getBalance()>=qtybought*item.priceWithOffer() && item.getQty()>=qtybought){
                eWallet.setBalance(eWallet.getBalance()-qtybought*item.priceWithOffer());
                if(admin.isEmpty() == false && admin.get(0).isPresent()) {Ewallet adminEwallet = eWalletRepository.findByowner(admin.get(0).get()).get();
                    adminEwallet.setBalance(adminEwallet.getBalance()+qtybought*item.priceWithOffer());
                    eWalletRepository.save(adminEwallet);
                }
                eWalletRepository.save(eWallet);
                user.setEwallet(eWallet);
                item.setQty(item.getQty()-qtybought);
                itemRepository.save(item);
                userRepository.save(user);
                OrderSnapshot orderSnapshot = new OrderSnapshot();
                orderSnapshot.setBuyerid(user.getId());
                orderSnapshot.setQtybought(qtybought);
                orderSnapshot.setSoldAt(LocalDate.now());
                List<Optional<ProductSnapshot>> productSnapshot = productSnapshotRepository.findByItemId(productid);
                orderSnapshot.setItem(productSnapshot.get(productSnapshot.size()-1).get());
                orderSnapshotRepository.save(orderSnapshot);
                sendSimpleMail(new EmailDetails(user.getEmail(),"Dear "+user.getName()+",\n\nYour order for has been placed successfully.\n" +
                        qtybought +" x "+item.getItemName()+
                        "\n\nRegards,\nTeam Bargains.","Bargains Order has been placed!"));
                return new ResponseEntity<>("Successfully Bought!",HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Insufficient Balance",HttpStatus.BAD_REQUEST);

            }
        }
        else return new ResponseEntity<>("Unauthorized Request",HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public List<ProductOutput> getItemsByCategory(Category category) {
        try{
            System.out.println(category.equals(Category.GROCERIES));
            List<Optional<ItemClass>> items = itemRepository.findByCategory(category);
            List<ProductOutput> productOutputs = new ArrayList<>();
            for(Optional<ItemClass> itemClass : items){
                productOutputs.add(new ProductOutput(itemClass.get()));
            }
            return productOutputs;
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Fatal Error!");
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
        return FuzzySearch.weightedRatio(b.getItemName(),searchname)+FuzzySearch.weightedRatio(b.getDescription(),searchname) - FuzzySearch.weightedRatio(a.getItemName(),searchname)-FuzzySearch.weightedRatio(a.getDescription(),searchname);
    }
}
