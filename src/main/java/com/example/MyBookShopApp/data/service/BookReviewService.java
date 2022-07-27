package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.repository.BookReviewLikeRepository;
import com.example.MyBookShopApp.data.repository.BookReviewRepository;
import com.example.MyBookShopApp.data.struct.book.Book;
import com.example.MyBookShopApp.data.struct.book.review.BookReview;
import com.example.MyBookShopApp.data.struct.book.review.BookReviewLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookReviewService {

    private final BookRepository bookRepository;
    private final BookReviewRepository bookReviewRepository;
    private final BookReviewLikeRepository bookReviewLikeRepository;

    @Autowired
    public BookReviewService(BookRepository bookRepository,
                             BookReviewRepository bookReviewRepository,
                             BookReviewLikeRepository bookReviewLikeRepository) {
        this.bookRepository = bookRepository;
        this.bookReviewRepository = bookReviewRepository;
        this.bookReviewLikeRepository = bookReviewLikeRepository;
    }

    public Book getBookBySlug(String slug){
        return bookRepository.findBooksBySlug(slug);
    }

    public void saveReview(BookReview bookReview) {
        bookReviewRepository.save(bookReview);
    }

    public void saveReviewLike(BookReviewLike bookReviewLike){
        bookReviewLikeRepository.save(bookReviewLike);
    }

    public List<BookReview> getAllReview(){
        return bookReviewRepository.findAll();
    }

    public Book getBookByReviewId(Integer reviewId){
        return bookReviewRepository.findBookReviewsById(reviewId).getBook();
    }
    public List<BookReviewLike> getAllReviewLike(){
        return bookReviewLikeRepository.findAll();
    }

    public BookReview getBookReviewById(Integer reviewId){
        return bookReviewRepository.findBookReviewsById(reviewId);
    }

}
