package com.anyview.yjy.entity;

import com.anyview.yjy.utils.TimeUtils.TimeJSON;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Movie {
    private Long id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime showTime;
    private LocalDateTime endTime;
    private Long hall;
    private String description;
    private Integer price;
    private Integer amount;

    public Movie() {
    }

    public Movie(Long id, String name, LocalDateTime createTime, LocalDateTime showTime, LocalDateTime endTime, Long hall, String description, Integer price, Integer amount) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.showTime = showTime;
        this.endTime = endTime;
        this.hall = hall;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public String toString() {
        return "{\"id\": " + id + ", \"name\": \"" + name + "\", \"createTime\": " +
                (createTime == null ? null : "\"" + TimeJSON.timeToJSON(createTime) + "\"") +
                ", \"showTime\": " + (showTime == null ? null : "\"" + TimeJSON.timeToJSON(showTime) + "\"") +
                ", \"endTime\": " + (endTime == null ? null : "\"" + TimeJSON.timeToJSON(endTime) + "\"") +
                ", \"hall\": " + hall + ", \"price\": " + price + ", \"amount\": " + amount +
                ", \"description\": " + (description == null ? null : "\"" + description + "\"") + "}";
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

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
