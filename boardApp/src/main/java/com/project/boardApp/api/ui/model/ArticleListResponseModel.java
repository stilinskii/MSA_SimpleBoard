package com.project.boardApp.api.ui.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ArticleListResponseModel {
    private String boardName;
    private String title;
    private Date createdAt;
    private String location;

}
