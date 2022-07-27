package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.book.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<BookRating,Integer> {

    BookRating findBookRatingById(Integer id);
}
