package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.struct.book.Book;
import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.repository.RatingRepository;
import com.example.MyBookShopApp.data.struct.book.BookRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class RatingService {

    private final BookRepository bookRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(BookRepository bookRepository, RatingRepository ratingRepository) {
        this.bookRepository = bookRepository;
        this.ratingRepository = ratingRepository;
    }

    public void updateRatingBooks(String slug, Integer value){
        Book book = bookRepository.findBooksBySlug(slug);
        BookRating bookRating = updateStarsAndNumberGrades(ratingRepository.findBookRatingById(book.getBookRating().getId()), value);
        double rating = (double) (bookRating.getOneStars() + bookRating.getTwoStars() * 2 + bookRating.getThreeStars() * 3 +
                bookRating.getFourStars() * 4 + bookRating.getFiveStars() * 5 ) / bookRating.getNumberGrades();
        bookRating.setRating((int) Math.round(rating));
        ratingRepository.save(bookRating);
    }

    private BookRating updateStarsAndNumberGrades (BookRating bookRating, Integer value){
        switch (value){
            case (1) : bookRating.setOneStars(bookRating.getOneStars() + 1);
                bookRating.setNumberGrades(bookRating.getNumberGrades() + 1);
                break;
            case (2) : bookRating.setTwoStars(bookRating.getTwoStars() + 1);
                bookRating.setNumberGrades(bookRating.getNumberGrades() + 1);
                break;
            case (3) : bookRating.setThreeStars(bookRating.getThreeStars() + 1);
                bookRating.setNumberGrades(bookRating.getNumberGrades() + 1);
                break;
            case (4) : bookRating.setFourStars(bookRating.getFourStars() + 1);
                bookRating.setNumberGrades(bookRating.getNumberGrades() + 1);
                break;
            case (5) : bookRating.setFiveStars(bookRating.getFiveStars() + 1);
                bookRating.setNumberGrades(bookRating.getNumberGrades() + 1);
                break;
        }
        return bookRating;
    }
}
