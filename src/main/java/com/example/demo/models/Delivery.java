package com.example.demo.models;

import org.hibernate.annotations.Type;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.*;

@ConfigurationProperties(prefix = "deliveryfile")
@Entity(name = "delivery")
public class Delivery {

    @Id//specifying primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto-generating the primary key
    @Column(name="delivery_id", unique = true)
    private Long deliveryId;

    @Column(name="logo", nullable=false)
    private String logo;

    @Column(name="bike", nullable=false)
    private int bike;

    @Column(name="car", nullable=false)
    private int car;

    @Column(name="boat", nullable=false)
    private int boat;

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
            foreignKey = @ForeignKey(
                    name = ""
            )
    )//join column properties
    private Account account;

    @Column(name = "upload_dir", nullable = true)
    private String uploadDir;

    public Delivery(){

    }

    public Delivery(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Delivery(String logo, int bike, int car, int boat, String timeBoat, String timeCar, String timeBike, Account account) {
        this.logo = logo;
        this.bike = bike;
        this.car = car;
        this.boat = boat;
        this.timeBoat = timeBoat;
        this.timeCar = timeCar;
        this.timeBike = timeBike;
        this.account = account;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getLogo() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/delivery/")
                .path(logo)
                .toUriString();
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getBike() {
        return bike;
    }

    public void setBike(int bike) {
        this.bike = bike;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public int getBoat() {
        return boat;
    }

    public void setBoat(int boat) {
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

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
