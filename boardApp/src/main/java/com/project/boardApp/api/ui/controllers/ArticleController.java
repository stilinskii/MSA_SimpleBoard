package com.project.boardApp.api.ui.controllers;

import com.project.boardApp.api.service.ArticleService;
import com.project.boardApp.api.ui.model.ArticleDetailResponseModel;
import com.project.boardApp.api.ui.model.ArticleListResponseModel;
import com.project.boardApp.api.ui.model.CreateArticleRequestModel;
import com.project.boardApp.api.ui.model.UpdateArticleRequestModel;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//http://localhost:8090/swagger-ui.html
@RequestMapping("/article")
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
    }

    @GetMapping
    public ResponseEntity<List<ArticleListResponseModel>> getArticles(
                                                                      @Parameter(description = "검색 날짜 이후에 작성된 글 조회", example = "2022-10-03")
                                                                      @RequestParam(required = false, name = "start-date") Date startDate,
                                                                      @Parameter(description = "검색 날짜 이전에 작성된 글 조회", example = "2022-10-04")
                                                                      @RequestParam(required = false, name = "end-date") Date endDate,
                                                                      @Parameter(description = "게시판 이름으로 글 찾기. 게시판 이름 부분검색도 가능", example = "자유")
                                                                      @RequestParam(required = false, name = "board-name") String boardName){
        List<ArticleListResponseModel> articles = articleService.getArticles(startDate, endDate, boardName);

        return ResponseEntity.status(HttpStatus.OK).body(articles);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> createArticle(@Valid @RequestBody CreateArticleRequestModel article){
        ArticleDetailResponseModel articleDetailResponseModel = articleService.saveArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleDetailResponseModel);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleDetailResponseModel> getArticle(@PathVariable Integer articleId){
        ArticleDetailResponseModel article = articleService.getArticle(articleId);
        if(article !=null){
            return new ResponseEntity<>(article, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @PutMapping("/{articleId}")
    public ResponseEntity<ArticleDetailResponseModel> updateArticle(@PathVariable Integer articleId, @Valid @RequestBody UpdateArticleRequestModel article){
        ArticleDetailResponseModel updatedArticle = articleService.updateArticle(articleId, article);

        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<String> deleteArticle(@PathVariable Integer articleId){
         articleService.deleteArticle(articleId);

        return ResponseEntity.ok(articleId + "번 게시글이 삭제되었습니다.");
    }


}
