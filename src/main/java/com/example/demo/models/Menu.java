package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity(name = "menu")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Menu {

    @Id//specifying primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto-generating the primary key
    @Column(name="menu_id", unique = true)
    private Long menuId;

    @Column(name="menu_name", length = 50)
    private String menu_name;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }
}
