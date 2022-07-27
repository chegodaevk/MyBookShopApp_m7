package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.ApiResponse;
import com.example.MyBookShopApp.data.struct.book.Book;
import com.example.MyBookShopApp.data.service.BookService;
import com.example.MyBookShopApp.errs.BookstoreApiWrongParameterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

// отличия анотации RestController от Controller состоит лишь в том что она добавляет ещё анотацию и ResponseBody
@RestController
// добавляем анотацию RequestMapping для того чтобы отделить внешний API от внутреннего который описан в MainPageController
@RequestMapping("/api")
@Api(description = "book data api")
public class BooksRestApiController {

    private final BookService bookService;

    public BooksRestApiController(BookService bookService) {
        this.bookService = bookService;
    }

    // возвращение книг по заданному автору
    @GetMapping("/books/by-author")
    @ApiOperation("operation to get book list of bookshop by passed author first name")
    public ResponseEntity<List<Book>> booksByAuthor(@RequestParam("author") String authorName){
        // возвращаем экземпляр объекта ResponseEntity, вызываем метод ok(т.е. успешное выполнение запроса)
        // в качестве аргумента передаём список заданных авторов получаемый из bookService
        return ResponseEntity.ok(bookService.getBooksByAuthor(authorName));
    }

    // возвращаем имя ниги
    @GetMapping("/books/by-title")
    @ApiOperation("get book by title")
    public  ResponseEntity<ApiResponse<Book>> booksByTitle(@RequestParam("title") String title) throws BookstoreApiWrongParameterException {
        ApiResponse<Book> response = new ApiResponse<>();
        List<Book> data = bookService.getBooksByTitle(title);
        response.setDebugMessage("successful request");
        response.setMessage("data size: " + data.size() + " elements");
        response.setStatus(HttpStatus.OK);
        response.setTimeStamp(LocalDateTime.now());
        response.setData(data);
    return ResponseEntity.ok(response);
    }

    // поиск книг по диапазону цен
    @GetMapping("/books/by-price-range")
    @ApiOperation("get book by price range from min price to max price")
    public ResponseEntity<List<Book>> priceRangeBooks(@RequestParam("min") Integer min, @RequestParam("max") Integer max){
        return ResponseEntity.ok(bookService.getBooksWithPriceBetween(min,max));
    }

    // книги с максимальнгой ценой
    @GetMapping("/books/with-max-discount")
    @ApiOperation("get list of book with max price")
    public ResponseEntity<List<Book>> maxDiscountBooks(){
        return ResponseEntity.ok(bookService.getBooksWithMaxDiscount());
    }

    // возвращаем бестселлеры
    @GetMapping("/book/bestseller")
    @ApiOperation("get bestseller books (which is_bestseller = 1)")
    public ResponseEntity<List<Book>> bestsellerBooks(){
        return ResponseEntity.ok(bookService.getBestseller());
    }

    // для обработки исклюючения ипользуется аннотация ExceptionHandler
    // в качестве параметра которой указывается класс ошибки которую будет отлавливать и обробатывать данный ExceptionHandler
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleMissingServletRequestParameterException(Exception exception){
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST, "Missing request parameters",
                exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookstoreApiWrongParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleBookstoreApiWrongParameterException(Exception exception){
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST, "Bad parameter value ...", exception),
                HttpStatus.BAD_REQUEST);
    }

}
