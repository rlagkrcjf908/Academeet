package com.ssafy.api.service;

import com.ssafy.api.request.UserPassPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserMeetRes;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * 유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Transactional

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    User_MeetRepository userMeetRepository;

    @Autowired
    RedisTemplate redisTemplate;

    ValueOperations<String, String> operation;
    public static final String ePw = createKey();
    @Autowired
    private MeetRepository meetRepository;
    @Autowired
    private GroupRepositorySupport groupRepositorySupport;
    @Autowired
    private Group_MeetRepository group_MeetRepository;


    @Override
    public User createUser(UserRegisterPostReq userRegisterInfo) {
        // 회원가입을위한 정보를 저장
        User user = new User();
        user.setEmail(userRegisterInfo.getEmail());
        // 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
        user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
        user.setName(userRegisterInfo.getName());
        user.setBirth(userRegisterInfo.getBirth());
        user.setNick(userRegisterInfo.getNick());
        user.setPhone(userRegisterInfo.getPhone());
        if (userRegisterInfo.getProfile() == null) {
            return userRepository.save(user);
        } else {
            MultipartFile img = userRegisterInfo.getProfile();
            String imageFileName = img.getOriginalFilename();
            String path = "C:/Users/SSAFY/Pictures/";
            Path imagePath = Paths.get(path + imageFileName);
            try {
                Files.write(imagePath, img.getBytes());
                user.setProfile(imageFileName);

            } catch (Exception e) {

            }

            return userRepository.save(user);
        }
    }


    @Override
    public User getUserByUserId(String userEmail) {
        // 디비에 유저 정보 조회 (userEmail 를 통한 조회).
        User user = userRepositorySupport.findUserByUserId(userEmail).get();

        return user;
    }

    @Override
    public int updateUser(int id, UserRegisterPostReq registerInfo) {
        Optional<User> oUser = userRepository.findById(Long.valueOf(id));
        if (oUser.isPresent()) return 0;
        User user = new User();
        user.setEmail(registerInfo.getEmail());
        // 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
        user.setPassword(passwordEncoder.encode(registerInfo.getPassword()));
        user.setName(registerInfo.getName());
        user.setBirth(registerInfo.getBirth());
        user.setNick(registerInfo.getNick());
        user.setPhone(registerInfo.getPhone());
        MultipartFile img = registerInfo.getProfile();
        String path = "C:/Users/SSAFY/Pictures/";
        if (oUser.get().getProfile() == path + img.getOriginalFilename()) {
            userRepository.save(user);
            return 1;
        } else {
            String imageFileName = img.getOriginalFilename();
            Path imagePath = Paths.get(path + imageFileName);
            try {
                Files.write(imagePath, img.getBytes());
                user.setProfile(imageFileName);

            } catch (Exception e) {

            }
            user.setProfile(imageFileName);
        }
        userRepository.save(user);
        return 1;
    }

    private MimeMessage createMessage(String to) throws Exception {
        System.out.println("보내는 대상 : " + to);
        System.out.println("인증 번호 : " + ePw);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);//보내는 대상
        message.setSubject("Academeet회원가입 이메일 인증");//제목

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요 Academeet입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += ePw + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("ssafy@acdemeet.com", "Babble"));//보내는 사람

        return message;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    @Override
    public String sendSimpleMessage(String to) throws Exception {
        // TODO Auto-generated method stub
        MimeMessage message = createMessage(to);
//		 operation = redisTemplate.opsForValue();
        try {//예외처리
//			operation.set(ePw, to, 5*60000, TimeUnit.MILLISECONDS);
            emailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }

    @Override
    public int authEmail(String code) {
//		String key = operation.get(email);
        if (ePw == code) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        users = userRepository.findAll();
        return users;
    }

    @Override
    public User passCheck(int id, UserPassPostReq userPassPostReq) {
        User user = userRepositorySupport.findUserById(id).get();
        return user;
    }

    @Override
    public List<UserMeetRes> getUserMeetList(int userId) {
        User user = userRepositorySupport.findUserById(userId).get();
        List<User_Meet> um = userMeetRepository.findUser_MeetByUserid(user);
        List<UserMeetRes> umrs = new ArrayList<>();
        for (int i = 0; i< um.size();i++){
            Meet meet = meetRepository.findMeetById(um.get(i).getMeetid().getId());
            Group_Meet gm = group_MeetRepository.findGroup_MeetByMeetid(meet.getId());
            Group group = groupRepositorySupport.findGroupById(gm.getGroupid().getId()).get();
            UserMeetRes umr = new UserMeetRes();

            umr.setMeetId(meet.getId());
            umr.setGroupTitle(group.getName());
            umr.setMeetTitle(meet.getTitle());
            umr.setStartTime(meet.getStarttime());
            umr.setEndTime(meet.getEndtime());

            umrs.add(umr);

        }
        return umrs;
    }

    @Override
    public List<User> searchUser(String name) {
        List<User> users = userRepository.findUsersByName(name);
        return users;
    }


}

