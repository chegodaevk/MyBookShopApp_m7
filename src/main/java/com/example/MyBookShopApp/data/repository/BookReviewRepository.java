package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.book.review.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReviewRepository extends JpaRepository<BookReview, Integer> {

    List<BookReview> findBookReviewsByBook_SlugOrderByRatingDesc(String slug);

    BookReview findBookReviewsById(Integer id);
}
