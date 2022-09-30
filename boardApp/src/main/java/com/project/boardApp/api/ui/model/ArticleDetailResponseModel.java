package com.project.boardApp.api.ui.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
public class ArticleDetailResponseModel {

    private String boardName;
    private String title;
    private LocalDateTime createdAt;
    private ArrayList<String> locations;
}
