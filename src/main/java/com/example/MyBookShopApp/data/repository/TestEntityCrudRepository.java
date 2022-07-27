package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.TestEntity;
import org.springframework.data.repository.CrudRepository;

// расширяем интерфейс CrudRepository которому в качестве рабочего типа передаём TestEntity и тип его id (Long)
public interface TestEntityCrudRepository extends CrudRepository<TestEntity,Long>{
}
