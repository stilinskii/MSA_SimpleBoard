package com.project.boardApp.api.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Setter
public class Board {

    public Board() {
    }

    public Board(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "board")
    private List<Article> articles;
}
