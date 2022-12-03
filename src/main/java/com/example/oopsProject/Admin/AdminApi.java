package com.example.oopsProject.Admin;

import com.example.oopsProject.Orders.OrderClass;
import com.example.oopsProject.Orders.OrderService;
import com.example.oopsProject.OutputClasses.UserOutput;
import com.example.oopsProject.UserClass.UserService;
import com.example.oopsProject.UserClass.addUserClass;
import com.example.oopsProject.UserClass.removeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminApi {
    private final ReportServiceLayer reportServiceLayer;
    private final UserService userService;
    private final OrderService orderService;
    @Autowired
    public AdminApi(ReportServiceLayer reportServiceLayer, UserService userService, OrderService orderService) {
        this.reportServiceLayer = reportServiceLayer;
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping("/getusers")
    public List<UserOutput> getUsers(@RequestBody addUserClass addUserClass){
        return userService.getUsers(addUserClass.getId());
    }
    @PostMapping(path = "/getuser")
    public UserOutput getUser(@RequestBody getOrdersClass getOrdersClass){
        return userService.adminGetUser(getOrdersClass.getId(), getOrdersClass.getRequesterId());
    }
    @PostMapping("/getnonapprovemanagers")
    public List<UserOutput> nonapproveManagers(@RequestBody getOrdersClass getOrdersClass){
        return userService.approveManagers(getOrdersClass.getRequesterId());
    }


    @PostMapping("/approvemanager")
    public ResponseEntity<?> approveManager(@RequestBody getOrdersClass getOrdersClass){
        return userService.approveManager(getOrdersClass.getId(),getOrdersClass.getRequesterId());
    }

    @DeleteMapping("/removeuser")
    public ResponseEntity<?> removeUser(@RequestBody removeUser removeUser){
        return userService.removeUser(removeUser.getSenderid(),removeUser.getUserid());
    }

    @PostMapping("/getapprovedmanagers")
    public List<UserOutput> approveManagers(@RequestBody addUserClass addUserClass){
        return userService.approvedManagers(addUserClass.getId());
    }

    @PostMapping("/itemsoldat")
    public List<Optional<OrderClass>> getItemSoldAtReport(@RequestBody getOrdersClass getOrdersClass){


        return reportServiceLayer.getOrdersByDate(getOrdersClass.getDate(),getOrdersClass.getRequesterId());

    }
    @PostMapping("/customerhistory")
    public List<Optional<OrderClass>> getCustomerHistory(@RequestBody getOrdersClass getOrdersClass){
        LocalDate lastDate = getOrdersClass.getDate().plusDays(30);


        return reportServiceLayer.getCustomerHistory(getOrdersClass.getId(),lastDate);

    }
    @PostMapping("/itemstatus")
    public List<ItemStatus> getItemStatus(@RequestBody getOrdersClass getOrdersClass){

        return reportServiceLayer.getItemStatus(getOrdersClass.getRequesterId());

    }
}
