package com.project.boardApp.api.data.repository;

import com.project.boardApp.api.data.Article;

import java.util.Date;
import java.util.List;


public interface ArticleCustomRepository {
    List<Article> findArticlesWithOptions(Date startDate, Date endDate, String BoardName);
}
