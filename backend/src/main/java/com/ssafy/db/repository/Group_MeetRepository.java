package com.ssafy.db.repository;

import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.Group_Meet;
import com.ssafy.db.entity.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface Group_MeetRepository extends JpaRepository<Group_Meet, Integer> {
    Group_Meet findGroup_MeetByMeetid(Meet meet);

    List<Group_Meet> findGroup_MeetsByGroupid(Group group);

    List<Group_Meet> findByGroupid(Group group);
    @Transactional
    void deleteGroup_MeetsByGroupid(Group group);
    @Transactional
    void deleteGroup_MeetsByMeetid(Meet meet);
}
