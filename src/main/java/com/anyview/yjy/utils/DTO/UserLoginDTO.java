package com.anyview.yjy.utils.DTO;

public class UserLoginDTO {
    private String phone;
    private String password;


    public UserLoginDTO() {
    }

    public UserLoginDTO(String phone, String password) {
        this.phone = phone;
        this.password = password;
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
        return "UserLoginDTO{phone = " + phone + ", password = " + password + "}";
    }
}
