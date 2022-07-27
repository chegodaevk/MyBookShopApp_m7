package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

// наследуем JpaRepository (для возможности возвращения List)
public interface BookRepository extends JpaRepository<Book,Integer> {

    // в JPA repository мы можем определять именнованные методы запроса
    List<Book> findBooksByAuthor_FirstName(String name);

    // определяем запрос при помощи аннотации query в теле которой прописываем HQL запрос(from Book соответствует запросу SELECT * FROM books)
    @Query("from Book")
    List<Book> customFindAllBooks();

    // NEW BOOK REST REPOSITORY COMMANDS
    // Containing обозначает что мы будем искать не полное соответствие а вхождение (содержит в себе параметр переданный в аргумент)
    List<Book> findBooksByAuthorFirstNameContaining(String authorFirstName);

    List<Book> findBooksByTitleContaining(String bookTitle);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    List<Book> findBooksByPriceOldIs (Integer price);

    // используем запрос на HML
    @Query("from Book where isBestseller=1")
    List<Book> getBestseller();

    // используем запрос на SQL
    @Query(value = "SELECT * FROM books WHERE discount = (SELECT MAX(discount) FROM books)", nativeQuery=true)
    List<Book> getBooksWithMaxDiscount();

    // метод поиска книг который будет учитывать необходимость постраничного вывода
    Page<Book> findBooksByTitleContaining (String bookTitle, Pageable nextPage);

    // метод вывода книг новинок
    Page<Book> findAllByOrderByPubDateDesc(Pageable pageable);

    // метод вывода книг новинок отсортированных по дате публикации в заданном диапазоне
    Page<Book> findBooksByPubDateBetweenOrderByPubDateDesc(Date fromDateRecent, Date endDateRecent, Pageable pageable);

    // обновление столбца коэф. популярности
    @Transactional
    @Modifying
    @Query("UPDATE Book book set book.bookPopularity = ?1 WHERE book.id = ?2")
    void updatePopularity(Double popularity, Integer id);

//    @Query(value = "UPDATE books set book_popularity = ?1 WHERE id = ?2", nativeQuery = true)
//    void updatePopularity(Double popularity, Integer id);

    Page<Book> findAllByOrderByBookPopularityDesc(Pageable pageable);

    Page<Book> findBooksByTagEntity_Tag(String tag, Pageable pageable);

    List<Book> findBooksByTagEntity_Tag(String tag);

    Page<Book> findBooksByGenreEntity_Name(String genre, Pageable pageable);

    Page<Book> findBooksByGenreEntity_ParentGenre_ParentGenre(String parentGenre, Pageable pageable);

    List<Book> findBooksByGenreEntity_ParentGenre_ParentGenre(String parentGenre);

    Page<Book> findBooksByAuthor_Id(Integer id, Pageable pageable);

    Book findBooksBySlug(String slug);

    // находим книги по массиву содержащему в себе slug книг
    List<Book> findBooksBySlugIn(String[] slugs);


}
