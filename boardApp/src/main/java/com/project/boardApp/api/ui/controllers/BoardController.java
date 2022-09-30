package com.project.boardApp.api.ui.controllers;

import com.project.boardApp.api.service.BoardService;
import com.project.boardApp.api.ui.model.ArticleDetailResponseModel;
import com.project.boardApp.api.ui.model.ArticleListResponseModel;
import com.project.boardApp.api.ui.model.CreateArticleRequestModel;
import com.project.boardApp.api.ui.model.UpdateArticleRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleListResponseModel>> getArticles(@RequestParam(required = false, name = "start-date") String startDate,
                                                                      @RequestParam(required = false, name = "end-date") String endDate){
        List<ArticleListResponseModel> articles = boardService.getArticles(startDate, endDate);

        return ResponseEntity.status(HttpStatus.OK).body(articles);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ArticleDetailResponseModel> createArticle(@RequestBody CreateArticleRequestModel article){
        ArticleDetailResponseModel articleDetailResponseModel = boardService.saveArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleDetailResponseModel);
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<ArticleDetailResponseModel> getArticles(@PathVariable Integer articleId){
        ArticleDetailResponseModel article = boardService.getArticle(articleId);
        if(article !=null){
            return new ResponseEntity<>(article, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

//고치기
    @GetMapping(value = {"/board/{boardId}/","/board/{boardId}/{articleId}"})
    public ResponseEntity<List<ArticleListResponseModel>> getArticlesByBoard(@PathVariable Integer boardId, @PathVariable(required = false) Integer articleId){
        List<ArticleListResponseModel> articles = boardService.findArticlesByBoard(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(articles);
    }

    @PutMapping("/{articleId}")
    public String updateArticle(@PathVariable Integer articleId, UpdateArticleRequestModel article){
        //1.      게시글 제목, 게시글 내용만 수정이 가능하다.
        //
        //2.      똑같은 내용을 수정요청할 시 해당 요청은 무시되어야 한다.
        return null;
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity deleteArticle(@PathVariable Integer articleId){
        boardService.deleteArticle(articleId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
