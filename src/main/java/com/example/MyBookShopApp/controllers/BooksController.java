package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.repository.BookReviewRepository;
import com.example.MyBookShopApp.data.service.BookReviewService;
import com.example.MyBookShopApp.data.struct.book.Book;
import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.service.ResourceStorage;
import com.example.MyBookShopApp.data.struct.book.review.BookReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookRepository bookRepository;
    private final ResourceStorage storage;
    private final BookReviewRepository bookReviewRepository;
    private final BookReviewService bookReviewService;

    @Autowired
    public BooksController(BookRepository bookRepository,
                           ResourceStorage storage,
                           BookReviewRepository bookReviewRepository,
                           BookReviewService bookReviewService) {
        this.bookRepository = bookRepository;
        this.storage = storage;
        this.bookReviewRepository = bookReviewRepository;
        this.bookReviewService = bookReviewService;
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable(value = "slug", required = false) String slug,
                           @ModelAttribute("bookReview") BookReview bookReview,
                           Model model){
        Book book = bookRepository.findBooksBySlug(slug);
        model.addAttribute("slugBook", book);
        model.addAttribute("reviewList", bookReviewRepository.findBookReviewsByBook_SlugOrderByRatingDesc(slug));
        return "/books/slug";
    }

    // сохранение изображения книги
    @PostMapping("/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file") MultipartFile file,
                                   @PathVariable("slug") String slug) throws IOException {
        String savePath = storage.saveNewBookImage(file, slug);
        Book bookToUpdate = bookRepository.findBooksBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookRepository.save(bookToUpdate); // save new path in db here
        return ("redirect:/books/" + slug);
    }

    // скачивание книги
    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {

        Path path = storage.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file path: " + path);

        MediaType mediaType = storage.getBookFileMine(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file mine: " + mediaType);

        byte[] data = storage.getBookFileArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file byte: " + data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }

}
