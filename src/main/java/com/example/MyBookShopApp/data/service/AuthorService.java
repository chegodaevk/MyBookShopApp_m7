package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.struct.author.Author;
import com.example.MyBookShopApp.data.repository.AuthorRepository;
import com.example.MyBookShopApp.data.struct.book.Book;
import com.example.MyBookShopApp.data.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Map<String, List<Author>> getAuthorsMap() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().collect(Collectors.groupingBy((Author a) -> {return a.getLastName().substring(0,1);}));
    }

    public Author getAuthorById(Integer id){
        return authorRepository.findAuthorById(id);
    }

    public Page<Book> getBooksListByAuthorId(Integer id, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBooksByAuthor_Id(id, nextPage);
    }
}
