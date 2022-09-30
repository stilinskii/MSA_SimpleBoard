package com.project.boardApp.api.ui.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ArticleDetailResponseModel {

    private String boardName;
    private String title;
    private Date createdAt;
    private List<String> locations;
}
