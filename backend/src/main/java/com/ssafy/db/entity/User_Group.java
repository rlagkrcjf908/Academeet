package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "User_Group")
@Getter
@Setter
public class User_Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userid;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groupid;
}
