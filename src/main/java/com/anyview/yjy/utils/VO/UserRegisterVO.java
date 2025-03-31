package com.anyview.yjy.utils.VO;

public class UserRegisterVO {
    String name;
    String phone;

    public UserRegisterVO() {
    }

    public UserRegisterVO(String name, String phone) {
        this.name = name;
        this.phone = phone;
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

    public String toString() {
        return "{\"name\": \"" + name + "\", \"phone\": \"" + phone + "\"}";
    }
}
