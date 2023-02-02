package com.ssafy.api.controller;

import com.ssafy.api.request.MeetCreateReq;
import com.ssafy.api.request.MeetEndReq;
import com.ssafy.api.service.MeetService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.repository.MeetRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "유저 API", tags = {"Meet"})
@RestController
@RequestMapping("/api/v1/meet")
public class MeetController {
    @Autowired
    MeetService meetService;
    @Autowired
    MeetRepository meetRepository;

    // 화상방 생성
    @PostMapping("/{user_id}")
    @ApiOperation(value = "미팅 생성", notes = "미팅 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> createMeet(@PathVariable("user_id") int userId,
                                                                 @RequestBody @ApiParam(value = "미팅룸 정보", required = true) MeetCreateReq createReq) {
        if (meetService.createMeet(userId, createReq)) {
            meetService.insertMeetMember(userId, createReq);
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }
        return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Fail"));
    }
    //미팅룸 생성시 그룹오너이면 그룹리스트를 불러오고 아니면 말고

    //사용자 검색해야겠죠


    //화상방 종료
    @PostMapping("/{meet_id}/end")
    @ApiOperation(value = "미팅 종료", notes = "미팅종료 후 데이터 저장")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> endMeet(@PathVariable("meet_id") int meetId,
                                                              @RequestBody @ApiParam(value = "회원가입 정보", required = true) MeetEndReq endReq) {
        if (meetService.endMeet(meetId, endReq)) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }
        return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Fail"));
    }

}
