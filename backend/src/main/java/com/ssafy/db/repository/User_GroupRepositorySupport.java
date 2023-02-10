package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QUser_Group;
import com.ssafy.db.entity.User_Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class User_GroupRepositorySupport  {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QUser_Group qUser_group = QUser_Group.user_Group;

    @Autowired
    private UserRepositorySupport userRepositorySupport;
    public Optional<User_Group> findUserByGroupId(int id){
        User_Group ug = jpaQueryFactory.select(qUser_group).from(qUser_group)
                .where(qUser_group.groupid.id.eq(id)).fetchOne();
        if (ug ==null) return Optional.empty();
        return Optional.ofNullable(ug);
    }








}
