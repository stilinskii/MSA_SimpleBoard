package com.project.boardApp.api.ui.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ArticleDetailResponseModel {

    //1.      게시판 이름
    //
    //2.      게시글 제목
    //
    //3.      생성 날짜
    //
    //4.      게시글 생성시 업로드된 모든 첨부파일의 경로

    private String boardName;
    private String title;
    private LocalDateTime createdAt;
    private ArrayList attachments;
}
