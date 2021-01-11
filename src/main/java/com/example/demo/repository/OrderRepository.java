package com.example.demo.repository;

import com.example.demo.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query("update orders r set r.payChannel = :pay_channel, r.payRefUrl = :payRefUrl, r.payStatus = :payStatus where r.transRef = :transRef")
    @Transactional/* using @transactional annotation to ensure data integrity after data manipulation */
    int updateOrderByPayment(@Param("pay_channel")String pay_channel, @Param("payRefUrl")String payRefUrl, @Param("payStatus")String payStatus, @Param("transRef")String transRef);

    @Query("from orders r where r.transRef = :transRef")
    Order getAllByAccount(@Param("transRef")String transRef);
}
