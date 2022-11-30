package com.example.oopsProject.Cart;

import com.example.oopsProject.Items.ItemClass;
import com.example.oopsProject.UserClass.UserClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findById(long userid);

    Optional<Cart> findByitemClass(ItemClass itemClass);
    List<Optional<Cart>> findByuserClass(UserClass userClass);




}
