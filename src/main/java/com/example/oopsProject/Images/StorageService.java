package com.example.oopsProject.Images;

import com.example.oopsProject.Items.ItemClass;
import com.example.oopsProject.Items.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.awt.*;
import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {
    private StorageRepository storageRepository;
    private ItemRepository itemRepository;

    @Autowired
    public StorageService(StorageRepository storageRepository, ItemRepository itemRepository) {
        this.storageRepository = storageRepository;
        this.itemRepository = itemRepository;
    }

    public String uploadImage(MultipartFile file, Long itemid) throws IOException {
        ItemClass item = itemRepository.findById(itemid).get();

        if(item.getImage()!=null) {
            ImageData imagecheck = storageRepository.findByItem(item).get();
            imagecheck.setImageData(file.getBytes());
            imagecheck.setName(file.getOriginalFilename());
            imagecheck.setType(file.getContentType());
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
        ImageData image = storageRepository.save(imagedata
        );
        itemRepository.save(item);

        if(image!=null){
            return "file successfully uploaded!" +file.getOriginalFilename();
        }
        return null;
        }

    }

    @Transactional
    public byte[] downloadImage(Long itemid){
        Optional<ImageData> image =  storageRepository.findByItem(itemRepository.findById(itemid).get());
        byte[] image_decompressed = ImageUtils.decompressImage(image.get().getImageData());
        return image_decompressed;
    }
}
