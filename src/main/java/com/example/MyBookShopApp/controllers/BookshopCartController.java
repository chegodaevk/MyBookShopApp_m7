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
import java.util.StringJoiner;
import java.util.logging.Logger;

@Controller
@RequestMapping("/books")
public class BookshopCartController {

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart(){
        return new ArrayList<>();
    }

    private final BookRepository bookRepository;

    @Autowired
    public BookshopCartController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // вывод содержимого корзины
    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model){
        // проверяем содержимое корзины (если корзины пуста то cookie нет)
        if (cartContents == null || cartContents.equals("")){
            model.addAttribute("isCartEmpty", true);
        } else {
            model.addAttribute("isCartEmpty", false);
            // если cartContents начинается со знака '/' то при помощи метода substring обрезаем данный знак
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            // если cartContents заканчивается знаком '/' то удаляем его
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
            // преобразуем строковое значение cartContents (в котором slug книг разделены между собой знаком '/') в массив
            // содержащий в себе slug книг (которые хранятся в cookie)
            String[] cookieSlugs = cartContents.split("/");
            // получем список книг по массину slug
            List<Book> booksFromCookiesSlugs = bookRepository.findBooksBySlugIn(cookieSlugs);
            model.addAttribute("bookCart", booksFromCookiesSlugs);
        }
        return "cart";
    }

//    @GetMapping("/postponed")
//    public String postponedBookPage(@CookieValue(name = "postponedContents", required = false) String postponedContents,
//                                    Model model) {
//        if (postponedContents == null || postponedContents.equals("")){
//            model.addAttribute("isPostponedEmpty", true);
//        } else {
//            model.addAttribute("isPostponedEmpty", false);
//            postponedContents = postponedContents.startsWith("/") ? postponedContents.substring(1) : postponedContents;
//            postponedContents = postponedContents.endsWith("/") ? postponedContents.substring(0, postponedContents.length()-1) : postponedContents;
//            String[] cookieSlugs = postponedContents.split("/");
//            List<Book> bookFromCookiesSlug = bookRepository.findBooksBySlugIn(cookieSlugs);
//            model.addAttribute("bookPostponed", bookFromCookiesSlug);
//        }
//        return "postponed";
//    }

    // удаление книг из корзины
    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug,
                                                  @CookieValue(name = "cartContents", required = false) String cartContents,
                                                  HttpServletResponse response,
                                                  Model model){
        // проверяем чтобы cookie небыл равен null а также небыл равен пустой строке
        if (cartContents != null && !cartContents.equals("")) {
            // создаем пустой список содержащий строковвые значения и в этот список загружаем массив мнеманических идентификаторов(slug)
            // содержащихся в cookie (в cookie slug разделены между собой знаком '/')
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            // удаляем из полученного списка cookieBooks переданный для удаления мнеманический идентификатор книги(slug)
            cookieBooks.remove(slug);
            // создаём эземпляр cookie и добавляем в него отредактированную строку
            // конвертировав её обратно из списка в строковое представление
            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else {
            // в противном случае указываем что корзина пуста
            model.addAttribute("isCartEmpty", true);
        }
        return "redirect:/books/cart";
    }

    // добавление книг в корзину
    // аннотация CookieValue используется джля указания имя cookie, допополнительное роле required=false указывает что оно не обязательно
    // для записи cookie потребуется HttpServletResponse и Model
    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug,
                                         @RequestParam("status") String status,
                                         @CookieValue(name = "cartContents", required = false) String cartContents,
                                         @CookieValue(name = "postponedContents", required = false) String postponedContents,
                                         HttpServletResponse response,
                                         Model model){
        Logger.getLogger(this.getClass().getSimpleName()).info("Status: " + status);
        if (status.equals("CART")) {
            // проверяем cookie на соответствие null или пустой строке
            if (cartContents == null || cartContents.equals("")) {
                // создаём новый экземпляр объекта Cookie, записывая в него значение slug книги
                Cookie cookie = new Cookie("cartContents", slug);
                // устанавливаем путь чтобы этот cookie был доступен из всех endpoint имеющих subdirectory c данным значением
                cookie.setPath("/books");
                // добавляем cookie к ответу
                response.addCookie(cookie);
                // эллементу isCartEmpty устанавливаем значение false
                model.addAttribute("isCartEmpty", false);
                // в противном случае проверяем если значение cookie(в данном случае cartContents) не содержит текущей переменной slug
            } else if (!cartContents.contains(slug)) {
                StringJoiner stringJoiner = new StringJoiner("/");
                // добавляем к уже существующему значение cartContents новый slug книги
                stringJoiner.add(cartContents).add(slug);
                // создаем новый экземпляп объекта cookie добавляя к нему обновленное значение
                Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
                cookie.setPath("/books");
                response.addCookie(cookie);
                model.addAttribute("isCartEmpty", false);
            }
            return ("redirect:/books/" + slug);
        }
        if (postponedContents == null || postponedContents.equals("")){
            Cookie cookie = new Cookie("postponedContents", slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isPostponedEmpty", false);
        } else if (!postponedContents.contains(slug)){
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(postponedContents).add(slug);
            Cookie cookie = new Cookie("postponedContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isPostponedEmpty", false);
        }
        return ("redirect:/books/" + slug);
    }
}
