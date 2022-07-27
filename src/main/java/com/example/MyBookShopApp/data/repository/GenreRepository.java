package com.example.MyBookShopApp.data.repository;


import com.example.MyBookShopApp.data.struct.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {

    List<GenreEntity> findGenreEntityByParentGenre_Id(Integer id);
}
