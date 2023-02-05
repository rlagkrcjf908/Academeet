package com.ssafy.api.service;

import com.ssafy.api.request.MeetCreateReq;
import com.ssafy.api.request.MeetEndReq;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("meetService")
public class MeetServiceImpl implements MeetService {
    @Autowired
    UserRepositorySupport userRepositorySupport;
    @Autowired
    MeetRepository meetRepository;
    @Autowired
    GroupRepositorySupport groupRepositorySupport;
    @Autowired
    private User_MeetRepository user_MeetRepository;
    @Autowired
    private User_GroupRepository user_GroupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private Group_MeetRepository group_MeetRepository;

    @Override
    public boolean createMeet(int userId, MeetCreateReq createReq) {
        Meet meet = new Meet();
        User user = userRepository.findUserById(userId);
        Group gp = groupRepository.findGroupById(createReq.getGroupid());
        System.out.println(gp);
        if (gp!=null) {
            if (meetRepository.findMeetByTitle(createReq.getTitle()) != null) return false;
            meet.setTitle(createReq.getTitle());
            meet.setDate(createReq.getDate());
            meet.setStarttime(createReq.getStarttime());
            meet.setEndtime(createReq.getEndtime());
            meet.setUserid(user);
            meet.setGroupid(gp);
            List<User_Group> ug = user_GroupRepository.findByGroupid(gp);
            List<Integer> users = new ArrayList<>();
            for (int i = 0; i<ug.size();i++){
                int getId = ug.get(i).getUserid().getId();
                users.add(getId);
            }

            createReq.setUsers(users);
            meetRepository.save(meet);

            Group_Meet gm = new Group_Meet();
            gm.setGroupid(gp);
            gm.setMeetid(meetRepository.findMeetByTitle(createReq.getTitle()));

            group_MeetRepository.save(gm);
            insertMeetMember(userId, createReq);
            return true;
        }else {
            if (meetRepository.findMeetByTitle(createReq.getTitle()) != null) return false;
            meet.setTitle(createReq.getTitle());
            meet.setDate(createReq.getDate());
            meet.setStarttime(createReq.getStarttime());
            meet.setEndtime(createReq.getEndtime());
            meet.setUserid(user);

            meetRepository.save(meet);
            insertMeetMember(userId, createReq);

            return true;
        }
    }

    @Override
    public void insertMeetMember(int userId, MeetCreateReq createReq) {
        User user = userRepository.findUserById(userId);
        Meet meet = meetRepository.findMeetByTitle(createReq.getTitle());
        Group group = groupRepository.findGroupById(createReq.getGroupid());
        if(group != null) {
            List<User_Group> ugs = user_GroupRepository.findByGroupid(group);
            for (int i = 0; i< ugs.size();i++){
                User_Meet um = new User_Meet();
                um.setMeetid(meet);
                um.setUserid(ugs.get(i).getUserid());
                user_MeetRepository.save(um);
            }

        }
        if(user.getId()==meet.getUserid().getId()){
            List<Integer> users = createReq.getUsers();
            for (int i = 0; i<users.size();i++){
                User_Meet um = new User_Meet();
                User addUser = userRepository.findUserById(users.get(i));
                um.setMeetid(meet);
                um.setUserid(addUser);
                user_MeetRepository.save(um);
            }
        }

    }

    @Override
    public boolean endMeet(int meetId, MeetEndReq endReq) {
        Meet meet = meetRepository.findMeetById(meetId);
        List<User_Meet> um = user_MeetRepository.findByMeetid(meet);

        if(meet == null) return false;
        meet.setEndtime(endReq.getEndtime());
        meet.setChat(endReq.getChat());
        meet.setStt(endReq.getStt());
        meet.setVideo(endReq.getVideo());
        meetRepository.save(meet);
        // 출석율 저장 개인의 출석율을 저장하는 것
//        for (int i = 0; i<um.size();i++){
//
//        }
        return true;
    }
}
