package com.ssafy.db.repository;

import com.ssafy.db.entity.User_Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface User_GroupRepository extends JpaRepository<User_Group,Long> {
    List<User_Group> findByUserid(int user_id);
    List<User_Group> findByGroupid(int group_id);
}
