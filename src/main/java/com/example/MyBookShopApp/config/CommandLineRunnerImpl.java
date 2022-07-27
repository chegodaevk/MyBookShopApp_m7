package com.example.MyBookShopApp.config;

import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.TestEntity;

import com.example.MyBookShopApp.data.repository.TestEntityCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.logging.Logger;

//@Configuration
public class CommandLineRunnerImpl  implements CommandLineRunner {



    TestEntityCrudRepository testEntityCrudRepository;
    BookRepository bookRepository;

    @Autowired
    public CommandLineRunnerImpl(TestEntityCrudRepository testEntityCrudRepository, BookRepository bookRepository) {
        this.testEntityCrudRepository = testEntityCrudRepository;
        this.bookRepository = bookRepository;
    }

    // в данном методе прописанна вся hibernate логика по созданию записей в БД, чтению, обновлению или удалению данных из таблицы БД
    @Override
    public void run(String... args) throws Exception {
        //реализуем операцию создания новых записей
        for (int i=0; i<5; i++){
            createTestEntity(new TestEntity());
        }

        // чтение данных с БД
        // преминяем новый метод findOne из testEntityDao
//        TestEntity readTestEntity = testEntityDao.findOne(3L);
        TestEntity readTestEntity = readTestEntityById(3L);
            if(readTestEntity != null){
                Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("read " + readTestEntity.toString());
            }else {
                throw new NullPointerException();
            }

        // обновлеение данных в БД
        TestEntity updatedTestEntity = updateTestEntity(5L);
            if(updatedTestEntity != null){
                Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("update " + updatedTestEntity.toString());
            }else {
                throw new NullPointerException();
            }
        // удаление данных из БД
        deleteTestEntity(4L);

        // для вывода книг по имени автора
        Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info(bookRepository.findBooksByAuthor_FirstName("Willshere").toString());
        // для вывода всего перечня книг из БД
        Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info(bookRepository.customFindAllBooks().toString());
    }

    private void deleteTestEntity(Long id) {
        // находим сущность
        TestEntity testEntity = testEntityCrudRepository.findById(id).get();
        // удаляем сущность
        testEntityCrudRepository.delete(testEntity);

//
    }

    private TestEntity updateTestEntity(Long id) {
        TestEntity testEntity = testEntityCrudRepository.findById(id).get();
        // преобразуем testEntity
        testEntity.setData("NEW DATA");
        // сохраняем testEntity при помощи метода save
        testEntityCrudRepository.save(testEntity);
        return testEntity;

    }

    private TestEntity readTestEntityById(Long id) {
        // метод findById получая данные по id  и в конце метод get (т.к. метод findById возвращает тип optional)
        return testEntityCrudRepository.findById(id).get();
    }


    private void createTestEntity(TestEntity entity){
        // задаём значения свойствам полученнной пустой entity, вызвав метод setData и вписываем название класса + хэшкод
        entity.setData(entity.getClass().getSimpleName() + entity.hashCode());
        // с помошью метода save() происходит сохранение
        testEntityCrudRepository.save(entity);

    }
}
