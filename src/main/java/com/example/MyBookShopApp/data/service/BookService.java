package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.struct.book.Book;
import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.errs.BookstoreApiWrongParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksData() {
        // метод findAll возвращает весь список из таблицы БД
        return bookRepository.findAll();
    }

    // NEW BOOK SERVICE METHOD

    // вся бизнес логика должна быть прописанна в сервисах не в контроллерах
    public List<Book> getBooksByAuthor(String authorName){
        return bookRepository.findBooksByAuthorFirstNameContaining(authorName);
    }

    public List<Book> getBooksByTitle(String title) throws BookstoreApiWrongParameterException {
        if (title.equals("") || title.length() <=1){
            throw new BookstoreApiWrongParameterException("Wrong values passed to one or more parameters");
        } else {
            List<Book> data = bookRepository.findBooksByTitleContaining(title);
            if (data.size() > 0){
                return data;
            } else {
                throw new BookstoreApiWrongParameterException("No data found with specified parameters ...");
            }
        }
    }

    public List<Book> getBooksWithPriceBetween(Integer min, Integer max){
        return bookRepository.findBooksByPriceOldBetween(min, max);
    }

    public List<Book> getBooksWithPrice(Integer price){
        return bookRepository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBooksWithMaxDiscount(){
        return bookRepository.getBooksWithMaxDiscount();
    }

    public List<Book> getBestseller(){
        return bookRepository.getBestseller();
    }

    // полученме рекомендовоных книг
    // offset - номер страницы limit - ограничение по числу выводимых на странице записей
    public Page<Book> getPageOfRecommendedBooks (Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAll(nextPage);
    }

    // поиск книг по  title
    // в качестве параметров принимает искомое название книги, значение для страницы и лимит вывода
    public Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBooksByTitleContaining(searchWord, nextPage);
    }

    // получение книг новинок
    public Page<Book> getRecentPageBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findAllByOrderByPubDateDesc(nextPage);
    }

    public Page<Book> getRecentBooksOrderByDate(Date fromDate, Date endDate, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findBooksByPubDateBetweenOrderByPubDateDesc(fromDate, endDate, nextPage);
    }

    // получениы популярных книг
    public Page<Book> getPageOfPopularBooks(Integer offset, Integer limit){
         Pageable nextPage = PageRequest.of(offset, limit);
         return bookRepository.findAll(nextPage);
    }

    // получение списка книг по тегу
    public Page<Book> getBooksByTag(String tag, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBooksByTagEntity_Tag(tag, nextPage);
    }



}
