package com.project.boardApp.api.ui.model;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateArticleRequestModel {

    @NotNull
    @Size(min=1, message = "한 글자 이상 입력해주세요.")
    private String title;

    @NotNull
    @Size(min=1, message = "한 글자 이상 입력해주세요.")
    private String content;

}
