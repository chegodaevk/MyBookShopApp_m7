package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.struct.author.Author;
import com.example.MyBookShopApp.data.service.AuthorService;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Api(description = "authors data")
public class AuthorsController {

    private final AuthorService authorService;

    @Autowired
    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ModelAttribute("authorsMap")
    public Map<String,List<Author>> authorsMap(){
        return authorService.getAuthorsMap();
    }

    @GetMapping("/authors")
    public String authorsPage(){
        return "/authors/index";
    }

    // для документирования request handler используем анотацию ApiOperation, на данном этапе укажем только описание операции
    @ApiOperation("method to get map of authors")
    @GetMapping("/api/authors")
    //  Spring думает что мы будем возвращать имя Template (шаблона) для того чтобы это не происходило используем анотацию ResponseBody
    @ResponseBody
    public Map<String,List<Author>> authors(){
        return authorService.getAuthorsMap();
    }

    @GetMapping("/authors/{id}")
    public String authorPage(@PathVariable(value = "id", required = false) Integer id, Model model){
        model.addAttribute("author", authorService.getAuthorById(id));
        model.addAttribute("authorsBooksList", authorService.getBooksListByAuthorId(id, 0, 5).getContent());
        return "/authors/slug";
    }

    @GetMapping("/books/author/{id}")
    @ResponseBody
    public BooksPageDto getNextPageAuthorsBooks(@RequestParam("offset") Integer offset,
                                                @RequestParam("limit") Integer limit,
                                                @PathVariable(value = "id", required = false) Integer id){
        return new BooksPageDto(authorService.getBooksListByAuthorId(id, offset, limit).getContent());
    }
}
