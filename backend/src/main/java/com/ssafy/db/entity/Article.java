package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Article")
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userid;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Group groupid;

    @Column(name = "title", length = 45,nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String file;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    java.util.Date date;

}
