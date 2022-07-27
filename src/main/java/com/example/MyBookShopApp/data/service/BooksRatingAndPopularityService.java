package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.struct.book.Book;
import com.example.MyBookShopApp.data.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksRatingAndPopularityService {

    private BookRepository bookRepository;

    @Autowired
    public BooksRatingAndPopularityService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> getPopularityBooksByRating (Integer offset, Integer limit){
        List<Book> bookList = bookRepository.findAll();
        for (Book book : bookList) {
            int b = book.getQuantityBoughtBooks();
            int c = book.getQuantityBooksInCart();
            int k = book.getQuantityPostponedBook();
            Double popularity = b + 0.7 * c + 0.4 * k;
            bookRepository.updatePopularity(popularity, book.getId());
        }
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAllByOrderByBookPopularityDesc(nextPage);
    }

    public Page<Book> getPopularityBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAllByOrderByBookPopularityDesc(nextPage);
    }

}
