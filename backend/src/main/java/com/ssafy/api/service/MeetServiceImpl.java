package com.ssafy.api.service;

import com.ssafy.api.request.MeetCreateReq;
import com.ssafy.api.request.MeetEndReq;
import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.Meet;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.User_Meet;
import com.ssafy.db.repository.GroupRepositorySupport;
import com.ssafy.db.repository.MeetRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import com.ssafy.db.repository.User_MeetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean createMeet(int userId, MeetCreateReq createReq) {
        Meet meet = new Meet();
        User user = userRepositorySupport.findUserById(userId).get();
        List<Group> groupList = createReq.getGroups();
        if (groupList.size() < 2) {
            if (meetRepository.findMeetByTitle(createReq.getTitle()) != null) return false;

            meet.setTitle(createReq.getTitle());
            meet.setDate(createReq.getDate());
            meet.setEndtime(createReq.getEndtime());
            meet.setUserid(user);
            meet.setGroupid(groupList.get(0));
            meetRepository.save(meet);
            return true;
        }
        if (meetRepository.findMeetByTitle(createReq.getTitle()) != null) return false;
        meet.setTitle(createReq.getTitle());
        meet.setDate(createReq.getDate());
        meet.setEndtime(createReq.getEndtime());
        meet.setUserid(user);
        meetRepository.save(meet);
        return true;

    }

    @Override
    public void insertMeetMember(int userId, MeetCreateReq createReq) {
        User user = userRepositorySupport.findUserById(userId).get();
        Meet meet = meetRepository.findMeetByTitle(createReq.getTitle());
        User_Meet um = new User_Meet();
        if(user.getId()==meet.getId()){
            List<User> users = createReq.getUsers();
            for (int i = 0; i<users.size();i++){
                um.setMeetid(meet);
                um.setUserid(users.get(i));
                user_MeetRepository.save(um);
            }
        }

    }

    @Override
    public boolean endMeet(int meetId, MeetEndReq endReq) {
        Meet meet = meetRepository.findMeetByUserid(meetId);
        if(meet == null) return false;
        meet.setEndtime(endReq.getEndtime());
        meet.setChat(endReq.getChat());
        meet.setStt(endReq.getStt());
        meet.setVideo(endReq.getVideo());
        meetRepository.save(meet);
        return true;
    }
}
