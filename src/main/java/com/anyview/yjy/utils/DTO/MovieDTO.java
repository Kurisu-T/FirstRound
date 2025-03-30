package com.anyview.yjy.utils.DTO;

import java.time.LocalDateTime;

public class MovieDTO {
    private Long id;
    private Long hall;
    private LocalDateTime showTime;


    public MovieDTO() {
    }

    public MovieDTO(Long id, Long hall, LocalDateTime showTime) {
        this.id = id;
        this.hall = hall;
        this.showTime = showTime;
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

    public String toString() {
        return "movieDTO{id = " + id + ", hall = " + hall + ", showTime = " + showTime + "}";
    }
}
