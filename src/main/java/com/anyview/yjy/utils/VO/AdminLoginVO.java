package com.anyview.yjy.utils.VO;

public class AdminLoginVO {
    private Long id;
    private String name;

    public AdminLoginVO() {
    }

    public AdminLoginVO(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public String toString() {
        return "{\"id\": " + id + ", \"name\": \"" + name + "\"}";
    }
}
