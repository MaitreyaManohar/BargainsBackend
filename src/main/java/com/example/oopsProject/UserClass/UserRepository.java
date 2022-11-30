package com.example.oopsProject.UserClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserClass,Long> {
    List<Optional<UserClass>> findByName(String name); //@Query(SELECT s FROM users_class WHERE s.name = ?1)

    Optional<UserClass> findById(long id);
    Optional<UserClass> findByEmail(String email);
}
