package com.example.oopsProject.Admin;

import com.example.oopsProject.Items.ItemClass;
import com.example.oopsProject.Items.ItemRepository;
import com.example.oopsProject.Orders.OrderClass;
import com.example.oopsProject.Orders.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceLayer {
    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    @Autowired
    public ReportServiceLayer(OrderRepository orderRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }
    @Transactional
    public List<Optional<OrderClass>> getOrdersByDate(LocalDate date){
        return orderRepository.findBysoldAt(date);
    }

    @Transactional
    public List<Optional<OrderClass>> getCustomerHistory(long id, LocalDate lastDate) {
        return orderRepository.getCustomerHistory(id,lastDate);
    }

    public List<ItemStatus> getItemStatus() {
        List<ItemClass> list = itemRepository.findAll();
        List<ItemStatus> returnlist = new ArrayList<>();
        for(ItemClass itemClass: list){
            ItemStatus itemStatus = new ItemStatus(itemClass.getItemName(),itemClass.getQty());
            returnlist.add(itemStatus);
        }
        return returnlist;
    }
}
