package com.example.demo.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "delivery")
public class Delivery {

    @Id//specifying primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto-generating the primary key
    @Column(name="delivery_id", unique = true)
    private Long deliveryId;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name="logo", nullable=false)
    private byte[] logo;

    @Column(name="bike", nullable=false)
    private String bike;

    @Column(name="car", nullable=false)
    private String car;

    @Column(name="boat", nullable=false)
    private String boat;

    @Column(name="time_boat", nullable=false, length = 50)
    private String timeBoat;

    @Column(name="time_car", nullable=false, length = 50)
    private String timeCar;

    @Column(name="time_bike", nullable=false, length = 50)
    private String timeBike;

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

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getBike() {
        return bike;
    }

    public void setBike(String bike) {
        this.bike = bike;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getBoat() {
        return boat;
    }

    public void setBoat(String boat) {
        this.boat = boat;
    }

    public String getTimeBoat() {
        return timeBoat;
    }

    public void setTimeBoat(String timeBoat) {
        this.timeBoat = timeBoat;
    }

    public String getTimeCar() {
        return timeCar;
    }

    public void setTimeCar(String timeCar) {
        this.timeCar = timeCar;
    }

    public String getTimeBike() {
        return timeBike;
    }

    public void setTimeBike(String timeBike) {
        this.timeBike = timeBike;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
