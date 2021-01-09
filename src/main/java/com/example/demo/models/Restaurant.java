package com.example.demo.models;

import javax.persistence.*;

@Entity(name = "restaurant")
public class Restaurant {

    @Id//specifying primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto-generating the primary key
    @Column(name="rest_id", unique = true)
    private Long restId;

    @Column(name="logo", nullable=false)
    private byte[] logo;

    @Column(name="city", nullable=false, length = 50)
    private String city;

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

    public Long getRestId() {
        return restId;
    }

    public void setRestId(Long restId) {
        this.restId = restId;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
