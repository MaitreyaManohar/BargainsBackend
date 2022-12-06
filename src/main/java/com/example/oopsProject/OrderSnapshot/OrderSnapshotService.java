package com.example.oopsProject.OrderSnapshot;

import com.example.oopsProject.Cart.CartRepository;
import com.example.oopsProject.Images.ImageData;
import com.example.oopsProject.Images.StorageRepository;
import com.example.oopsProject.Items.ItemClass;
import com.example.oopsProject.Items.ItemRepository;
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
import java.util.*;

@Service
public class OrderSnapshotService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final StorageRepository storageRepository;

    private final OrderSnapshotRepository orderSnapshotRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderSnapshotService(CartRepository cartRepository, ItemRepository itemRepository, StorageRepository storageRepository, OrderSnapshotRepository orderSnapshotRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.storageRepository = storageRepository;
        this.orderSnapshotRepository = orderSnapshotRepository;
        this.userRepository = userRepository;
    }

    public List<OrderSnapshot> getOrderSnapshots() {
        return orderSnapshotRepository.findAll();
    }

    @Transactional
    public List<OrderOutput> getPastOrders(long customerId) {
        UserClass user = userRepository.findById(customerId).get();
        if(user.isLoggedin() && user.getRole().equals(Role.CUSTOMER)){
            List<OrderOutput> orderOutputs = new ArrayList<>();
            System.out.println("YES");
            for(Optional<OrderSnapshot> optionalOrderClass : orderSnapshotRepository.findByBuyerid(customerId)){
                OrderSnapshot order = optionalOrderClass.get();
                OrderOutput orderOutput = new OrderOutput(order);
                ItemClass item = itemRepository.findById(order.getItem().getItemId()).get();
                Optional<ImageData> imageDataOptional = storageRepository.findByItem(item);
                if(imageDataOptional.isPresent()==false) {
                    orderOutput.setBuyer(new UserOutput(user));
                    orderOutputs.add(orderOutput);
                }
                else {
                    ImageData imageData = imageDataOptional.get();
                orderOutput.getItem().setImage(imageData);
                orderOutput.getItem().getImage().setImageData(imageData.getImageData());
                orderOutput.setBuyer(new UserOutput(user));
                orderOutputs.add(orderOutput);

                }

            }
            Collections.reverse(orderOutputs);
            return orderOutputs;}
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unauthorized!!");

    }
}
