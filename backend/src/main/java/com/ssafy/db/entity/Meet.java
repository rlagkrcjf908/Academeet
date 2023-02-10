package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "Meet")
@Getter
@Setter
public class Meet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userid;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groupid;
    @Column(length = 45, nullable = false)
    String title;
    //    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    Date date;
    //    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    Time starttime;
    //    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    Time endtime;

    @Column(columnDefinition = "TEXT")
    String chat;
    @Column(columnDefinition = "TEXT")
    String stt;
    @Column(columnDefinition = "TEXT")
    String video;
}
