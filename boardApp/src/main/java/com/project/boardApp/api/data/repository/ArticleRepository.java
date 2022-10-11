package com.project.boardApp.api.data.repository;

import com.project.boardApp.api.data.Article;
import com.project.boardApp.api.data.Board;
import com.project.boardApp.api.data.repository.ArticleCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>, ArticleCustomRepository {

    List<Article> findByBoard(Board board);
}
