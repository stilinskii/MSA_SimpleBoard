package com.project.boardApp.api.ui.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleListResponseModel {
    private String boardName;
    private String title;
    private LocalDateTime createAt;
    private String location;

}
