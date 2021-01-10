package com.example.demo.repository;

import com.example.demo.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

    //using the query annotation to creating custom query for login
    @Query("select a from account a where a.name = :name")
    //creating a method in the interface
    Account getByName(@Param("name")String name);
}
