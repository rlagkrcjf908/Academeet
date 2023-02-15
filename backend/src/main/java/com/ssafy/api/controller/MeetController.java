package com.ssafy.api.controller;

import com.ssafy.api.request.*;
import com.ssafy.api.service.GroupService;
import com.ssafy.api.service.MeetService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.MeetRepository;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(value = "유저 API", tags = {"Meet"})
@RestController
@RequestMapping("/api/v1/meet")
//@CrossOrigin(origins="*")
public class MeetController {
    @Autowired
    MeetService meetService;
    @Autowired
    MeetRepository meetRepository;

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

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
        System.out.println(createReq.getTitle());
        System.out.println(createReq.getDate());
        System.out.println(createReq.getStarttime());
        System.out.println(createReq.getGroupid());
        if (meetService.createMeet(userId, createReq)) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }
        return ResponseEntity.status(403).body(BaseResponseBody.of(401, "Fail"));
    }
    //미팅룸 생성시 그룹오너이면 그룹리스트를 불러오고 아니면 말고
    @GetMapping("/{user_id}/getGroup")
    public ResponseEntity<List<Group>> getGroupinMeet( @PathVariable("user_id")int userId){

        List<Group> groups = groupService.getGroupinMeet(userId);
        return ResponseEntity.status(200).body(groups);
    }

    //사용자 검색해야겠죠
    @PostMapping("/search")
    public ResponseEntity<List<User>> searchUser(@RequestBody Map<String,String> nameMap){
        System.out.println(nameMap.get("name"));
        String name = nameMap.get("name");
        List<User> users = userService.searchUser(name);
        return ResponseEntity.status(200).body(users);
    }

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
                                                              @RequestBody @ApiParam(value = "회원가입 정보", required = true) MeetEndReq endReq) throws IOException {
        String str = String.valueOf(endReq.getEndtime());
        String get = new String();
        String[] splitData = str.split(":");
        for (int i = 0; i<1;i++){
            get += splitData[i];
        }
        int num = Integer.parseInt(get);
        num = num+9;
        if(num>=24) num = num-24;
        String set = String.valueOf(num);
        for (int i = 1; i<3;i++){
            set += ":"+splitData[i];
        }
        endReq.setEndtime(Time.valueOf(set));


        if (meetService.endMeet(meetId, endReq)) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Fail"));
    }

    @PostMapping("/{meet_id}/attend")
    public ResponseEntity<? extends BaseResponseBody> addAttendance(@PathVariable("meet_id") int meetid,
                                                                    @RequestBody AttendReq attendReq){
        if(meetService.addAttendance(meetid,attendReq)){
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }
        return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Fail"));
    }
    @PostMapping("/recognize")
    public ResponseEntity<? extends BaseResponseBody> recogtest(@RequestBody test test){
        for (int i = 0; i<test.getStt().length;i++){
            System.out.println(test.getStt()[i]);
        }
        if(meetService.recogtest(test.getStt())){
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"Success"));
        }
        return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Fail"));
    }
    @GetMapping("/test")
    public ResponseEntity<? extends BaseResponseBody> test(/*@RequestBody SttReq sttReq*/){
        SttReq sttReq = new SttReq();
        List<String> names = new ArrayList<>();
        names.add("이학철");
        names.add("asd");
        names.add("cvz");
        List<SttDetailReq> stt = new ArrayList<>();
        SttDetailReq sttDetailReq1 = new SttDetailReq();
        sttDetailReq1.setTime("11");
        sttDetailReq1.setName("김학철");
        sttDetailReq1.setStt("이게 뭐시랑께요?");
        stt.add(sttDetailReq1);
        SttDetailReq sttDetailReq2 = new SttDetailReq();
        sttDetailReq2.setTime("11");
        sttDetailReq2.setName("김학철");
        sttDetailReq2.setStt("이게 뭐시랑께요?");
        stt.add(sttDetailReq2);
        SttDetailReq sttDetailReq3 = new SttDetailReq();
        sttDetailReq3.setTime("12");
        sttDetailReq3.setName("김학철");
        sttDetailReq3.setStt("이게 뭐시랑께요?");
        stt.add(sttDetailReq3);
        SttDetailReq sttDetailReq4 = new SttDetailReq();
        sttDetailReq4.setTime("13");
        sttDetailReq4.setName("이학철");
        sttDetailReq4.setStt("이게 뭐시랑께요?");
        stt.add(sttDetailReq4);

        sttReq.setTitle("테스트 엑셀");
        sttReq.setName("학철");
        sttReq.setDate("2022-02-03");
        sttReq.setGroupName("test");
        sttReq.setUserName(names);
        sttReq.setStt(stt);
        meetService.makeExcelFile(sttReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"Success"));

    }

}
