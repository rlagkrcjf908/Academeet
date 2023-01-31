package com.ssafy.api.service;

import com.ssafy.api.request.GroupCreatePostReq;
import com.ssafy.api.request.GroupUpdatePostReq;
import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.User_Group;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("groupService")
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupRepositorySupport groupRepositorySupport;
    @Autowired
    private UserRepositorySupport userRepositorySupport;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private User_GroupRepository user_groupRepository;

    @Autowired
    private User_GroupRepositorySupport userGroupRepositorySupport;

    private EntityManager em;
    @Override
    public Group createGroup(int id, GroupCreatePostReq groupCreatePostReq) {
        Group group = new Group();
        User user = userRepositorySupport.findUserById(id).get();
        group.setOwnerid(user);
        group.setName(groupCreatePostReq.getName());

        return groupRepository.save(group);
    }

    @Override
    public int insertUserToGroup(int groupId, int userId) {

        User_Group ug = new User_Group();
        Optional<Group> ogroup = groupRepositorySupport.findGroupById(groupId);
        Optional<User> ouser = userRepositorySupport.findUserById(userId);
        ug.setGroupid(ogroup.get());
        ug.setUserid(ouser.get());
        user_groupRepository.save(ug);
        return 1;
    }

    @Override
    public List<Group> getGroupList(int userId) {
        List<User_Group> ug = user_groupRepository.findByUserid(userId);
        List<Group> group = new ArrayList<>();
        for (int i = 0; i < ug.size(); i++) {
            int gid = ug.get(i).getGroupid().getId();
            group.add(groupRepositorySupport.findGroupById(gid).get());
        }
        return group;
    }

    @Override
    public Group getGroupByGroupId(int groupId) {
        Group group = groupRepositorySupport.findGroupById(groupId).get();
        return group;
    }

    @Override
    public int deleteGroup(int groupId) {
        Optional<Group> ogroup = groupRepositorySupport.findGroupById(groupId);
        if (ogroup == null) {
            return 0;
        }
        groupRepository.deleteById(Long.valueOf(groupId));
        return 1;
    }

    @Override
    public int updateGroup(int groupId, GroupUpdatePostReq groupUpdateInfo) {
        Optional<Group> ogroup = groupRepositorySupport.findGroupById(groupId);
        if (ogroup.isPresent()) return 0;
        Group group = new Group();
        group.setName(groupUpdateInfo.getName());
        //사용자 리스트 수정
        groupUserAdd(groupId, groupUpdateInfo.getAddUsers());
        groupUserDelete(groupId, groupUpdateInfo.getDelUsers());

        return 1;

    }

    @Override
    public List<User> getGroupUser(int groupId) {
        List<User_Group> ug = user_groupRepository.findByGroupid(groupId);
        List<User> user = new ArrayList<>();
        for (int i = 0; i<ug.size();i++){
            int id =ug.get(i).getUserid().getId();
            user.add( userRepositorySupport.findUserById(id).get());
        }
        return user;
    }

    private void groupUserDelete(int groupId, List<User> delUsers) {
        User_Group ug = userGroupRepositorySupport.findUserByGroupId(groupId).get();
        for (int i = 0; i < delUsers.size(); i++) {
            User user = userRepositorySupport.findUserById(delUsers.get(i).getId()).get();
            if(ug.getUserid().getId()==user.getId()){
                user_groupRepository.deleteById(Long.valueOf(ug.getId()));
            }
        }

    }

    private void groupUserAdd(int groupId, List<User> users) {
        User_Group ug = new User_Group();
        Optional<Group> ogroup = groupRepository.findById(Long.valueOf(groupId));
        for (int i = 0; i < users.size(); i++) {
            User user = userRepositorySupport.findUserById(users.get(i).getId()).get();
            ug.setGroupid(ogroup.get());
            ug.setUserid(user);
            user_groupRepository.save(ug);
        }

    }
}
