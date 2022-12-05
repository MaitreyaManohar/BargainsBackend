package com.example.oopsProject.ProductSnapshot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSnapshotService {

    private final ProductSnapshotRepository productSnapshotRepository;

    @Autowired
    public ProductSnapshotService(ProductSnapshotRepository productSnapshotRepository) {
        this.productSnapshotRepository = productSnapshotRepository;
    }

    public List<Optional<ProductSnapshot>> getProductSnapshot(long itemid) {
        return productSnapshotRepository.findByItemId(itemid);
    }
}
