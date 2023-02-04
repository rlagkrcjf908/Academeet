package com.ssafy.db.repository;

import com.ssafy.db.entity.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetRepository extends JpaRepository<Meet,Integer> {
    Meet findMeetByTitle(String title);

    Meet findMeetById(int meetId);
}
