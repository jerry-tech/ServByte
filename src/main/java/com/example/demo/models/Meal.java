package com.example.demo.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@ConfigurationProperties(prefix = "mealfile")
@Entity(name = "meal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Meal {

    @Id//specifying primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto-generating the primary key
    @Column(name="meal_id", unique = true)
    private Long mealId;

    @Column(name="picture", nullable=false)
    private String picture;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="price", nullable=false)
    private BigDecimal price;

    @Column(name="time_taken", nullable=false, length=50)
    private String timeTaken;

    @Column(name="description", nullable=false)
    private String description;

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

    @Column(name = "upload_dir", nullable = true)
    private String uploadDir;

    public Meal(){

    }
    public Meal(Long mealId) {
        this.mealId = mealId;
    }

    public Meal(String name, String picture, BigDecimal price, String timeTaken, String description, Restaurant restaurant) {
        this.name = name;
        this.picture = picture;
        this.price = price;
        this.timeTaken = timeTaken;
        this.description = description;
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public String getPicture() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/meal/")
                .path(picture)
                .toUriString();
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
