package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.QGroup;
import com.ssafy.db.entity.QUser_Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GroupRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QGroup qGroup = QGroup.group;

    QUser_Group qUser_group = QUser_Group.user_Group;
    public Optional<Group> findGroupById(int id){
        Group group = jpaQueryFactory.select(qGroup).from(qGroup)
                .where(qGroup.id.eq(id)).fetchOne();
        if (group ==null) return Optional.empty();
        return Optional.ofNullable(group);
    }
}
