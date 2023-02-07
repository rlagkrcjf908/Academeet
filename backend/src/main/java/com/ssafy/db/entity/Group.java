package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "`Group`")
@Getter
@Setter
public class Group{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    int id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User ownerid;

    @Column(length = 45, nullable = false)
    String name;

//    @OneToMany(mappedBy = "groupid",cascade = CascadeType.REMOVE )
//    private List<User_Group> user_group = new ArrayList<>();
//
//    @OneToMany(mappedBy = "groupid",cascade = CascadeType.REMOVE )
//    private List<Group_Meet> group_meet = new ArrayList<>();

}
