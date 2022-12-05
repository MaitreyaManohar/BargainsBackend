package com.example.oopsProject.ProductSnapshot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSnapshotRepository extends JpaRepository<ProductSnapshot,Long> {
    List<Optional<ProductSnapshot>> findByItemId(long itemid);
}
