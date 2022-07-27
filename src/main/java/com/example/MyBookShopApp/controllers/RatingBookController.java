package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@Controller
public class RatingBookController {

    private final RatingService ratingService;

    @Autowired
    public RatingBookController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/rateBook")
    public String handleRatingBook(@RequestParam("bookId") String slug,
                                   @RequestParam("value") Integer value){
        ratingService.updateRatingBooks(slug, value);
        return ("redirect:/books/" + slug);
    }
}
