package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 유저 모델 정의.
 */
@Entity
@Table(name = "User")
@Getter
@Setter
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(length = 45, nullable = false)
    String email;
    @Column(length = 45, nullable = false)
    String name;
    @Column(length = 45, nullable = false)
    String birth;
    @Column(length = 45, nullable = false)
    String nick;
    @Column(length = 45, nullable = false)
    String phone;
    String profile;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 225, nullable = false)
    String password;

//    @ManyToMany
//    @JoinTable(name = "User_Group",
//                joinColumns = @JoinColumn(name = "USER_ID"),
//                inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
//    private List<Group> group = new ArrayList<Group>();
//
//    @ManyToMany
//    @JoinTable(name = "User_Meet",
//            joinColumns = @JoinColumn(name = "USER_ID"),
//            inverseJoinColumns = @JoinColumn(name = "Meet_ID"))
//    private List<Meet> meet = new ArrayList<Meet>();
}
