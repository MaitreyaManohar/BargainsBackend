package com.example.oopsProject.OrderSnapshot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderSnapshotRepository extends JpaRepository<OrderSnapshot,Long> {
    List<Optional<OrderSnapshot>> findByBuyerid(long id);
    List<Optional<OrderSnapshot>> findBysoldAt(LocalDate date);

    @Query("select o FROM OrderSnapshot o WHERE month(o.soldAt) = ?2 AND o.buyerid=?1 AND year(o.soldAt)=?3")
    List<Optional<OrderSnapshot>> getCustomerHistoryForMonth(long id, int month,int year);
}
