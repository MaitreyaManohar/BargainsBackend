package com.example.oopsProject.Admin;

import com.example.oopsProject.Orders.OrderClass;
import com.example.oopsProject.Orders.OrderService;
import com.example.oopsProject.OutputClasses.UserOutput;
import com.example.oopsProject.UserClass.Role;
import com.example.oopsProject.UserClass.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.rsocket.context.LocalRSocketServerPort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
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

    @PostMapping("/itemsoldat")
    public List<Optional<OrderClass>> getItemSoldAtReport(@RequestBody getOrdersClass getOrdersClass){
        UserOutput user = userService.getUser(getOrdersClass.getId());
        List<Optional<OrderClass>> list;
        if(user.getRole() == Role.ADMIN){
            return reportServiceLayer.getOrdersByDate(getOrdersClass.getDate());
        }
        return null;
    }
    @PostMapping("/customerhistory")
    public List<Optional<OrderClass>> getCustomerHistory(@RequestBody getOrdersClass getOrdersClass){
        LocalDate lastDate = getOrdersClass.getDate().plusDays(30);
        UserOutput user = userService.getUser(getOrdersClass.getRequesterId());
        if(user.getRole() == Role.CUSTOMER || user.getRole() == Role.ADMIN){
            return reportServiceLayer.getCustomerHistory(getOrdersClass.getId(),lastDate);
        }
        return null;
    }
    @PostMapping("/itemstatus")
    public List<ItemStatus> getItemStatus(@RequestBody getOrdersClass getOrdersClass){
        UserOutput user = userService.getUser(getOrdersClass.getRequesterId());
        if(user.getRole() == Role.ADMIN){
            return reportServiceLayer.getItemStatus();
        }
        return null;
    }
}
