package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.service.ParentAndGenreService;
import com.example.MyBookShopApp.data.struct.genre.GenreEntity;
import com.example.MyBookShopApp.data.struct.genre.ParentGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
public class GenresController {


    public final ParentAndGenreService parentAndGenreService;

    @Autowired
    public GenresController(ParentAndGenreService parentAndGenreService) {
        this.parentAndGenreService = parentAndGenreService;
    }

    @ModelAttribute("genreMap")
    public HashMap<ParentGenre, List<GenreEntity>> getGenreMap(){
        return parentAndGenreService.getGenresMap();
    }

    @GetMapping("/genres")
    public String genresBookPage () {
        return "genres/index";
    }

    @GetMapping("/genres/{name}")
    public String getBooksByGenre(@PathVariable(value = "name", required = false) String genreName,
                                  Model model){
        model.addAttribute("value", "genre");
        model.addAttribute("bookListByGenre", parentAndGenreService.getBooksByGenre(genreName, 0, 5).getContent());
        return "genres/slug";
    }

    @GetMapping("/parentGenres/{name}")
    public String getBooksByParentGenre(@PathVariable(value = "name", required = false) String genreName,
                                        Model model){
        model.addAttribute("value", "parentGenre");
        model.addAttribute("bookListByGenre", parentAndGenreService.getBooksByParentGenre(genreName, 0, 5).getContent());
        return "genres/slug";
    }

    @GetMapping("/books/genre/{name}")
    @ResponseBody
    public BooksPageDto getNextPageBooksByGenre(@PathVariable(value = "name", required = false) String genreName,
                                                @RequestParam("offset") Integer offset,
                                                @RequestParam("limit") Integer limit){
        return new BooksPageDto(parentAndGenreService.getBooksByGenre(genreName, offset, limit).getContent());
    }

    @GetMapping("/books/parentGenre/{name}")
    @ResponseBody
    public BooksPageDto getNextPageBooksByParentGenre(@PathVariable(value = "name", required = false) String parentName,
                                                      @RequestParam("offset") Integer offset,
                                                      @RequestParam("limit") Integer limit){
        return new BooksPageDto(parentAndGenreService.getBooksByParentGenre(parentName, offset, limit).getContent());
    }
}
