package com.project.boardApp.api.service;

import com.project.boardApp.api.data.ArticleRepository;
import com.project.boardApp.api.data.AttachmentRepository;
import com.project.boardApp.api.data.BoardRepository;
import com.project.boardApp.api.ui.model.ArticleDetailResponseModel;
import com.project.boardApp.api.ui.model.ArticleListResponseModel;
import com.project.boardApp.api.ui.model.CreateArticleRequestModel;
import com.project.boardApp.api.ui.model.UpdateArticleRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public List<ArticleListResponseModel> getArticles(String startDate, String endDate) {
        if(startDate!=null){
            if(endDate!=null){
                //시작일 끝일 같이 검색
            }else{
                //시작일만 검색
            }
        }else if(endDate != null){
            //끝일만 검색
        }
        return null;
    }

    @Override
    public ArticleDetailResponseModel saveArticle(CreateArticleRequestModel article) {
        return null;
    }

    @Override
    public ArticleDetailResponseModel getArticle(Integer articleId) {
        return null;
    }

    @Override
    public ArticleDetailResponseModel updateArticle(Integer articleId, UpdateArticleRequestModel article) {
        return null;
    }

    @Override
    public void deleteArticle(Integer articleId) {

    }
}
