package com.example.oopsProject.Admin;

import com.example.oopsProject.Items.ItemClass;
import com.example.oopsProject.Items.ItemRepository;
import com.example.oopsProject.OrderSnapshot.OrderSnapshot;
import com.example.oopsProject.OrderSnapshot.OrderSnapshotRepository;
import com.example.oopsProject.OutputClasses.OrderOutput;
import com.example.oopsProject.OutputClasses.UserOutput;
import com.example.oopsProject.UserClass.Role;
import com.example.oopsProject.UserClass.UserClass;
import com.example.oopsProject.UserClass.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceLayer {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private final OrderSnapshotRepository orderSnapshotRepository;
    @Autowired
    public ReportServiceLayer(ItemRepository itemRepository, UserRepository userRepository, OrderSnapshotRepository orderSnapshotRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.orderSnapshotRepository = orderSnapshotRepository;
    }
    @Transactional
    public List<OrderOutput> getOrdersByDate(LocalDate date, long id){
        Optional<UserClass> optionalUserClass = userRepository.findById(id);
        if(optionalUserClass.isPresent()==false) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User does not Exist! ");
        UserClass user = optionalUserClass.get();
        List<OrderOutput> orderOutputs = new ArrayList<>();
        List<Optional<OrderSnapshot>> orders = orderSnapshotRepository.findBysoldAt(date);
        if(user.isLoggedin() && user.getRole().equals(Role.ADMIN)){
            for(Optional<OrderSnapshot> orderClassOptional: orders){
                OrderSnapshot order = orderClassOptional.get();
                OrderOutput neworder = new OrderOutput(order);
                Optional<UserClass> buyer = userRepository.findById(order.getBuyerid());
                if(buyer.isPresent()) neworder.setBuyer(new UserOutput(buyer.get()));
                orderOutputs.add(neworder);
            }
        return orderOutputs;}
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unauthorized Access");
    }

    @Transactional
    public List<OrderOutput> getCustomerHistory(String email, int month, int year) {
        Optional<UserClass> userClass = userRepository.findByEmail(email);
        if(userClass.isPresent()==false) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User does not exist!!");
        UserClass user = userClass.get();
        List<OrderOutput> orderOutputs = new ArrayList<>();
        if((user.getRole().equals(Role.ADMIN)|| user.getRole().equals(Role.CUSTOMER)) && user.isLoggedin()){
            for(Optional<OrderSnapshot> orderClassOptional : orderSnapshotRepository.getCustomerHistoryForMonth(user.getId(),month,year)){
                OrderSnapshot order = orderClassOptional.get();
                OrderOutput neworder = new OrderOutput(order);
                Optional<UserClass> buyer = userRepository.findById(order.getBuyerid());
                neworder.setBuyer(new UserOutput(buyer.get()));
                orderOutputs.add(neworder);
            }
            return orderOutputs;
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unauthorized");
    }

    @Transactional
    public List<ItemStatus> getItemStatus(long id) {
        Optional<UserClass> userClass = userRepository.findById(id);
        if(userClass.isPresent()==false) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User does not exist!!");
        UserClass user = userRepository.findById(id).get();
        if (user.isLoggedin() && user.getRole().equals(Role.ADMIN)) {
            List<ItemClass> list = itemRepository.findAll();
            List<ItemStatus> returnlist = new ArrayList<>();
            for (ItemClass itemClass : list) {
                ItemStatus itemStatus = new ItemStatus(itemClass.getItemName(), itemClass.getQty());
                returnlist.add(itemStatus);
            }
            return returnlist;
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unauthorized Access!");
    }
}
