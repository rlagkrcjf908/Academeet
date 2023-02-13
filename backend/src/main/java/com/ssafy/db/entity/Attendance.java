package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Attendance")
@Getter
@Setter
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userid;
    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private Group groupid;
    @ManyToOne
    @JoinColumn(name = "MEET_ID")
    private Meet meetid;

    @Column(nullable = false)
    double attendance;

    @Column
    private Integer attendcount;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    java.util.Date date;
}
