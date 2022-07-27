package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.book.review.BookReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReviewLikeRepository extends JpaRepository<BookReviewLike, Integer> {

    List<BookReviewLike> findBookReviewLikeByBookReview_IdAndAndValue(Integer reviewId, Short value);

}
