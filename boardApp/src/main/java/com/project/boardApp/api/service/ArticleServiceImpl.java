package com.project.boardApp.api.service;

import com.project.boardApp.api.data.Article;
import com.project.boardApp.api.data.repository.ArticleRepository;
import com.project.boardApp.api.data.Attachment;
import com.project.boardApp.api.data.Board;
import com.project.boardApp.api.ui.model.ArticleDetailResponseModel;
import com.project.boardApp.api.ui.model.ArticleListResponseModel;
import com.project.boardApp.api.ui.model.CreateArticleRequestModel;
import com.project.boardApp.api.ui.model.UpdateArticleRequestModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<ArticleListResponseModel> getArticles(Date startDate, Date endDate, String boardName) {
        List<Article> articles = articleRepository.findArticlesWithOptions(startDate, endDate, boardName);

        return getArticleListResponseModels(articles);
    }

    private List<ArticleListResponseModel> getArticleListResponseModels(List<Article> articles) {
        List<ArticleListResponseModel> returnValue = new ArrayList<>();

        articles.stream().forEach(article -> {
            ArticleListResponseModel articleModel = modelMapper.map(article, ArticleListResponseModel.class);
            articleModel.setBoardName(article.getBoard().getName());

            //첨부파일 없을때 대비
            if(article.getAttachments()!=null){
                articleModel.setLocation(article.getAttachments().get(0).getLocation());
            }

            returnValue.add(articleModel);
        });
        return returnValue;
    }

    @Override
    public ArticleDetailResponseModel saveArticle(CreateArticleRequestModel articleModel) {

        Article article = modelMapper.map(articleModel, Article.class);
        List<Attachment> attachments = getAttachments(article);
        article.setBoard(new Board(articleModel.getBoardName()));
        article.setAttachments(attachments);

        Article savedArticle = articleRepository.save(article);

        ArticleDetailResponseModel returnValue = getArticleDetailResponseModel(savedArticle);

        return returnValue;
    }


    private ArticleDetailResponseModel getArticleDetailResponseModel(Article article) {
        ArticleDetailResponseModel articleDetail = modelMapper.map(article, ArticleDetailResponseModel.class);
        List<String> locations = article.getAttachments().stream().map(e -> e.getLocation()).collect(Collectors.toList());

        articleDetail.setLocations(locations);
        articleDetail.setBoardName(article.getBoard().getName());
        return articleDetail;
    }

    private List<Attachment> getAttachments(Article article) {
        return List.of(new Attachment("location1", article), new Attachment("location2", article), new Attachment("location3", article));
    }

    @Override
    public ArticleDetailResponseModel getArticle(Integer articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(()->new EmptyResultDataAccessException(1));

        //조회수 올리기
        addViewCnt(article);

        return getArticleDetailResponseModel(article);
    }

    private void addViewCnt(Article article) {
        article.setViewCnt(article.getViewCnt()+1);
        articleRepository.save(article);
    }


    @Override
    public ArticleDetailResponseModel updateArticle(Integer articleId, UpdateArticleRequestModel article) {
        Article articleToBeUpdated = articleRepository.findById(articleId).orElseThrow(()->new EmptyResultDataAccessException(1));
        articleToBeUpdated.setTitle(article.getTitle());
        articleToBeUpdated.setContent(article.getContent());
        Article updatedArticle = articleRepository.save(articleToBeUpdated);
        return getArticleDetailResponseModel(updatedArticle);
    }

    @Override
    public void deleteArticle(Integer articleId) {
        articleRepository.deleteById(articleId);
    }
}
