package com.project.boardApp.api.service;

import com.project.boardApp.api.ui.model.ArticleDetailResponseModel;
import com.project.boardApp.api.ui.model.ArticleListResponseModel;
import com.project.boardApp.api.ui.model.CreateArticleRequestModel;
import com.project.boardApp.api.ui.model.UpdateArticleRequestModel;

import java.util.List;

public interface BoardService {

    List<ArticleListResponseModel> getArticles(String startDate,String endDate);
    ArticleDetailResponseModel saveArticle(CreateArticleRequestModel article);
    ArticleDetailResponseModel getArticle(Integer articleId);
    ArticleDetailResponseModel updateArticle(Integer articleId, UpdateArticleRequestModel article);
    void deleteArticle(Integer articleId);

}
