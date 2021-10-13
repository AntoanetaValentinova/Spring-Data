package com.jsonprocessing.demo.repository;

import com.jsonprocessing.demo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u "+
    "WHERE (SELECT count(p) FROM Product p WHERE p.seller.id=u.id) > 0 "
    +"ORDER BY u.lastName,u.firstName")
    List<User> findAllUsersWithMoreThanOneSoldProductOrderByLastNameFirstName();

    @Query("SELECT u FROM User u "+
    "WHERE u.productsSold.size>0 "+
    "ORDER BY u.productsSold.size,u.lastName")
    List<User> findAllUsersWithMoreThanOneProductSoldOrderByProductsSoldAndLastName();
}
