package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.service.BookService;
import com.example.MyBookShopApp.data.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TagsController {

    private final BookService bookService;
    private final TagService tagService;

    @Autowired
    public TagsController(BookService bookService, TagService tagService) {
        this.bookService = bookService;
        this.tagService = tagService;
    }

    @ModelAttribute("tagsList")
    public List<TagEntity> tagList(){
        return tagService.getTagList();
    }

    @GetMapping("/tag/{tag}")
    public String getBooksByTag(@PathVariable(value = "tag", required = false) String tag, Model model){
        model.addAttribute("tag", tag);
        model.addAttribute("tagList", bookService.getBooksByTag(tag,0, 5).getContent());
        return "tags/index";
    }

    @GetMapping("/books/tag/{tag}")
    @ResponseBody
    public BooksPageDto getNextPageBookByTag(@PathVariable(value = "tag", required = false) String tag,
                                             @RequestParam("offset") Integer offset,
                                             @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getBooksByTag(tag, offset, limit).getContent());
    }
}
