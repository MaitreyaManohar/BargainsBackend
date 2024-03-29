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

    List<Optional<UserClass>> findByApproved(boolean b);

    List<Optional<UserClass>> findByApprovedAndRole(boolean b, Role manager);

    List<Optional<UserClass>> findByRole(Role customer);

    void deleteByEmail(String email);
}
