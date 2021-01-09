package com.example.demo.models;

import javax.persistence.*;

@Entity(name = "orders")
public class Order {

    @Id//specifying primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto-generating the primary key
    @Column(name="order_id", unique = true)
    private Long orderId;

    @Column(name="pay_channel", nullable=false, length = 50)
    private String payChannel;

    @Column(name="pay_status", nullable=false, length = 50)
    private String payStatus;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )//specifying the relationships btw the two tables

    @JoinColumn(
            name = "account_id",
            nullable = false,
            updatable = false,
            insertable= false,
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
            insertable= false,
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
            insertable= false,
            foreignKey = @ForeignKey(
                    name = ""
            )
    )//join column properties
    private Restaurant restaurant;

    @Column(name="pay_ref_url", nullable=false, length = 50)
    private String payRefUrl;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )//specifying the relationships btw the two tables

    @JoinColumn(
            name = "delivery_id",
            nullable = false,
            updatable = false,
            insertable= false,
            foreignKey = @ForeignKey(
                    name = ""
            )
    )//join column properties
    private Delivery delivery;


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
}
