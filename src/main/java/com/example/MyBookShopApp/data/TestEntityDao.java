package com.example.MyBookShopApp.data;

import org.springframework.stereotype.Repository;

@Repository
public class TestEntityDao extends AbstractHibernateDao<TestEntity>{

    public TestEntityDao(){
        // вызываемм супер метод
        super();
        // устанавливаем класс TestEntity в метод setClazz
        setClazz(TestEntity.class);

    }

}
