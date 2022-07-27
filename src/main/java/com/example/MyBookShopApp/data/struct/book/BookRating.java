package com.example.MyBookShopApp.data.struct.book;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "ratings")
public class BookRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number_grades")
    private Integer numberGrades;

    private  Integer rating;

    @Column(name = "five_stars")
    private Integer fiveStars;

    @Column(name = "four_stars")
    private Integer fourStars;

    @Column(name = "three_stars")
    private Integer threeStars;

    @Column(name = "two_stars")
    private Integer twoStars;

    @Column(name = "one_stars")
    private Integer oneStars;

    @OneToOne(mappedBy = "bookRating")
    @JsonIgnore
    private Book book;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberGrades() {
        return numberGrades;
    }

    public void setNumberGrades(Integer numberGrades) {
        this.numberGrades = numberGrades;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getFiveStars() {
        return fiveStars;
    }

    public void setFiveStars(Integer fiveStars) {
        this.fiveStars = fiveStars;
    }

    public Integer getFourStars() {
        return fourStars;
    }

    public void setFourStars(Integer fourStars) {
        this.fourStars = fourStars;
    }

    public Integer getThreeStars() {
        return threeStars;
    }

    public void setThreeStars(Integer threeStars) {
        this.threeStars = threeStars;
    }

    public Integer getTwoStars() {
        return twoStars;
    }

    public void setTwoStars(Integer twoStars) {
        this.twoStars = twoStars;
    }

    public Integer getOneStars() {
        return oneStars;
    }

    public void setOneStars(Integer oneStars) {
        this.oneStars = oneStars;
    }
}
