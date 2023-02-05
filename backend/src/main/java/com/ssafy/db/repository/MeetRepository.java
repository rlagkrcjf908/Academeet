package com.ssafy.db.repository;

import com.ssafy.db.entity.Meet;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetRepository extends JpaRepository<Meet,Integer> {
    Meet findMeetByTitle(String title);

    Meet findMeetById(int meetId);

    List<Meet> findMeetsByUserid(User user);
}
