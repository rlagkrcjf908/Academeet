package com.ssafy.api.service;

import com.ssafy.api.request.MeetCreateReq;
import com.ssafy.api.request.MeetEndReq;

import java.io.IOException;

public interface MeetService {
    boolean createMeet(int userId,MeetCreateReq createReq);

    void insertMeetMember(int userId, MeetCreateReq createReq);

    boolean endMeet(int meetId, MeetEndReq endReq) throws IOException;
}
