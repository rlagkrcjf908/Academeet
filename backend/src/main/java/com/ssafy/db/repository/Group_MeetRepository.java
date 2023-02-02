package com.ssafy.db.repository;

import com.ssafy.db.entity.Group_Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Group_MeetRepository extends JpaRepository<Group_Meet, Integer> {
    Group_Meet findGroup_MeetByMeetid(int meetId);
}
