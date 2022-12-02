package com.example.oopsProject.UserClass;


import com.example.oopsProject.Admin.getOrdersClass;
import com.example.oopsProject.Cart.Cart;
import com.example.oopsProject.Cart.CartService;
import com.example.oopsProject.Ewallet.EWalletService;
import com.example.oopsProject.Ewallet.Ewallet;
import com.example.oopsProject.Images.StorageService;
import com.example.oopsProject.Items.ItemClass;
import com.example.oopsProject.Items.ItemService;

import com.example.oopsProject.Mail.EmailDetails;
import com.example.oopsProject.Mail.EmailService;
import com.example.oopsProject.Orders.OrderService;
import com.example.oopsProject.OutputClasses.OrderOutput;
import com.example.oopsProject.OutputClasses.ProductOutput;
import com.example.oopsProject.OutputClasses.UserOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path = "/")
public class UserController {
    private final UserService userService;
    private final CartService cartService;

    private final ItemService itemService;
    private final OrderService orderService;

    private final EWalletService eWalletService;

    private final StorageService imageService;

    private final EmailService emailService;
    @Autowired
    public UserController(UserService userService, CartService cartService, ItemService itemService, OrderService orderService, EWalletService eWalletService, StorageService imageService, EmailService emailService) {
        this.eWalletService = eWalletService;
        this.orderService = orderService;
        this.userService = userService;
        this.cartService = cartService;
        this.itemService = itemService;
        this.imageService = imageService;
        this.emailService = emailService;
    }




    @PostMapping("/items")
    public List<ProductOutput> getItems(@RequestBody addUserClass addUserClass){
        return itemService.getItems(addUserClass.getId());
    }

    @PostMapping("/customer/buyitem")
    public ResponseEntity<?> buyItem(@RequestBody addToCartClass addToCartClass){
        return itemService.buyItem(addToCartClass.getUserid(), addToCartClass.getProductid(),addToCartClass.getQtybought());
    }


    @GetMapping("customer/getitem/{itemid}")
    public ProductOutput getItem(@PathVariable long itemid){
        return itemService.getItem(itemid);
    }


    @PostMapping("/getuserinfo")
    public UserOutput getUserInfo(@RequestBody getOrdersClass getOrdersClass){
        return userService.getuserinfo(getOrdersClass.getId());
    }

    @GetMapping(path = "customer/getcartbyid/{id}")
    public Optional<Cart> getcartByCartid(@PathVariable("id") long id){
        return cartService.getCartByCartid(id);
    }


    @GetMapping(path = "customer/getpastorders/{customerid}")
    public List<OrderOutput> getPastOrders(@PathVariable("customerid") long customerId){
        return orderService.getPastOrders(customerId);
    }

    @PostMapping(path = "customer/getcart")
    public List<Cart> getCartbyId(@RequestBody addUserClass addUserClass){
        return cartService.getCartById(addUserClass.getId());
    }




    @PostMapping(path = "/signup")
    public addUserClass addUser(@RequestBody addUserClass userClass){
        addUserClass l = userService.addUser(userClass);
        if(l!=null && userClass.getRole().equals(Role.CUSTOMER)){
            emailService.sendSimpleMail(new EmailDetails(userClass.getEmail(),"Your account for Bargains has been created successfully!","Bargains Account Created"));
        }
        else if(l!=null && userClass.getRole().equals(Role.MANAGER)) {
            emailService.sendSimpleMail(new EmailDetails(userClass.getEmail(),"Your application for Bargains manager is accepted and pending!","Bargains Application Pending"));
        }
        return l;
    }

    @PostMapping(path = "/modifyuser")
    public ResponseEntity<?> modifyUser(@RequestBody addUserClass userClass){
        return userService.modifyUser(userClass);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<?> logout(@RequestBody addUserClass addUserClass){
        return userService.logout(addUserClass.getId());
    }

    @PostMapping(path = "/customer/addtocart")
    public ResponseEntity<?> addToCart(@RequestBody addToCartClass addtocart){
        return cartService.addToCart(addtocart.getUserid(),addtocart.getProductid(),addtocart.getQtybought());
    }


    @PostMapping(path = "/login")
    public addUserClass login(@RequestBody LoginClass loginClass, HttpServletResponse response){
        return userService.login(loginClass.getEmail(),loginClass.getPassword());
//        cookie.setMaxAge(7 * 24 * 60 * 60);
////        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
//        response.addCookie(cookie);
////       request.getSession().setAttribute("HELLO","HELLOWORLD");
//        return new ResponseEntity<>("",HttpStatus.OK);

    }
    @PostMapping("manager/additem")
    public long addItem(@RequestBody addItemClass addItem){
        return itemService.addItem(addItem);
    }

    @GetMapping("item/{category}")
    public List<ProductOutput> getItemsByCategory(@PathVariable("category") Category category){
        System.out.println(category);
        return itemService.getItemsByCategory(category);
    }
    @PostMapping("/customer/buyfromcart")
    public ResponseEntity<?> buyFromCart(@RequestBody BuyFromCartClass userClass){
        System.out.println("Running the buyfromcartmethod");
        return cartService.buyFromCart(userClass.getUser_id());
    }

    @PostMapping("/deleteItem")
    public ResponseEntity<?> deleteItem(@RequestBody addToCartClass deleteClass){
        return itemService.deleteItem(deleteClass.getUserid(),deleteClass.getProductid());
    }

    @PatchMapping("/modifyitem")
    public ResponseEntity<?> modifyItem(@RequestBody ItemClass item){
        return itemService.modifyItem(item);
    }

    @PatchMapping(path = "customer/ewallet/topup")
    public ResponseEntity<?> topUp(@RequestBody TopUpClass topupclass){
        return eWalletService.topUp(topupclass);
    }


    @GetMapping(path = "/ewallet/getbalance")
    public Ewallet getBankBalance(@RequestBody UserClass userClass){
        return eWalletService.getBalance(userClass.getId());
    }
    @PostMapping("/manager/uploadimage")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file,@RequestParam("itemid") Long itemid,@RequestParam("requesterId") Long requesterId) throws IOException{
        System.out.println("THIS IS THE ITEMID"+itemid.getClass());
        String uploadImage = imageService.uploadImage(file,itemid,requesterId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }







    @PostMapping("/customer/deletefromcart")
    public ResponseEntity<?> deleteFromCart(@RequestBody addToCartClass addToCartClass){
        cartService.deleteFromCart(addToCartClass.getProductid(),addToCartClass.getUserid());
        return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
    }



    @GetMapping("/search/{searchname}")
    public List<ItemClass> search(@PathVariable("searchname") String searchname){
        return itemService.search(searchname);
    }

}
