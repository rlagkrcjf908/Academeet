package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Group_Meet")
@Getter
@Setter
public class Group_Meet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groupid;

    @ManyToOne
    @JoinColumn(name = "meet_id")
    private Meet meetid;
}
