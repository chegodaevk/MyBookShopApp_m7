package com.example.MyBookShopApp.data.struct.genre;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parents")
public class ParentGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "parent_genre")
    private String parentGenre;

    @Column(name = "quantity_books")
    private Integer quantityBooks;

    @OneToMany(mappedBy = "parentGenre")
    @JsonIgnore
    private List<GenreEntity> genreList = new ArrayList<>();

    public Integer getQuantityBooks() {
        return quantityBooks;
    }

    public void setQuantityBooks(Integer quantityBooks) {
        this.quantityBooks = quantityBooks;
    }

    public List<GenreEntity> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<GenreEntity> genreList) {
        this.genreList = genreList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParentGenre() {
        return parentGenre;
    }

    public void setParentGenre(String parentGenre) {
        this.parentGenre = parentGenre;
    }

}
