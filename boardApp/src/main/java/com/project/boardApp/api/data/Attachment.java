package com.project.boardApp.api.data;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "attachment")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer article_id;
    private String location;

    @Column(name = "crated_datetime")
    private LocalDateTime createdAt;
}
