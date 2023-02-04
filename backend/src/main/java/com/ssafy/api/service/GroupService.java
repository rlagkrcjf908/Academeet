package com.ssafy.api.service;

import com.ssafy.api.request.GroupCreatePostReq;
import com.ssafy.api.request.GroupUpdatePostReq;
import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.User;

import java.util.List;

public interface GroupService {
    Group createGroup(int id, GroupCreatePostReq groupCreatePostReq);

    int insertUserToGroup(int groupId, int userId);

    List<Group> getGroupList(int userId);

    Group getGroupByGroupId(int groupId);

    int deleteGroup(int groupId);

    int updateGroup(int groupId, GroupUpdatePostReq groupUpdateInfo);

    List<User> getGroupUser(int groupId);

    List<Group> getGroupinMeet(int userId);
}
