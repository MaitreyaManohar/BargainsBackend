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
    @Query("select o FROM OrderClass o WHERE o.soldAt < ?2 AND o.buyer.email=?1")
    List<Optional<OrderClass>> getCustomerHistory(String email, LocalDate lastDate);

    @Query("select o FROM OrderClass o WHERE month(o.soldAt) = ?2 AND o.buyer.email=?1 AND year(o.soldAt)=?3")
    List<Optional<OrderClass>> getCustomerHistoryForMonth(String email, int month,int year);
}
