package com.springdataautomapobj.demo.models.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class GameAddEditDto {
    private  String title;
    private BigDecimal price;
    private Double size;
    private String trailer;
    private String thumbnailURL;
    private String description;
    private String releaseDate;

    public GameAddEditDto() {
    }

    public GameAddEditDto(String title, BigDecimal price, Double size, String trailer, String thumbnailURL, String description, String releaseDate) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailer = trailer;
        this.thumbnailURL = thumbnailURL;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    @Pattern(regexp = "^[A-Z]{1}[\\w]{2,100}$",message = "Please,enter valid title!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DecimalMin(value="0",message = "Please,enter positive price!")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Positive(message = "Please,enter positive price!")
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Size(min=11,max=11,message = "Please enter valid trailer!")
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Pattern(regexp = "^[h]{1}[t]{1}[t]{1}[p]{1}[s]{0,1}[:]{1}[\\/\\/]+[\\w\\W]+$",message = "Please, enter valid URL!")
    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Size(min=20,message = "Description must be at leat 20 characters long!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


}
