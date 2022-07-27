package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.struct.book.Book;
import com.example.MyBookShopApp.data.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/books")
public class PostponedController {

    private final BookRepository bookRepository;

    @Autowired
    public PostponedController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/postponed")
    public String postponedBookPage(@CookieValue(name = "postponedContents", required = false) String postponedContents,
                                    Model model) {
        if (postponedContents == null || postponedContents.equals("")){
            model.addAttribute("isPostponedEmpty", true);
        } else {
            model.addAttribute("isPostponedEmpty", false);
            postponedContents = postponedContents.startsWith("/") ? postponedContents.substring(1) : postponedContents;
            postponedContents = postponedContents.endsWith("/") ? postponedContents.substring(0, postponedContents.length()-1) : postponedContents;
            String[] cookieSlugs = postponedContents.split("/");
            List<Book> bookFromCookiesSlug = bookRepository.findBooksBySlugIn(cookieSlugs);
            model.addAttribute("bookPostponed", bookFromCookiesSlug);
        }
        return "postponed";
    }

    @PostMapping("/changeBookStatus/postponed/remove/{slug}")
    public String handleRemoveBookFromPostponedRequest(@PathVariable("slug") String slug,
                                                       @CookieValue(name = "postponedContents", required = false) String postponedContents,
                                                       HttpServletResponse response,
                                                       Model model){
        if (postponedContents != null && !postponedContents.equals("")){
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(postponedContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("postponedContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isPostponedEmpty", false);
        } else {
            model.addAttribute("isPostponedEmpty", true);
        }
        return "redirect:/books/postponed";
    }

}
