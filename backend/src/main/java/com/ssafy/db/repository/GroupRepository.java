package com.ssafy.db.repository;

import com.ssafy.db.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {
    boolean findGroupByName(String name);

}
