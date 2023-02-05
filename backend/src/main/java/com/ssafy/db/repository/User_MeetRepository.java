package com.ssafy.db.repository;

import com.ssafy.db.entity.Meet;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.User_Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface User_MeetRepository extends JpaRepository<User_Meet,Integer> {
    List<User_Meet> findUser_MeetByUserid(User user);
    List<User_Meet> findByMeetid(Meet meet);
}
