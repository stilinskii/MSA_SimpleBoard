package com.project.boardApp.api.ui.controllers;

import com.project.boardApp.api.ui.model.CreateArticleRequestModel;
import com.project.boardApp.api.ui.model.UpdateArticleRequestModel;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/board")
@RestController
public class BoardController {

    @GetMapping("/all")
    public String getArticles(@RequestParam(required = false, name = "start-date") String startDate,
                              @RequestParam(required = false, name = "end-date") String endDate){
        //게시글 모두 가져오기 , 생성날짜 기준으로 게시글 가져오기



        //전체 검색.
        return null;
    }

    @PostMapping
    public String createArticle(CreateArticleRequestModel article){
        //과제상에 파일 업로드 API는 없음으로 게시글 생성 시 목업 파일을 3개 씩 같이 생성한다. (location 필드는 가짜 데이터를 넣어도 무방)
        return null;
    }

    @GetMapping("/{articleId}")
    public String getArticle(@PathVariable Integer articleId){

        return null;
    }

    @PutMapping("/{articleId}")
    public String updateArticle(@PathVariable Integer articleId, UpdateArticleRequestModel article){
        //1.      게시글 제목, 게시글 내용만 수정이 가능하다.
        //
        //2.      똑같은 내용을 수정요청할 시 해당 요청은 무시되어야 한다.
        return null;
    }

    @DeleteMapping("/{articleId}")
    public String deleteArticle(@PathVariable Integer articleId){
        return null;
    }
}
