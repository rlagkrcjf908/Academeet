package com.ssafy.api.service;

import com.ssafy.api.response.AttendGroupRes;
import com.ssafy.api.response.AttendRes;
import com.ssafy.db.entity.Attendance;
import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.User_Group;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("attendService")

public class AttendServiceImpl implements AttendService{
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    User_MeetRepository userMeetRepository;
    @Autowired
    UserRepositorySupport userRepositorySupport;
    @Autowired
    GroupRepositorySupport groupRepositorySupport;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private User_GroupRepository user_GroupRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public List<AttendGroupRes> getGroupAttendInfo(int groupId) {
        List<AttendGroupRes> attendGroupRes = new ArrayList<>();
        Group gp  = groupRepository.findGroupById(groupId);
        List<User_Group> ug = user_GroupRepository.findByGroupid(gp);
        for (int i = 0; i<ug.size();i++){
            AttendGroupRes agr = new AttendGroupRes();
            int id = ug.get(i).getUserid().getId();
            double allatt = 0;
            double sumatt = 0;

            User user = userRepositorySupport.findUserById(id).get();
            Group group = groupRepositorySupport.findGroupById(groupId).get();

            List<Attendance> attendance = attendanceRepository.findAttendanceByUseridAndGroupid(user,group);
            for (int j = 0; j<attendance.size();j++){
                sumatt += attendance.get(i).getAttendance();
            }
            allatt = sumatt/attendance.size();

            agr.setUserId(id);
            agr.setName(user.getName());
            agr.setAllAtt(allatt);
            attendGroupRes.add(agr);
        }
        return attendGroupRes;
    }

    @Override
    public List<AttendRes> getAttendance(int userId, int groupId) {
        List<AttendRes> resList = new ArrayList<>();
        AttendRes res = new AttendRes();
        User user = userRepositorySupport.findUserById(userId).get();
        Group group =  groupRepositorySupport.findGroupById(groupId).get();

        //해당유저가 진행한 모든 미팅룸 번호
        List<Attendance> att = attendanceRepository.findAttendanceByUseridAndGroupid(user,group);
        //미팅룸 번호로 타이틀과 날짜과 미팅 그룹 사용자의 출석률
        for (int i = 0; i<att.size();i++){
            res.setTitle(att.get(i).getMeetid().getTitle());
            res.setDate(att.get(i).getMeetid().getDate());
            res.setAttendance(att.get(i).getAttendance());
            resList.add(res);
        }
        return resList;
    }
}
