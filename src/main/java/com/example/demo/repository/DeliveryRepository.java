package com.example.demo.repository;

import com.example.demo.models.Delivery;
import com.example.demo.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Query("from delivery r where r.account.name = :name")
    Delivery getDeliveryAccount(@Param("name")String name);

}
