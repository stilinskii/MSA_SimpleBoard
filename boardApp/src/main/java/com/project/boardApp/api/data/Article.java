package com.project.boardApp.api.data;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "article")
@Getter
@Setter
@DynamicInsert
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "Text")
    private String content;

    @Column(name = "view_count", columnDefinition = "int default 0")
    private Integer viewCnt;

    @Column(name = "crated_datetime", nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
//    ,  updatable = false, insertable = false,
//            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    private Date createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Attachment> attachments;
}
