package com.ssafy.db.repository;

import com.ssafy.db.entity.Attendance;
import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.Meet;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {
    @Transactional
    List<Attendance> findAttendanceByUseridAndGroupid(User user, Group group);

    @Transactional
    Attendance findAttendanceByUseridAndMeetid(User user, Meet meet);

    List<Attendance> findAttendancesByMeetid(Meet meet);
}
