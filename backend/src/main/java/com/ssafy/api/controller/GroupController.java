package com.ssafy.api.controller;

import com.ssafy.api.request.ArticlePostReq;
import com.ssafy.api.request.GroupCreatePostReq;
import com.ssafy.api.request.GroupUpdatePostReq;
import com.ssafy.api.response.*;
import com.ssafy.api.service.ArticleService;
import com.ssafy.api.service.AttendService;
import com.ssafy.api.service.GroupService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Group;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ArticleRepository;
import com.ssafy.db.repository.GroupRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(value = "인증 API", tags = {"Group."})
@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AttendService attendService;

    //그룹 API--------------------------------------------------------------------------
    //그룹생성
    @PostMapping("/{user_id}")
    @ApiOperation(value = "그룹 생성", notes = "그룹 이름과 사용자를 추가하세요")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> createGroup(@PathVariable("user_id") int userid,
                                                                  @RequestBody @ApiParam(value = "그룹 정보", required = true) GroupCreatePostReq createInfo) {

        if(groupRepository.findGroupByName(createInfo.getName())) return ResponseEntity.status(200).body(BaseResponseBody.of(401, "그룹명이 중복되었습니다."));
        Group group = groupService.createGroup(userid, createInfo);
        int group_id = group.getId();
        //그룹테이블이 만들어지면은 해당 그룹의 인원들의 데이터를 받아서 저장
        insertUserTOGroup(group_id, createInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));

    }

    // 그룹안에 사용자 insert
    public Map<String, Object> insertUserTOGroup(int group_id, GroupCreatePostReq createInfo) {
        List<User> users = createInfo.getUsers();
        int res = 0;
        for (int i = 0; i < users.size(); i++) {
            int user_id = users.get(i).getId();
            res += groupService.insertUserToGroup(group_id, user_id);
        }
        Map<String, Object> response = new HashMap<>();
        if (res == users.size() - 1) {
            response.put("result", "SUCCESS");
            //성공되었다는 값
        } else {
            //실패되었다는 값 보내기
            response.put("result", "FAIL");
        }

        return response;
    }


    // 그룹 리스트
    @GetMapping("/{id}/list") //user 로 변경
    @ApiOperation(value = "그룹 리스트 조회", notes = "회원 본인의 그룹리스트를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public List<Group> getGroupList(@ApiParam Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        int userId = userDetails.getUserId();
        List<Group> group = groupService.getGroupList(userId);
        return group;
    }

    // 선택한 그룹 정보 조회(ownerId일때)
    @GetMapping("/{group_id}") //user 로 변경
    @ApiOperation(value = "선택한 그룹 조회", notes = "선택한 그룹의 정보를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<GroupRes> getUserInfo(@PathVariable("group_id") int group_id) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우G에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        Group group = groupService.getGroupByGroupId(group_id);

        return ResponseEntity.status(200).body(GroupRes.of(group));
    }

    // 그룹 삭제
    @DeleteMapping("/{group_id}/delete")
    @ApiOperation(value = "선택한 그룹 삭제", notes = "선택한 그룹의 정보를 삭제한다.")

    public Map<String, Object> deleteGroup(@PathVariable("group_id") int group_id) {
        Map<String, Object> response = new HashMap<>();
        int res = groupService.deleteGroup(group_id);
        if (res > 1) {
            response.put("result", "SUCCESS");
            //성공되었다는 값
        } else {
            //실패되었다는 값 보내기
            response.put("result", "FAIL");
        }

        return response;
    }

    // 그룹 수정
    @PutMapping("/{group_id}/update")
    @ApiOperation(value = "선택한 그룹 수정", notes = "선택한 그룹의 정보를 수정한다.")
    public Map<String, Object> updateUserInfo(@PathVariable("group_id") int group_id, @RequestBody
    @ApiParam(value = "회원가입 정보", required = true) GroupUpdatePostReq groupUpdateInfo) {
        /**
         * 요청받은 해당 유저의 고유아이디에 변경되어진 정보를 받으면 수정
         * 받은정보가 하나라도 비었을시 오류 발생
         */
        Map<String, Object> response = new HashMap<>();
        int res = groupService.updateGroup(group_id, groupUpdateInfo);
        if (res > 0) {
            response.put("result", "SUCCESS");
            //성공되었다는 값
        } else {
            //실패되었다는 값 보내기
            response.put("result", "FAIL");
            response.put("reason", "일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요.");
        }

        return response;
    }

    //그룹 인원 API--------------------------------------------------------------------------

    // 그룹내 인원 추가(service)
    // 그룹내 인원 삭제(service)
    // 그룹내 인원 조회
    @GetMapping("/{group_id}/list")
    public List<User> getGroupUser(@PathVariable("group_id") int group_id) {
        List<User> user = groupService.getGroupUser(group_id);
        return user;
    }


    //그룹 관리자 API--------------------------------------------------------------------------

    //그룹 출석 조회(모든 사용자 list)
    @GetMapping("/{group_id}/attendInfo")
    public ResponseEntity<List<AttendGroupRes>> GroupAttendInfo(@PathVariable("group_id")int groupId){
        List<AttendGroupRes> agr = attendService.getGroupAttendInfo(groupId);
        return ResponseEntity.status(200).body(agr);
    }
    //그룹 내 회원 출석 상세정보
