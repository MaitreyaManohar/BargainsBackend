package com.example.oopsProject.Orders;

import com.example.oopsProject.Cart.CartRepository;
import com.example.oopsProject.Items.ItemRepository;
import com.example.oopsProject.OutputClasses.OrderOutput;
import com.example.oopsProject.UserClass.Role;
import com.example.oopsProject.UserClass.UserClass;
import com.example.oopsProject.UserClass.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    private final UserRepository userRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
    }


    @Transactional
    public List<OrderOutput> getPastOrders(long customerId) {
        UserClass user = userRepository.findById(customerId).get();
        if(user.isLoggedin() && user.getRole().equals(Role.CUSTOMER)){
            List<OrderOutput> orderOutputs = new ArrayList<>();
            System.out.println("YES");
            for(Optional<OrderClass> optionalOrderClass : orderRepository.findBybuyer(user)){
                OrderClass order = optionalOrderClass.get();
                OrderOutput orderOutput = new OrderOutput(order);
                orderOutputs.add(orderOutput);
            }
            return orderOutputs;}
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unauthorized!!");

    }
}
