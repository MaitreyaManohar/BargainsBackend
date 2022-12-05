package com.example.oopsProject.Admin;

import com.example.oopsProject.Orders.OrderClass;
import com.example.oopsProject.Orders.OrderService;
import com.example.oopsProject.OutputClasses.OrderOutput;
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
    public ResponseEntity<?> approveManager(@RequestBody removeUser removeUser){
        return userService.approveManager(removeUser.getEmail(),removeUser.getSenderid());
    }

    @PostMapping("/removeuser")
    public ResponseEntity<?> removeUser(@RequestBody removeUser removeUser){
        return userService.removeUser(removeUser.getSenderid(),removeUser.getEmail());
    }

    @PostMapping("/getcustomers")
    public List<UserOutput> getCustomers(@RequestBody getOrdersClass getOrdersClass){
        return userService.getCustomers(getOrdersClass.getRequesterId());
    }
    @PostMapping("/getapprovedmanagers")
    public List<UserOutput> approveManagers(@RequestBody getOrdersClass getOrdersClass){
        return userService.approvedManagers(getOrdersClass.getRequesterId());
    }

    @PostMapping("/itemsoldat")
    public List<OrderOutput> getItemSoldAtReport(@RequestBody getOrdersClass getOrdersClass){
        return reportServiceLayer.getOrdersByDate(getOrdersClass.getDate(),getOrdersClass.getRequesterId());

    }
    @PostMapping("/customerhistory")
    public List<OrderOutput> getCustomerHistory(@RequestBody getOrdersClass getOrdersClass){


        return reportServiceLayer.getCustomerHistory(getOrdersClass.getEmail(),getOrdersClass.getMonth(),getOrdersClass.getYear());

    }
    @PostMapping("/itemstatus")
    public List<ItemStatus> getItemStatus(@RequestBody getOrdersClass getOrdersClass){

        return reportServiceLayer.getItemStatus(getOrdersClass.getRequesterId());

    }
}
