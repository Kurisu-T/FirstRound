package com.anyview.yjy.entity;

import java.time.LocalDateTime;

public class Movie {
    private Long id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime showTime;
    private Long hall;
    private String description;

    public Movie() {
    }

    public Movie(Long id, String name, LocalDateTime createTime, LocalDateTime showTime, Long hall, String description) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.showTime = showTime;
        this.hall = hall;
        this.description = description;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public Long getHall() {
        return hall;
    }

    public void setHall(Long hall) {
        this.hall = hall;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "Movie{id = " + id + ", name = " + name + ", createTime = " + createTime + ", showTime = " + showTime + ", hall = " + hall + ", description = " + description + "}";
    }
}
