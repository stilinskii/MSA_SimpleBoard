package com.project.boardApp.api.data.repository;

import com.project.boardApp.api.data.Article;
import com.project.boardApp.api.data.repository.ArticleCustomRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.project.boardApp.api.data.QArticle.article;

@Repository
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

    @Autowired
    private JPAQueryFactory query;

    @Override
    public List<Article> findArticlesWithOptions(Date startDate, Date endDate, String boardName) {

        List<Article> result = query.selectFrom(article)
                .where(startDateTime(startDate),
                        endDateTime(endDate),
                        boardName(boardName))
                .fetch();

        return result;
    }

    private BooleanExpression boardName(String boardName) {
        return Objects.isNull(boardName) || boardName.isBlank()? null : article.board.name.contains(boardName);
    }

    private BooleanExpression startDateTime(Date startDate) {
        return Objects.isNull(startDate) ? null : article.createdAt.goe(startDate);
    }

    private BooleanExpression endDateTime(Date endDate) {
        return Objects.isNull(endDate) ? null : article.createdAt.loe(endDate);
    }
}
