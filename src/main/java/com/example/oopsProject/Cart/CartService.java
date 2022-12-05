package com.example.oopsProject.Cart;

import com.example.oopsProject.Ewallet.EWalletRepository;
import com.example.oopsProject.Ewallet.Ewallet;
import com.example.oopsProject.Items.ItemRepository;
import com.example.oopsProject.Mail.EmailDetails;
import com.example.oopsProject.Mail.EmailService;
import com.example.oopsProject.OrderSnapshot.OrderSnapshot;
import com.example.oopsProject.OrderSnapshot.OrderSnapshotRepository;
import com.example.oopsProject.ProductSnapshot.ProductSnapshot;
import com.example.oopsProject.ProductSnapshot.ProductSnapshotRepository;
import com.example.oopsProject.UserClass.Role;
import com.example.oopsProject.UserClass.UserClass;
import com.example.oopsProject.UserClass.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    private final ProductSnapshotRepository productSnapshotRepository;
    private final OrderSnapshotRepository orderSnapshotRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ItemRepository itemRepository, EWalletRepository eWalletRepository, ProductSnapshotRepository productSnapshotRepository, OrderSnapshotRepository orderSnapshotRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.eWalletRepository = eWalletRepository;
        this.productSnapshotRepository = productSnapshotRepository;
        this.orderSnapshotRepository = orderSnapshotRepository;
    }



    @Transactional
    public ResponseEntity<?> addToCart(long customerid, long itemid, int qtybought){
        System.out.println(userRepository.findById(customerid).get().getRole());
        if(userRepository.findById(customerid).get().getRole().equals(Role.CUSTOMER) && userRepository.findById(customerid).get().isLoggedin()){
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
        if(userRepository.findById(id).get().getRole().equals(Role.CUSTOMER) && userRepository.findById(id).get().isLoggedin()){
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
        Optional<UserClass> userClass = userRepository.findById(customerid);
        if(userClass.get().getRole().equals(Role.CUSTOMER) && userClass.get().isLoggedin()){

            List<Optional<Cart>> cartList = cartRepository.findByuserClass(userClass.get());
        System.out.println("Cart size is "+cartList.size());
        int total = 0;
        for (int i = 0;i<cartList.size();i++) {
            if (cartList.get(i).get().getItemClass().getQty() > cartList.get(i).get().qtybought) {
                total = total + cartList.get(i).get().itemClass.priceWithOffer() * cartList.get(i).get().qtybought;

            }
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Quantity chosen is more than the stock!");
        }

        Ewallet ewallet = eWalletRepository.findByowner(userRepository.findById(customerid).get()).get();
        if (ewallet.getBalance()>total) {
            ewallet.deduct(total);
            for (int i = 0;i<cartList.size();i++) {
                if(cartList.get(i).get().itemClass.getQty()-cartList.get(i).get().qtybought>0){
                cartList.get(i).get().itemClass.setQty(cartList.get(i).get().itemClass.getQty() - cartList.get(i).get().qtybought);
//                orderRepository.save(new OrderClass(cartList.get(i).get().userClass,cartList.get(i).get().itemClass, LocalDate.now(),cartList.get(i).get().qtybought));

                    OrderSnapshot orderSnapshot = new OrderSnapshot(cartList.get(i).get());
                    List<Optional<ProductSnapshot>> productSnapshot = productSnapshotRepository.findByItemId(cartList.get(i).get().getItemClass().getItemId());
                    orderSnapshot.setItem(productSnapshot.get(productSnapshot.size()-1).get());
                    orderSnapshotRepository.save(orderSnapshot);
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
