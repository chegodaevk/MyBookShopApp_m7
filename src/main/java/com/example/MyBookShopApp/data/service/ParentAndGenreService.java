package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.struct.book.Book;
import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.repository.GenreRepository;
import com.example.MyBookShopApp.data.repository.ParentRepository;
import com.example.MyBookShopApp.data.struct.genre.GenreEntity;
import com.example.MyBookShopApp.data.struct.genre.ParentGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;

@Service
public class ParentAndGenreService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final ParentRepository parentRepository;

    @Autowired
    public ParentAndGenreService(GenreRepository genreRepository, BookRepository bookRepository, ParentRepository parentRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.parentRepository = parentRepository;
    }

    public Page<Book> getBooksByGenre(String genre, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBooksByGenreEntity_Name(genre, nextPage);
    }

    // получение map c ключом ParentGenre и значение в виде списка объектов GenreEntity
    public HashMap<ParentGenre, List<GenreEntity>> getGenresMap(){
        HashMap<ParentGenre, List<GenreEntity>> genresMap = new HashMap<>();
        for (int i = 0; i < parentRepository.findAll().size(); i++){
            genresMap.put(parentRepository.findAll().get(i),
                    genreRepository.findGenreEntityByParentGenre_Id(parentRepository.findAll().get(i).getId()));
        }
        return genresMap;
    }

    public Page<Book> getBooksByParentGenre(String parentGenre, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBooksByGenreEntity_ParentGenre_ParentGenre(parentGenre, nextPage);
    }

    public List<Book> getBooksByParentGenreList(String parentGenre){
        return bookRepository.findBooksByGenreEntity_ParentGenre_ParentGenre(parentGenre);
    }

}
