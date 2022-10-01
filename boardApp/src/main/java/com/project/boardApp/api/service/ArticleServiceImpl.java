package com.project.boardApp.api.service;

import com.project.boardApp.api.data.*;
import com.project.boardApp.api.exception.ArticleNotFoundException;
import com.project.boardApp.api.ui.model.ArticleDetailResponseModel;
import com.project.boardApp.api.ui.model.ArticleListResponseModel;
import com.project.boardApp.api.ui.model.CreateArticleRequestModel;
import com.project.boardApp.api.ui.model.UpdateArticleRequestModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;


    @Override
    public List<ArticleListResponseModel> getArticles(Date startDate, Date endDate, String boardName) {
        List<Article> articles = articleRepository.findArticlesWithOptions(startDate, endDate, boardName);

        return getArticleListResponseModels(articles);
    }

    //TODO modelmapper들 정리
    private List<ArticleListResponseModel> getArticleListResponseModels(List<Article> articles) {
        List<ArticleListResponseModel> returnValue = new ArrayList<>();

        articles.stream().forEach(article -> {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
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
        //board 저장
// boardRepository.save(new Board(articleModel.getBoardName()));
        //그에따른 article 저장
        //attachment 3개 저장

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Article article = modelMapper.map(articleModel, Article.class);
        List<Attachment> attachments = getAttachments(article);
        article.setBoard(new Board(articleModel.getBoardName()));
        article.setAttachments(attachments);

        Article savedArticle = articleRepository.save(article);

        ArticleDetailResponseModel returnValue = getArticleDetailResponseModel(savedArticle);

        return returnValue;
    }


    private ArticleDetailResponseModel getArticleDetailResponseModel(Article article) {
        ModelMapper modelMapper = new ModelMapper();
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
        Article article = articleRepository.getById(articleId);
        if(article==null){
            return null;
        }
        //조회수 올리기
        addViewCnt(article);

        return getArticleDetailResponseModel(article);
    }

    private void addViewCnt(Article article) {
        article.setViewCnt(article.getViewCnt()+1);
        articleRepository.save(article);
    }

    @Override
    public List<ArticleListResponseModel> findArticlesByBoard(Integer BoardId) {
        Board board = boardRepository.getById(BoardId);
        List<Article> articles = articleRepository.findByBoard(board);
        return getArticleListResponseModels(articles);
    }

    @Override
    public ArticleDetailResponseModel updateArticle(Integer articleId, UpdateArticleRequestModel article) {
//        Article articleToBeUpdated = articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId+"번 게시글이 존재하지 않습니다."));
        Article articleToBeUpdated = articleRepository.findById(articleId).get();
        articleToBeUpdated.setTitle(article.getTitle());
        articleToBeUpdated.setContent(article.getContent());
        Article updatedArticle = articleRepository.save(articleToBeUpdated);
        return getArticleDetailResponseModel(updatedArticle);
    }

    @Override
    public void deleteArticle(Integer articleId) {
//        articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId+"번 게시글이 존재하지 않습니다."));

        articleRepository.deleteById(articleId);

    }
}
