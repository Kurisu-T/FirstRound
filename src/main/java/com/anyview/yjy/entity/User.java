package com.anyview.yjy.entity;

public class User {
    private Long id;
    private String name;
    private String phone;
    private String password;
    private Integer status;

    public User() {
    }

    public User(Long id, String name, String phone, String password, Integer status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.status = status;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String toString() {
        return "{\"id\": " + id + ", \"name\": \"" + name + "\", \"phone\": \"" + phone +
                "\", \"password\": \"" + password + "\", \"status\": " + status + "}";
    }
}
