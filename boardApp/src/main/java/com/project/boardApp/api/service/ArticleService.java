package com.project.boardApp.api.service;

import com.project.boardApp.api.ui.model.ArticleDetailResponseModel;
import com.project.boardApp.api.ui.model.ArticleListResponseModel;
import com.project.boardApp.api.ui.model.CreateArticleRequestModel;
import com.project.boardApp.api.ui.model.UpdateArticleRequestModel;

import java.util.Date;
import java.util.List;

public interface ArticleService {

    List<ArticleListResponseModel> getArticles(Date startDate, Date endDate, String boardName);
    ArticleDetailResponseModel saveArticle(CreateArticleRequestModel article);
    ArticleDetailResponseModel getArticle(Integer articleId);
    List<ArticleListResponseModel> findArticlesByBoard(Integer BoardId);

    ArticleDetailResponseModel updateArticle(Integer articleId, UpdateArticleRequestModel article);
    void deleteArticle(Integer articleId);

}
