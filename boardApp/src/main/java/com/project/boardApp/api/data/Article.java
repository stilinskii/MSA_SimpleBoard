package com.project.boardApp.api.data;


import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer board_id;
    private String title;
    private String content;

    @Column(name = "view_count")
    private Integer viewCnt;
    @Column(name = "crated_datetime")
    private LocalDateTime createdAt;
}
