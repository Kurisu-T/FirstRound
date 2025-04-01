package com.anyview.yjy.utils.VO;

import com.anyview.yjy.utils.TimeUtils.TimeJSON;

import java.time.LocalDateTime;

public class MovieVO {
    private Long id;
    private String name;
    private LocalDateTime showTime;
    private LocalDateTime endTime;
    private Long hall;
    private String description;
    private Integer price;
    private Integer amount;

    public MovieVO() {
    }

    public MovieVO(Long id, String name, LocalDateTime showTime, LocalDateTime endTime, Long hall, String description, Integer price, Integer amount) {
        this.id = id;
        this.name = name;
        this.showTime = showTime;
        this.endTime = endTime;
        this.hall = hall;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public String toString() {
        return "{\"id\":" + getId() + ",\"name\":\"" + getName() +
                "\",\"showTime\":\"" + TimeJSON.timeToJSON(getShowTime()) +
                "\",\"endTime\":\"" + TimeJSON.timeToJSON(getEndTime()) +
                "\", \"hall\":" + getHall() + ",\"description\":\"" + getDescription() + "\", " +
                "\"price\":" + price + ", \"amount\":" + amount + "}";
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
