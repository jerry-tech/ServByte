package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "meal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Meal {

    @Id//specifying primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto-generating the primary key
    @Column(name="meal_id", unique = true)
    private Long mealId;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name="picture", nullable=false)
    private byte[] picture;

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
            insertable= false,
            foreignKey = @ForeignKey(
                    name = ""
            )
    )//join column properties
    private Restaurant resturant;

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
    private Menu menu;

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
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

    public Restaurant getResturant() {
        return resturant;
    }

    public void setResturant(Restaurant resturant) {
        this.resturant = resturant;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
