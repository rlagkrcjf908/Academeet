package com.ssafy.api.service;

import com.ssafy.api.request.AttendUpdateReq;
import com.ssafy.api.response.AttendGroupRes;
import com.ssafy.api.response.AttendRes;
import com.ssafy.db.entity.*;
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
    @Autowired
    private MeetRepository meetRepository;

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

            User user = userRepository.findUserById(id);
            Group group = groupRepository.findGroupById(groupId);

            List<Attendance> attendance = attendanceRepository.findAttendanceByUseridAndGroupid(user,group);
            for (int j = 0; j<attendance.size();j++){
                System.out.println(attendance.get(j).getAttendance());
                if(attendance.get(j).getAttendance()>70){
                    sumatt += attendance.get(j).getAttendance();
                }
            }
            allatt = (sumatt/attendance.size())*100;
            agr.setUserId(id);
            agr.setName(user.getName());
            agr.setAllAtt(allatt);
            agr.setProfile(user.getProfile());
            attendGroupRes.add(agr);
        }
        return attendGroupRes;
    }

    @Override
    public List<AttendRes> getAttendance(int userId, int groupId) {
        User user = userRepository.findUserById(userId);
        Group group =  groupRepository.findGroupById(groupId);

        //해당유저가 진행한 모든 미팅룸 번호
        List<Attendance> att = attendanceRepository.findAttendanceByUseridAndGroupid(user,group);
        //미팅룸 번호로 타이틀과 날짜과 미팅 그룹 사용자의 출석률
        List<AttendRes> resList = new ArrayList<>();
        for (int i = 0; i<att.size();i++){
            Meet meet = att.get(i).getMeetid();
            AttendRes res = new AttendRes();
            res.setMeetId(meet.getId());
            res.setTitle(meet.getTitle());
            res.setDate(meet.getDate());
            res.setAttendance(att.get(i).getAttendance());
            resList.add(res);
        }

        return resList;
    }

    @Override
    public boolean updateAttendance(int userId, int groupId, AttendUpdateReq attendUpdateReq) {
        User user = userRepository.findUserById(userId);
        Group group =  groupRepository.findGroupById(groupId);
        Meet meet = meetRepository.findMeetById(attendUpdateReq.getMeetId());

        Attendance attendance = attendanceRepository.findAttendanceByUseridAndMeetid(user,meet);
        if(attendance == null) return false;
        System.out.println(attendance.getAttendance());
        attendance.setAttendance(attendUpdateReq.getAttendance());
        attendanceRepository.save(attendance);
        System.out.println(attendance.getAttendance());
        return true;
    }
}
