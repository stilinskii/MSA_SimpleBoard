package com.project.boardApp.api.ui.model;

import com.project.boardApp.api.data.Attachment;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CreateArticleRequestModel {

    private String boardName;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private ArrayList<Attachment> attachments;
}
