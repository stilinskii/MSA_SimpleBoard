package com.project.boardApp.api.exception;

import com.project.boardApp.api.ui.model.ErrorMessage;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.*;



@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    //모든에러
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessageDescription = ex.getLocalizedMessage() == null ? ex.toString() : ex.getLocalizedMessage();

        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), INTERNAL_SERVER_ERROR);
    }

    //날짜 형식 예외
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {

        if (isNotDateTypeMissmatch(ex)) {
            return getNormalObjectResponseEntity(ex);
        }

        ErrorMessage errorMessage = new ErrorMessage(new Date(), "날짜형식을 제대로 입력해주세요. ex)2022-10-03");

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), NOT_FOUND);
    }

    private ResponseEntity<Object> getNormalObjectResponseEntity(Exception ex) {
        String errorMessageDescription = ex.getLocalizedMessage() == null ? ex.toString() : ex.getLocalizedMessage();
        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), NOT_FOUND);
    }

    private boolean isNotDateTypeMissmatch(MethodArgumentTypeMismatchException ex) {
        return !ex.getCause().getMessage().contains("Unparseable date");
    }

    //유효성검사 필드에러
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return new ResponseEntity<>(processFieldErrors(fieldErrors), new HttpHeaders(), BAD_REQUEST);
    }

    private ErrorMessage processFieldErrors(List<FieldError> fieldErrors) {
        ErrorMessage error = new ErrorMessage (new Date(),"validation error");
        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            error.addFieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return error;
    }


    //없는 게시글 조회,수정,삭제 요청시 호출되는 에러 handler
    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleNoSuchElementException(Exception ex, WebRequest request){
        String uri = ((ServletWebRequest) request).getRequest().getRequestURI();

        if(isNotFromFindArticle(uri)){
            return getNormalObjectResponseEntity(ex);
        }

        String articleNum = getArticleNum(uri);
        ErrorMessage errorMessage = new ErrorMessage(new Date(),articleNum+"번 게시글이 존재하지 않습니다.");

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), NOT_FOUND);
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
