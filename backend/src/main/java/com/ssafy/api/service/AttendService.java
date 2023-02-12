package com.ssafy.api.service;

import com.ssafy.api.request.AttendUpdateReq;
import com.ssafy.api.response.AttendGroupRes;
import com.ssafy.api.response.AttendRes;

import java.util.List;

public interface AttendService {
    List<AttendGroupRes> getGroupAttendInfo(int groupId);

    List<AttendRes> getAttendance(int userId, int groupId);

    boolean updateAttendance(int userId, int groupId, AttendUpdateReq attendUpdateReq);
}
