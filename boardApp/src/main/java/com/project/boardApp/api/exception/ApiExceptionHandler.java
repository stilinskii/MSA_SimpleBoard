package com.project.boardApp.api.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {




    @ExceptionHandler(value =
            {NoSuchElementException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleNoSuchElementException(Exception ex, WebRequest request){
//        String articleId = request.getParameter("articleId");
        String uri = ((ServletWebRequest) request).getRequest().getRequestURI();

        boolean matches = uri.matches("^*/article/[0-9]*/?");

        if(!matches){
            String errorMessageDescription = ex.getLocalizedMessage() == null ? ex.toString(): ex.getLocalizedMessage();
            return new ResponseEntity<>(errorMessageDescription, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("게시글이 존재하지 않습니다.", new HttpHeaders(),HttpStatus.NOT_FOUND);
    }
}
