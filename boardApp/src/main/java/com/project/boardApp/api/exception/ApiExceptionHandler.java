package com.project.boardApp.api.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    //없는 게시글 조회,수정,삭제 요청시 호출되는 에러 handle
    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleNoSuchElementException(Exception ex, WebRequest request){
        String uri = ((ServletWebRequest) request).getRequest().getRequestURI();

        if(isNotFromFindArticle(uri)){
            String errorMessageDescription = ex.getLocalizedMessage() == null ? ex.toString(): ex.getLocalizedMessage();
            return new ResponseEntity<>(errorMessageDescription, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }

        String articleNum = getArticleNum(uri);

        return new ResponseEntity<>(articleNum+"번 게시글이 존재하지 않습니다.", new HttpHeaders(),HttpStatus.NOT_FOUND);
    }

    private String getArticleNum(String uri) {
        String[] uriSplit = uri.split("/");
        return uriSplit[2];
    }

    private boolean isNotFromFindArticle(String uri) {
        // /article/{articleId} 인지 체크
        boolean matches = uri.matches("^*/article/[0-9]*/?");
        return !matches;
    }
}
