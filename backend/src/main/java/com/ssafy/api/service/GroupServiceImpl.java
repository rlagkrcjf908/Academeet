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
        groupRepository.save(group);
        return group;
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
        User user = userRepository.findUserById(userId);
        List<User_Group> ug = user_groupRepository.findByUserid(user);
        if(ug==null)return null;
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
        groupRepository.deleteById(groupId);
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
        Group group = groupRepository.findGroupById(groupId);
        List<User_Group> ug = user_groupRepository.findByGroupid(group);
        List<User> user = new ArrayList<>();
        for (int i = 0; i<ug.size();i++){
            int id =ug.get(i).getUserid().getId();
            user.add( userRepositorySupport.findUserById(id).get());
        }
        return user;
    }

    @Override
    public List<Group> getGroupinMeet(int userId) {
        User user = userRepository.findUserById(userId);
        List<Group> groups = groupRepository.findGroupsByOwnerid(user);
        return groups;

    }

    private void groupUserDelete(int groupId, List<Integer> delUsers) {
        Group group = groupRepository.findGroupById(groupId);
        User_Group ug = user_groupRepository.findUser_GroupByGroupid(group);
        for (int i = 0; i < delUsers.size(); i++) {
            User user = userRepository.findUserById(delUsers.get(i));
            if(ug.getUserid().getId()==user.getId()){
                user_groupRepository.deleteById(ug.getId());
            }
        }

    }

    private void groupUserAdd(int groupId, List<Integer> users) {
        Group group = groupRepository.findGroupById(groupId);
        User_Group ug = user_groupRepository.findUser_GroupByGroupid(group);
        for (int i = 0; i < users.size(); i++) {
            User user = userRepository.findUserById(users.get(i));
            ug.setGroupid(group);
            ug.setUserid(user);
            user_groupRepository.save(ug);
        }

    }
}
