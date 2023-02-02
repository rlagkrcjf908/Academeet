package com.ssafy.api.response;

import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserRes{
	@ApiModelProperty(name="User Email")
	String email;
	@ApiModelProperty(name="name", example="your_name")
	String name;
	@ApiModelProperty(name="birth", example="your_birth")
	String birth;
	@ApiModelProperty(name="nick", example="your_nick")
	String nick;
	@ApiModelProperty(name="phone", example="your_phone")
	String phone;
	@ApiModelProperty(name="profile", example="your_profile")
	UrlResource img;
	
	public static UserRes of(User user) throws MalformedURLException {
		UserRes res = new UserRes();
		res.setEmail(user.getEmail());
		res.setName(user.getName());
		res.setBirth(user.getBirth());
		res.setPhone(user.getPhone());
		res.setNick(user.getNick());
		String path = "C:/Users/SSAFY/Pictures/";
		res.setImg(new UrlResource("file:"+path+user.getProfile()));
		return res;
	}
}
