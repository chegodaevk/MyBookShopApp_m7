package com.example.MyBookShopApp.data.struct.genre;

import com.example.MyBookShopApp.data.struct.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genres")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @JsonIgnore
    private ParentGenre parentGenre;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String slug;

    @Column(name = "quantity_books")
    private Integer quantityBooks;

    @OneToMany(mappedBy = "genreEntity")
    @JsonIgnore
    private List<Book> bookList = new ArrayList<>();

    public Integer getQuantityBooks() {
        return quantityBooks;
    }

    public void setQuantityBooks(Integer quantityBooks) {
        this.quantityBooks = quantityBooks;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ParentGenre getParentGenre() {
        return parentGenre;
    }

    public void setParentGenre(ParentGenre parentGenre) {
        this.parentGenre = parentGenre;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
