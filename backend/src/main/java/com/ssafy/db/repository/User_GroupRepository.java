package com.ssafy.db.repository;

import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.User_Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface User_GroupRepository extends JpaRepository<User_Group,Integer> {
    List<User_Group> findByUserid(User user);
    List<User_Group> findByGroupid(Group group);

    @Transactional
    void deleteUser_GroupsByGroupid(Group group);

    User_Group findUser_GroupByGroupid(Group group);
}
