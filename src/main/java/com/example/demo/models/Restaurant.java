package com.example.demo.models;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.*;

@ConfigurationProperties(prefix = "restlogo")
@Entity(name = "restaurant")
public class Restaurant {

    @Id//specifying primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto-generating the primary key
    @Column(name="rest_id", unique = true)
    private Long restId;

    @Column(name="logo", nullable=false)
    private String logo;

    @Column(name="city", nullable=false, length = 50)
    private String city;

    @Column(name = "upload_dir", nullable = true)
    private String uploadDir;

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

    public Restaurant(){

    }

    public Restaurant(Long restId){
        this.restId = restId;
    }

    public Restaurant(String logo, String city, Account account) {
        this.logo = logo;
        this.city = city;
        this.account = account;
    }

    public Long getRestId() {
        return restId;
    }

    public void setRestId(Long restId) {
        this.restId = restId;
    }

    public String getLogo() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/restaurants/")
                .path(logo)
                .toUriString();
    }

    public void setLogo(String logo) {
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

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
