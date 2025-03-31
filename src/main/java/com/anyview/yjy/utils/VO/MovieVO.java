package com.anyview.yjy.utils.VO;

import com.anyview.yjy.utils.TimeUtils.TimeJSON;

import java.time.LocalDateTime;

public class MovieVO {
    private Long id;
    private String name;
    private LocalDateTime showTime;
    private Long hall;
    private String description;
    private Integer price;

    public MovieVO() {
    }

    public MovieVO(Long id, String name, LocalDateTime showTime, Long hall, String description, Integer price) {
        this.id = id;
        this.name = name;
        this.showTime = showTime;
        this.hall = hall;
        this.description = description;
        this.price = price;
    }

    public String toString() {
        return "{\"id\":" + getId() + ",\"name\":\"" + getName() +
                "\",\"showTime\":\"" + TimeJSON.timeToJSON(getShowTime()) +
                "\", \"hall\":" + getHall() + ",\"description\":\"" + getDescription() + "\", " +
                "\"price\":" + price + "}";
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
}
