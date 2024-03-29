package com.example.demo.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "orders")
public class Order {

    @Id//specifying primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto-generating the primary key
    @Column(name = "order_id", unique = true)
    private Long orderId;

    @Column(name = "delivery_means")
    private String deliveryMeans;

    @Column(name = "pay_channel", columnDefinition = "varchar(50) default 'card'")//specifying defaults
    private String payChannel;

    @Column(name = "pay_status", columnDefinition = "varchar(50) default 'pending'")
    private String payStatus;

    @Column(name = "delivery_fee")
    private BigDecimal deliveryFee;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "total")
    private BigDecimal total;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )//specifying the relationships btw the two tables

    @JoinColumn(
            name = "account_id",
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(
                    name = ""
            )
    )//join column properties
    private Account account;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )//specifying the relationships btw the two tables

    @JoinColumn(
            name = "meal_id",
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(
                    name = ""
            )
    )//join column properties
    private Meal meal;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )//specifying the relationships btw the two tables

    @JoinColumn(
            name = "rest_id",
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(
                    name = ""
            )
    )//join column properties
    private Restaurant restaurant;

    @Column(name = "trans_ref", nullable = false, length = 50, unique = true)
    private String transRef;

    @Column(name = "pay_ref_url", nullable = true, length = 250, unique = true)
    private String payRefUrl;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )//specifying the relationships btw the two tables

    @JoinColumn(
            name = "delivery_id",
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(
                    name = ""
            )
    )//join column properties
    private Delivery delivery;

    public Order() {

    }


    public Order(String deliveryMeans, String payStatus, BigDecimal deliveryFee, BigDecimal amount, BigDecimal total, String transRef, Account account, Meal meal, Restaurant restaurant,  Delivery delivery) {
        this.deliveryMeans = deliveryMeans;
        this.payStatus = payStatus;
        this.deliveryFee = deliveryFee;
        this.amount = amount;
        this.total = total;
        this.transRef = transRef;
        this.account = account;
        this.meal = meal;
        this.restaurant = restaurant;
        this.delivery = delivery;
    }

    public String getDeliveryMeans() {
        return deliveryMeans;
    }

    public void setDeliveryMeans(String deliveryMeans) {
        this.deliveryMeans = deliveryMeans;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getPayRefUrl() {
        return payRefUrl;
    }

    public void setPayRefUrl(String payRefUrl) {
        this.payRefUrl = payRefUrl;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }


    public String getTransRef() {
        return transRef;
    }

    public void setTransRef(String transRef) {
        this.transRef = transRef;
    }
}
