package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.TagEntity;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.dto.SearchWordDto;
import com.example.MyBookShopApp.data.service.BookService;
import com.example.MyBookShopApp.data.service.BooksRatingAndPopularityService;
import com.example.MyBookShopApp.data.service.TagService;
import com.example.MyBookShopApp.data.struct.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;
    private final TagService tagService;
    private final BooksRatingAndPopularityService booksRatingAndPopularityService;

    @Autowired
    public MainPageController(BookService bookService, TagService tagService, BooksRatingAndPopularityService booksRatingAndPopularityService) {
        this.bookService = bookService;
        this.tagService = tagService;
        this.booksRatingAndPopularityService = booksRatingAndPopularityService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        // метод getPageOfRecommendedBooks возвращает Pageable, для возвращения List нужно воспльзоваться методом getContent
        return bookService.getPageOfRecommendedBooks(0,6).getContent();
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks() {
        return bookService.getRecentPageBooks(0,6).getContent();
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks() {
        return booksRatingAndPopularityService.getPopularityBooksByRating(0, 6).getContent();
    }

    @ModelAttribute("tagsList")
    public List<TagEntity> tagList(){
        return tagService.getTagList();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResult(){
        return new ArrayList<>();
    }

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }

    @GetMapping("/signin")
    public String signinBoookShop() {
        return "signin";
    }

    @GetMapping("/documents")
    public String documentsPage(){
        return "documents/index";
    }

    @GetMapping("/about")
    public String aboutPage(){
        return "about";
    }

    @GetMapping("/faq")
    public String helpPage(){
        return "faq";
    }

    @GetMapping("/contacts")
    public String contactsPage(){
        return "contacts";
    }

    @GetMapping("/signup")
    public String registrationPage(){
        return "signup";
    }

    // вывод рекомендованных книг на главной страници
    @GetMapping("/recommended")
    @ResponseBody
    public BooksPageDto getBooksPage (@RequestParam("offset") Integer offset,
                                      @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }

    // вывод новинок на главной страници
    @GetMapping("/recent")
    @ResponseBody
    public BooksPageDto getRecentBooksPage (@RequestParam("offset") Integer offset,
                                           @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getRecentPageBooks(offset, limit).getContent());
    }

    // вывод популярных книг на главной страници
    @GetMapping("/popular")
    @ResponseBody
    public BooksPageDto getPopularBooksPage (@RequestParam("offset") Integer offset,
                                             @RequestParam("limit") Integer limit) {
        return new BooksPageDto(booksRatingAndPopularityService.getPopularityBooks(offset, limit).getContent());
    }

}
