package com.example.oopsProject.Images;

import com.example.oopsProject.Items.ItemClass;
import com.example.oopsProject.Items.ItemRepository;
import com.example.oopsProject.ProductSnapshot.ProductSnapshot;
import com.example.oopsProject.ProductSnapshot.ProductSnapshotRepository;
import com.example.oopsProject.UserClass.Role;
import com.example.oopsProject.UserClass.UserClass;
import com.example.oopsProject.UserClass.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StorageService {
    private StorageRepository storageRepository;
    private ItemRepository itemRepository;

    private UserRepository userRepository;

    private final ProductSnapshotRepository productSnapshotRepository;
    @Autowired
    public StorageService(StorageRepository storageRepository, ItemRepository itemRepository, UserRepository userRepository, ProductSnapshotRepository productSnapshotRepository) {
        this.storageRepository = storageRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.productSnapshotRepository = productSnapshotRepository;
    }

    public String uploadImage(MultipartFile file, Long itemid, Long requesterId) throws IOException {
        ItemClass item = itemRepository.findById(itemid).get();
        UserClass manager = userRepository.findById(requesterId).get();
        if(manager.isLoggedin() && (manager.getRole().equals(Role.MANAGER) || manager.getRole().equals(Role.ADMIN))){
        if(item.getImage()!=null) {
            ImageData imagecheck = storageRepository.findByItem(item).get();
            imagecheck.setImageData(file.getBytes());
            imagecheck.setName(file.getOriginalFilename());
            imagecheck.setType(file.getContentType());
            List<Optional<ProductSnapshot>> productSnapshotList = productSnapshotRepository.findByItemId(itemid);
            ProductSnapshot productSnapshot = productSnapshotList.get(productSnapshotList.size()-1).get();
            productSnapshot.setImageData(file.getBytes());
            productSnapshotRepository.save(productSnapshot);
            storageRepository.save(imagecheck);
            return "Successfully updated";
        }
        else{
        ImageData imagedata = new ImageData(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes(),
                itemRepository.findById(itemid).get()
        );
        item.setImage(imagedata);
            List<Optional<ProductSnapshot>> productSnapshotList = productSnapshotRepository.findByItemId(itemid);
            ProductSnapshot productSnapshot = productSnapshotList.get(productSnapshotList.size()-1).get();
            productSnapshot.setImageData(file.getBytes());
            productSnapshotRepository.save(productSnapshot);
        ImageData image = storageRepository.save(imagedata
        );
        itemRepository.save(item);

        if(image!=null){
            return "file successfully uploaded!" +file.getOriginalFilename();
        }
        else return null;
        }}
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"UnauthorizedToUpload");

    }

    @Transactional
    public byte[] downloadImage(Long itemid){
        Optional<ImageData> image =  storageRepository.findByItem(itemRepository.findById(itemid).get());
        byte[] image_decompressed = ImageUtils.decompressImage(image.get().getImageData());
        return image_decompressed;
    }
}
