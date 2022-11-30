package com.example.oopsProject.Images;

import com.example.oopsProject.Items.ItemClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<ImageData,Long> {
    Optional<ImageData> findByName(String filename);
    Optional<ImageData> findByItem(ItemClass item);
}
