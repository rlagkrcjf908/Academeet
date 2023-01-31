package com.ssafy.api.service;

import com.ssafy.api.request.UserPassPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserId(String useremail);

	int updateUser(int id,UserRegisterPostReq registerInfo);

	String sendSimpleMessage(String to)throws Exception;


	int authEmail(String code);

    List<User> getAllUser();

    User passCheck(int id, UserPassPostReq userPassPostReq);
}
