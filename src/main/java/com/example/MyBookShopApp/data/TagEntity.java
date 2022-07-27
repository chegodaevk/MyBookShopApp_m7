package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.struct.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tag;

    @Column(name = "quantity_books_with_tag")
    private Integer quantityBooksWithTag;

    @Column(name = "css_class")
    private String cssClass;

    @OneToMany(mappedBy = "tagEntity")
    @JsonIgnore
    private List<Book> bookTagList = new ArrayList<>();

    public List<Book> getBookTagList() {
        return bookTagList;
    }

    public void setBookTagList(List<Book> bookTagList) {
        this.bookTagList = bookTagList;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getQuantityBooksWithTag() {
        return quantityBooksWithTag;
    }

    public void setQuantityBooksWithTag(Integer quantityBooksWithTag) {
        this.quantityBooksWithTag = quantityBooksWithTag;
    }
}
