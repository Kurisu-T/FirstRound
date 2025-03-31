package com.anyview.yjy.entity;

import com.anyview.yjy.utils.TimeUtils.TimeJSON;

import java.sql.Time;
import java.time.LocalDateTime;

public class Orders {
    private Long id;
    private Long userId;
    private Long movie;
    private Long hall;
    private Long seat;
    private LocalDateTime createTime;
    private LocalDateTime showTime;
    private Long status;
    private Integer price;

    public Orders() {
    }

    public Orders(Long id, Long userId, Long movie, Long hall, Long seat, LocalDateTime createTime, LocalDateTime showTime, Long status, Integer price) {
        this.id = id;
        this.userId = userId;
        this.movie = movie;
        this.hall = hall;
        this.seat = seat;
        this.createTime = createTime;
        this.showTime = showTime;
        this.status = status;
        this.price = price;
    }

    public String toString() {
        return "{\"id\":" + getId() + ",\"userId\":" + getUserId() +
                ",\"movie\":" + getMovie() + ",\"hall\":" + getHall() +
                ",\"seat\":" + getSeat() + ",\"price\":" + price +
                ",\"showTime\":\"" + TimeJSON.timeToJSON(getShowTime()) +
                "\",\"createTime\":\"" + TimeJSON.timeToJSON(getCreateTime()) +
                "\",\"status\":\"" + getStatus() + "\"}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovie() {
        return movie;
    }

    public void setMovie(Long movie) {
        this.movie = movie;
    }

    public Long getHall() {
        return hall;
    }

    public void setHall(Long hall) {
        this.hall = hall;
    }

    public Long getSeat() {
        return seat;
    }

    public void setSeat(Long seat) {
        this.seat = seat;
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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
