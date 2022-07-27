package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.service.BookService;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class RecentController {

    private BookService bookService;
    private Logger logger = Logger.getLogger(RecentController.class);
    @Autowired
    public RecentController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/recent")
    public String getRecentBooks(Model model)  {
        model.addAttribute("recentListByDate", bookService.getRecentPageBooks(0,20).getContent());
        return "books/recent";
    }

    @PostMapping("/books/recent")
    public String getRecentBooksByDate(@RequestParam("from") String from,
                                       @RequestParam("to") String to,
                                       Model model){
        Date dateFrom = new Date();
        Date dateTo = new Date();
        try {
            dateFrom = new SimpleDateFormat("dd.MM.yyyy").parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            dateTo = new SimpleDateFormat("dd.MM.yyyy").parse(to);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.addAttribute("recentListByDate", bookService.getRecentBooksOrderByDate(dateFrom, dateTo, 0 , 20).getContent());
        return "books/recent";
    }

    @GetMapping(value = {"/books/recent/changedate", "/books/page/recent"})
    @ResponseBody
    public BooksPageDto getNextOrderRecentPageBook(@RequestParam("from") String from,
                                                   @RequestParam("to") String to,
                                                   @RequestParam("offset") Integer offset,
                                                   @RequestParam ("limit") Integer limit){
        Date dateFrom = new Date();
        Date dateTo = new Date();
        try {
            dateFrom = new SimpleDateFormat("dd.MM.yyyy").parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            dateTo = new SimpleDateFormat("dd.MM.yyyy").parse(to);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new BooksPageDto(bookService.getRecentBooksOrderByDate(dateFrom, dateTo, offset,limit).getContent());
    }

}
