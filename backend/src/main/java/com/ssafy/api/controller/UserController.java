package com.ssafy.api.controller;

import com.ssafy.api.request.UserIdCheckReq;
import com.ssafy.api.request.UserPassPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdatePostReq;
import com.ssafy.api.response.UserAllRes;
import com.ssafy.api.response.UserMeetRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.AttendService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.MeetRepository;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    AttendService attendService;
    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    UserRepository userRepository;
    @Autowired
    private MeetRepository meetRepository;

    //회원가입
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<User> register(
            @RequestPart @ApiParam(value = "회원가입 정보", required = true) UserRegisterPostReq registerInfo, @RequestPart MultipartFile profile) {

        User user = userService.createUser(registerInfo,profile);
        return ResponseEntity.status(200).body(user);
    }

    @GetMapping("/me") //user 로 변경
    @ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<UserRes> getUserInfo(/*@ApiIgnore Authentication authentication*/) throws MalformedURLException {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */

      /*  SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        String userEmail = userDetails.getUsername();
        System.out.println(userEmail);*/
        String userEmail = "rlagkrcjf6@naver.com";
        User user = userService.getUserByUserId(userEmail);
        return ResponseEntity.status(200).body(UserRes.of(user));
    }

    // 사용자 전체 조회
    @GetMapping("/all")
    @ApiOperation(value = "회원 전체 정보 조회", notes = "회원들의 전체 정보를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<UserAllRes> getAllUserInfo() throws MalformedURLException {
        List<UserRes> users = userService.getAllUser();
        return ResponseEntity.status(200).body(UserAllRes.of(users));
    }

    //회원 정보 수정
    @PutMapping("/{id}/update") //user 로 변경
    @ApiOperation(value = "회원 본인 정보 수정", notes = "로그인한 회원 본인의 정보를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> updateUserInfo(@PathVariable("id") int id, @RequestPart
    @ApiParam(value = "회원가입 정보", required = true) UserUpdatePostReq updateInfo,@RequestPart MultipartFile profile) {
        /**
         * 요청받은 해당 유저의 고유아이디에 변경되어진 정보를 받으면 수정
         * 받은정보가 하나라도 비었을시 오류 발생
         */

        int res = userService.updateUser(id, updateInfo,profile);
        System.out.println(updateInfo.getEmail());
        if (res > 0) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }
        return ResponseEntity.status(403).body(BaseResponseBody.of(403,"Fail"));
    }

    // 입력한 비밀번호 확인
    @PostMapping("/{id}/pwinput")
    @ApiOperation(value = "비밀번호 확인", notes = "비밀번호 변경시 현재비밀번호와 동일한지 확인한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public boolean passCheck(@PathVariable("id") int id, @RequestBody @ApiParam(value = "password")
    UserPassPostReq userPassPostReq) {
        Map<String, Object> response = new HashMap<>();
        User user = userService.passCheck(id, userPassPostReq);
        String password = userPassPostReq.getPassword();
        System.out.println(id);
        System.out.println(password);
        System.out.println(passwordEncoder.matches(password,user.getPassword()));
        int res = 0;
        if (passwordEncoder.matches(password,user.getPassword())) {
            res += 1;
        }
        if (res > 0) {
            return true;
            //성공되었다는 값
        } else {
            return false;
        }


    }

    // 비밀번호 변경
    @PutMapping("/{id}/pwchange")
    @ApiOperation(value = "비밀번호 변경", notes = "비밀번호 변경시 현재비밀번호와 동일한지 확인한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public Map<String, Object> passChange(@PathVariable("id") int id, @RequestBody @ApiParam(value = "password")
    UserPassPostReq userPassPostReq) {
        Map<String, Object> response = new HashMap<>();

        if ( userService.passChange(id, userPassPostReq.getPassword())) {
            response.put("result", "SUCCESS");
            //성공되었다는 값
        } else {
            //실패되었다는 값 보내기
            response.put("result", "FAIL");
        }

        return response;
    }

//	// 개인 출석 조회
//	@GetMapping("/{group_id}/{user_id}/attend")
//	@ApiOperation(value = "개인 출석률 확인", notes = "본인 출석률 및 미팅 리스트들을 조회한다.")
//	@ApiResponses({
//			@ApiResponse(code = 200, message = "성공"),
//			@ApiResponse(code = 401, message = "인증 실패"),
//			@ApiResponse(code = 404, message = "사용자 없음"),
//			@ApiResponse(code = 500, message = "서버 오류")
//	})
//	public ResponseEntity<List<AttendRes>> getAttendance(@PathVariable("user_id")int userId,@PathVariable("group_id")int groupId){
//		List<AttendRes> attend = attendService.getAttendance(userId,groupId);
//		return ResponseEntity.status(200).body(attend);
//	}

    /**
     * 개인의 고유 아이디와 그룹 아이디 미팅룸 아이디를 데이터를 받아와서 해당 출석율을 계산하여 값을 보낸다
     * 값을 받을때 출석률 값을 구하는 알고리즘 작성해야합니다.
     */
    // 회원가입 시 이메일 인증
    @PutMapping("/email")
    @ApiOperation(value = "회원 가입시 이메인 인증", notes = "기존사용하고 있는 이메일을 통해 인증")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> emailConfirm(@RequestBody @ApiParam(value="email", required = true)  String email) throws Exception {

        System.out.println(email);
        String confirm = userService.sendSimpleMessage(email);
        System.out.println(confirm);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, confirm));
    }

    // 입력한 이메일 인증코드가 맞는지 확인
    @PostMapping("/emailCheck")
    @ApiOperation(value = "이메일 인증 확인", notes = "이메일 인증번호가 일치한지 확인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> authEmail(@RequestBody String code) {
        /**
         * 단순 생성된 코드를 비교하는 것이지만
         */
        System.out.println(code);
        int check = userService.authEmail(code);
        if (check > 0) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
        }
    }

    // 아이디 중복체크
    /**
     * 데이터들의 중복이 있을 시  즉각적으로 확인 후 true or false값 전송
     */
    @PostMapping("/login/idCheck")
    @ResponseBody
    public int idCheck(@RequestBody UserIdCheckReq userIdCheckReq) {
        System.out.println(userIdCheckReq.getEmail());
        if (userRepository.findUserByEmail(userIdCheckReq.getEmail()) == null) return 1;
        return 0;
    }
    // 자신의 미팅리스트 불러오기
    @GetMapping("/{user_id}/meetList")
    public ResponseEntity<List<UserMeetRes>> getUserMeetList(@PathVariable("user_id")int userId){

        List<UserMeetRes> umr = userService.getUserMeetList(userId);
        return ResponseEntity.status(200).body(umr);
    }
}

//회의 List
/*
* 그룹별? 당일 수업: /그룹이름 /회의이름 /회의시간 /종료시간 / 회의고유번호
* */