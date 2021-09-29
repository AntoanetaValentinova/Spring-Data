package com.springdataautomapobj.demo.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="games")
public class Game extends BaseEntity{
    private String title;
    private String trailer;
    private BigDecimal price;
    private String imageThumbnail;
    private Double size;
    private String description;
    private LocalDate releaseDate;

    public Game() {
    }

    public Game(String title, String trailer, BigDecimal price, String imageThumbnail, Double size, String description, LocalDate releaseDate) {
        this.title = title;
        this.trailer = trailer;
        this.price = price;
        this.imageThumbnail = imageThumbnail;
        this.size = size;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    @Column
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Column(name="image_thumbnail")
    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    @Column
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="release_date")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
