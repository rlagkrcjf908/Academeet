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
    @JoinColumn(name = "GROUP_ID")
    private Group groupid;

    @ManyToOne
    @JoinColumn(name = "MEET_ID")
    private Meet meetid;
}
