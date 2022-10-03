package com.project.boardApp.api.ui.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorMessage {
    private Date timestamp;
    private String message;

    private List<FieldError> fieldErrors = new ArrayList<>();

    public ErrorMessage(Date date, String errorMessageDescription) {
        this.timestamp=date;
        this.message=errorMessageDescription;
    }

    public void addFieldError(String objectName, String path, String message) {
        FieldError error = new FieldError(objectName, path, message);
        fieldErrors.add(error);
    }


}
