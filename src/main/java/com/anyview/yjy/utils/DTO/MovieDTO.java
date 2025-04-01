package com.anyview.yjy.utils.DTO;

import java.time.LocalDateTime;

public class MovieDTO {
    private Long id;
    private Long hall;
    private LocalDateTime showTime;
    private LocalDateTime endTime;
    private Integer price;

    public MovieDTO() {
    }

    public MovieDTO(Long id, Long hall, LocalDateTime showTime, LocalDateTime endTime, Integer price) {
        this.id = id;
        this.hall = hall;
        this.showTime = showTime;
        this.endTime = endTime;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHall() {
        return hall;
    }

    public void setHall(Long hall) {
        this.hall = hall;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String toString() {
        return "MovieDTO{id = " + id + ", hall = " + hall + ", showTime = " + showTime + ", endTime = " + endTime + ", price = " + price + "}";
    }
}
