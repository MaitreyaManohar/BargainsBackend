package com.example.oopsProject.Orders;

import com.example.oopsProject.UserClass.UserClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderClass,Long> {
    List<Optional<OrderClass>> findBybuyer(UserClass user);

    List<Optional<OrderClass>> findBysoldAt(LocalDate date);
    @Query("select o FROM OrderClass o WHERE o.soldAt < ?2 AND o.buyer.id=?1")
    List<Optional<OrderClass>> getCustomerHistory(long id, LocalDate lastDate);
}
