package com.project.boardApp.api.ui.controllers;

import com.project.boardApp.api.service.ArticleService;
import com.project.boardApp.api.ui.model.ArticleDetailResponseModel;
import com.project.boardApp.api.ui.model.ArticleListResponseModel;
import com.project.boardApp.api.ui.model.CreateArticleRequestModel;
import com.project.boardApp.api.ui.model.UpdateArticleRequestModel;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/article")
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
    }

    @GetMapping
    public ResponseEntity<List<ArticleListResponseModel>> getArticles(@RequestParam(required = false, name = "start-date") Date startDate,
                                                                      @RequestParam(required = false, name = "end-date") Date endDate,
                                                                      @RequestParam(required = false, name = "board-name") String boardName){
        List<ArticleListResponseModel> articles = articleService.getArticles(startDate, endDate, boardName);
//TODO 날짜형식 유효성검사
        return ResponseEntity.status(HttpStatus.OK).body(articles);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ArticleDetailResponseModel> createArticle(@RequestBody CreateArticleRequestModel article){
        //TODO create 유효성검사
        ArticleDetailResponseModel articleDetailResponseModel = articleService.saveArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleDetailResponseModel);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleDetailResponseModel> getArticles(@PathVariable Integer articleId){
        ArticleDetailResponseModel article = articleService.getArticle(articleId);
        if(article !=null){
            return new ResponseEntity<>(article, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @PutMapping(
            value = "/{articleId}"
            )
    public ResponseEntity<ArticleDetailResponseModel> updateArticle(@PathVariable Integer articleId, @RequestBody UpdateArticleRequestModel article){
        //1.      게시글 제목, 게시글 내용만 수정이 가능하다.
        //
        //2.      똑같은 내용을 수정요청할 시 해당 요청은 무시되어야 한다.
        //TODO update 유효성검사 빈칸이면 안되고, 같은 내용이면 안됨. = JPA에서 같은내용이면 알아서 안함.

        ArticleDetailResponseModel updatedArticle = articleService.updateArticle(articleId, article);

        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

    @DeleteMapping("/{articleId}")
    public void deleteArticle(@PathVariable Integer articleId){
         articleService.deleteArticle(articleId);

//        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
