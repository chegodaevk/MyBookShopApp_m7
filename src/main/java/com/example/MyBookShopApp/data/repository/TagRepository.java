package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface TagRepository extends JpaRepository<TagEntity, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE TagEntity tagEntity set tagEntity.quantityBooksWithTag=?1 WHERE tagEntity.id=?2")
    void  updateQuantityTags(Integer quantityBookWithTag, Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE TagEntity  tagEntity set tagEntity.cssClass=?1 WHERE tagEntity.id=?2")
    void updateCssClass(String cssClass, Integer id);
}
