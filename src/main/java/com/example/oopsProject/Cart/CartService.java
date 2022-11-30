package com.example.oopsProject.Cart;

import com.example.oopsProject.Ewallet.EWalletRepository;
import com.example.oopsProject.Ewallet.Ewallet;
import com.example.oopsProject.Items.ItemClass;
import com.example.oopsProject.Items.ItemRepository;
import com.example.oopsProject.Mail.EmailDetails;
import com.example.oopsProject.Mail.EmailService;
import com.example.oopsProject.Orders.OrderClass;
import com.example.oopsProject.Orders.OrderRepository;
import com.example.oopsProject.UserClass.Role;
import com.example.oopsProject.UserClass.UserClass;
import com.example.oopsProject.UserClass.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService extends EmailService {

    CartRepository cartRepository;
    UserRepository userRepository;
    ItemRepository itemRepository;
    EWalletRepository eWalletRepository;

    OrderRepository orderRepository;
    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ItemRepository itemRepository,EWalletRepository eWalletRepository,OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.eWalletRepository = eWalletRepository;
        this.orderRepository = orderRepository;
    }



    @Transactional
    public ResponseEntity<?> addToCart(long customerid, long itemid, int qtybought){
        System.out.println(userRepository.findById(customerid).get().getRole());
        if(userRepository.findById(customerid).get().getRole().equals(Role.CUSTOMER)){
            System.out.println("NOT CUSTOMER");
        List<Optional<Cart>> users = cartRepository.findByuserClass(userRepository.findById(customerid).get());
        boolean itemIsAlreadyPresent = false;
        for(Optional<Cart> cart:users){
            if (cart.get().getItemClass().getItemId() == itemid){
                itemIsAlreadyPresent = true;
                cart.get().setQtybought(cart.get().getQtybought()+qtybought);
                cartRepository.save(cart.get());
                break;
            }
        }
        if(!itemIsAlreadyPresent){
            cartRepository.save(new Cart(qtybought,userRepository.findById(customerid).get(),itemRepository.findById(itemid).get()));
        }
        return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
        }
        else return new ResponseEntity<>("FAILURE",HttpStatus.BAD_REQUEST);

    }

    @Transactional
    public List<Cart> getCartById(long id) {
        if(userRepository.findById(id).get().getRole().equals(Role.CUSTOMER)){
        UserClass user = userRepository.findById(id).get();
        List<Cart> cartitems = new ArrayList<>();
        for(Optional<Cart> cart : cartRepository.findByuserClass(user)){
            cartitems.add(cart.get());
        }
        return cartitems;}
        else return null;
    }

    public Optional<Cart> getCartByCartid(long id){
        return cartRepository.findById(id);
    }
    @Transactional
    public ResponseEntity<?> buyFromCart(long customerid) {
        if(userRepository.findById(customerid).get().getRole().equals(Role.CUSTOMER)){
        List<Optional<Cart>> cartList = cartRepository.findByuserClass(userRepository.findById(customerid).get());
        System.out.println("Cart size is "+cartList.size());
        int total = 0;
        for (int i = 0;i<cartList.size();i++) {
            if (cartList.get(i).get().itemClass.getQty() > cartList.get(i).get().qtybought) {
                System.out.println("IT IS TRUE");
                total = total + cartList.get(i).get().itemClass.getPrice() * cartList.get(i).get().qtybought;

            }
            System.out.println("THIS IS I "+i);
        }
        System.out.println("THIS IS THE TOTAL "+total);

        Ewallet ewallet = eWalletRepository.findByowner(userRepository.findById(customerid).get()).get();
        if (ewallet.getBalance()>total) {
            System.out.println("AGAIN TRUE");
            ewallet.deduct(total);
            System.out.println("THIS IS THE EWALLET TOTAL "+ewallet.getBalance());
            for (int i = 0;i<cartList.size();i++) {
                if(cartList.get(i).get().itemClass.getQty()-cartList.get(i).get().qtybought>0){
                cartList.get(i).get().itemClass.setQty(cartList.get(i).get().itemClass.getQty() - cartList.get(i).get().qtybought);

                orderRepository.save(new OrderClass(cartList.get(i).get().userClass,cartList.get(i).get().itemClass, LocalDate.now(),cartList.get(i).get().qtybought));
                cartRepository.deleteById(cartList.get(i).get().cartid);
                sendSimpleMail(new EmailDetails(userRepository.findById(customerid).get().getEmail(),"ORDER SUCCESSFUL",null));
                return new ResponseEntity<>("SUCCESSFULLY Bought",HttpStatus.OK);
                }
                else return new ResponseEntity<>("Quantity Bought is more than stock", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else return new ResponseEntity<>("BAD REQUEST",HttpStatus.BAD_REQUEST);


    }

    @Transactional
    public void deleteFromCart(long productid, long userid) {
        for(Optional<Cart> cart: cartRepository.findByuserClass(userRepository.findById(userid).get())){
            if(cart.get().getItemClass().getItemId()==productid){
                System.out.println("YESSS");
                cartRepository.delete(cart.get());
            }
        }
    }
}
