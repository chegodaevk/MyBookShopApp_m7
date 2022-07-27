package com.example.MyBookShopApp.data.struct.book.review;

import com.example.MyBookShopApp.data.struct.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "book_review")
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

//    @Column(columnDefinition = "INT NOT NULL", name = "user_id")
    @Column(name = "user_id")
    private Integer userId;

//    @Column(columnDefinition = "DATETIME  NOT NULL")
    private LocalDateTime time;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String text;

    @JsonIgnore
    private Integer rating;

    @OneToMany(mappedBy = "bookReview")
    @JsonIgnore
    private List<BookReviewLike> bookReviewLikeList;

    public List<BookReviewLike> getBookReviewLikeList() {
        return bookReviewLikeList;
    }

    public void setBookReviewLikeList(List<BookReviewLike> bookReviewLikeList) {
        this.bookReviewLikeList = bookReviewLikeList;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getConvertedTime(){
        return time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
