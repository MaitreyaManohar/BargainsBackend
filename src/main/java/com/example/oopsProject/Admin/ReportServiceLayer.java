package com.example.oopsProject.Admin;

import com.example.oopsProject.Items.ItemClass;
import com.example.oopsProject.Items.ItemRepository;
import com.example.oopsProject.Orders.OrderClass;
import com.example.oopsProject.Orders.OrderRepository;
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
    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    @Autowired
    public ReportServiceLayer(OrderRepository orderRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public List<Optional<OrderClass>> getOrdersByDate(LocalDate date, long id){
        UserClass user = userRepository.findById(id).get();
        if(user.isLoggedin() && user.getRole().equals(Role.ADMIN)){
        return orderRepository.findBysoldAt(date);}
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unauthorized Access");
    }

    @Transactional
    public List<Optional<OrderClass>> getCustomerHistory(long id, LocalDate lastDate) {
        UserClass user = userRepository.findById(id).get();
        if((user.getRole().equals(Role.ADMIN)|| user.getRole().equals(Role.CUSTOMER)) && user.isLoggedin()){
        return orderRepository.getCustomerHistory(id,lastDate);}
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unauthorized");
    }

    @Transactional
    public List<ItemStatus> getItemStatus(long id) {
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
