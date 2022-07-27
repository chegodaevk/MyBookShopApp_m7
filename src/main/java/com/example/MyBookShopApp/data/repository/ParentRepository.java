package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.genre.ParentGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<ParentGenre, Integer> {

}
