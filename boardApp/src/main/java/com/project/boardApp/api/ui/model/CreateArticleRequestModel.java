package com.project.boardApp.api.ui.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateArticleRequestModel {

    private String boardName;
    private String title;
    private String content;
//    private LocalDateTime createdAt;
//    private List<Attachment> attachments;
}
