package com.example.oopsProject.Ewallet;

import com.example.oopsProject.UserClass.UserClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EWalletRepository extends JpaRepository<Ewallet,Long> {

    Optional<Ewallet> findByowner(UserClass user);
}