//    @GetMapping("/{group_id}/attendInfo/detail")
//    public ResponseEntity<List<AttendGroupRes>> GroupAttendInfo(@PathVariable("group_id")int groupId){
//        List<AttendGroupRes> agr = attendService.getGroupAttendInfo(groupId);
//        return ResponseEntity.status(200).body(agr);
//    }
    @GetMapping("/{group_id}/{user_id}/attend")
    @ApiOperation(value = "개인 출석률 확인", notes = "본인 출석률 및 미팅 리스트들을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<AttendRes>> getAttendance(@PathVariable("user_id")int userId, @PathVariable("group_id")int groupId){
        List<AttendRes> attend = attendService.getAttendance(userId,groupId);
        return ResponseEntity.status(200).body(attend);
    }
    //그룹 내 회원 출석 상세정보 수정
    @PutMapping("/{group_id}/{user_id}/update")
    public ResponseEntity<List<AttendGroupRes>> updateAttendance(@PathVariable("user_id")int userId, @PathVariable("group_id")int groupId,
    @RequestBody double att){
        List<AttendGroupRes> agr = attendService.getGroupAttendInfo(groupId);
        return ResponseEntity.status(200).body(agr);
    }
    //그룹 게시판 API------------------------------------------------------------------------

    @PostMapping("/{group_id/{user_id}/article")
    @ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> writeArticle(@PathVariable("group_id")int groupId,@PathVariable("user_id")int userId,
            @RequestBody @ApiParam(value = "게시글 정보.", required = true) ArticlePostReq articlePostReq){
        if (articleService.writeArticle(groupId,userId,articlePostReq)) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }
        return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Fail"));
    }

    @GetMapping("/{group_id/{user_id}/artlist")
    @ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.")
    public ResponseEntity<ArticleParamRes> listArticle(@PathVariable("group_id")int groupId,@PathVariable("user_id")int userId){
        return ResponseEntity.status(200).body(ArticleParamRes.of(articleService.listArticle(groupId,userId)));
    }

    @GetMapping("/{articleno}")
    @ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.")
    public ResponseEntity<ArticleRes> getArticle(@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno){
        return ResponseEntity.status(200).body(ArticleRes.of(articleService.getArticle(articleno)));
    }

    @PutMapping("/{articleno}/update")
    @ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    public ResponseEntity<? extends BaseResponseBody> updateArticle(@PathVariable("articleno")int articleno,@RequestBody @ApiParam(value = "수정할 글정보.", required = true) ArticlePostReq articlePostReq){
        if (articleService.updateArticle(articleno,articlePostReq)) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }
        return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Fail"));
    }

    @DeleteMapping("/{articleno}")
    @ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    public ResponseEntity<? extends BaseResponseBody> deleteArticle(@PathVariable("articleno") @ApiParam(value = "살제할 글의 글번호.", required = true) int articleno){
        if (articleService.deleteArticle(articleno)) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }
        return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Fail"));
    }
}
