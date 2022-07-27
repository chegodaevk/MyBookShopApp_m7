package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.struct.book.Book;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.service.BooksRatingAndPopularityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
public class PopularController {

    private BooksRatingAndPopularityService booksRatingAndPopularityService;

    @Autowired
    public PopularController(BooksRatingAndPopularityService booksRatingAndPopularityService) {
        this.booksRatingAndPopularityService = booksRatingAndPopularityService;
    }

    @ModelAttribute ("booksPopularityList")
    public List<Book> getPopularityBooksList(){
        return booksRatingAndPopularityService.getPopularityBooksByRating(0,20).getContent();
    }

    @GetMapping("/books/popular")
    public String getPagePopularBooks(Model model){
        return "books/popular";
    }

    @GetMapping("/books/page/popular")
    @ResponseBody
    public BooksPageDto getNextPagePopularBooks(@RequestParam("offset") Integer offset,
                                                @RequestParam("limit") Integer limit){
        return new BooksPageDto(booksRatingAndPopularityService.getPopularityBooks(offset, limit).getContent());
    }

}
