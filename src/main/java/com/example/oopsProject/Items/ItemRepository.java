package com.example.oopsProject.Items;

import com.example.oopsProject.UserClass.Category;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemClass,Long> {
    List<Optional<ItemClass>> findByCategory(Category category);
//    List<Optional<ItemClass>> findBySeller(String seller);
}
