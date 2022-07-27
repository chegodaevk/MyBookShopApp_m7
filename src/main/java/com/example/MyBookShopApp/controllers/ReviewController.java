package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.service.BookReviewService;
import com.example.MyBookShopApp.data.struct.book.review.BookReview;
import com.example.MyBookShopApp.data.struct.book.review.BookReviewLike;
import liquibase.pro.packaged.B;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Controller
public class ReviewController {

    private final BookReviewService bookReviewService;

    @Autowired
    public ReviewController(BookReviewService bookReviewService) {
        this.bookReviewService = bookReviewService;
    }

    @PostMapping("/books/{slug}/review")
    public String getReviewBook(@PathVariable("slug") String slug,
                                @ModelAttribute("bookReview") BookReview bookReview){
        if (bookReviewService.getAllReview().size() == 0){
            bookReview.setId(1);
        }
//        bookReview.setUserId(1);
        bookReview.setRating(0);
        bookReview.setBook(bookReviewService.getBookBySlug(slug));
        bookReview.setTime(LocalDateTime.now());
        bookReviewService.saveReview(bookReview);
        return "redirect:/books/" + slug;
    }

    @PostMapping("/rateBookReview")
    public String getLikesOrDislikes(@RequestParam("reviewid") Integer reviewId,
                                     @RequestParam("value") Short value){
        Logger.getLogger(this.getClass().getSimpleName()).info("reviewId: " + reviewId);
        Logger.getLogger(this.getClass().getSimpleName()).info("value: " + value);
        BookReview bookReview = bookReviewService.getBookReviewById(reviewId);
        bookReview.setRating(bookReview.getRating() + value);
        bookReviewService.saveReview(bookReview);
        Logger.getLogger(this.getClass().getSimpleName()).info("rating: " + bookReview.getRating());
        BookReviewLike bookReviewLike = new BookReviewLike();
        if (bookReviewService.getAllReviewLike().size() == 0){
            bookReviewLike.setId(1);
        }
//        bookReviewLike.setUserId(1);
        bookReviewLike.setBookReview(bookReviewService.getBookReviewById(reviewId));
        bookReviewLike.setTime(LocalDateTime.now());
        bookReviewLike.setValue(value);
        bookReviewService.saveReviewLike(bookReviewLike);
        return "redirect:/books/" + bookReviewService.getBookByReviewId(reviewId).getSlug();
    }
}
