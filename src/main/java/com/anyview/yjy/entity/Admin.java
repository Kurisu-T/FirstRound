package com.anyview.yjy.entity;

public class Admin {
    private Long id;
    private String name;
    private String phone;
    private String password;


    public Admin() {
    }

    public Admin(Long id, String name, String phone, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "{\"id\": " + id + ", \"name\": \"" + name + "\", \"phone\": \"" + phone +
                "\", \"password\": \"" + password + "\"}";
    }
}
