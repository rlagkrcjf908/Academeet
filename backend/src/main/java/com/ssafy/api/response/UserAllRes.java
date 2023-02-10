package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("UserAllResponse")
public class UserAllRes {
    @ApiModelProperty(name="users")
    List<UserRes> users;

    public static UserAllRes of(List<UserRes> users) {
        UserAllRes res = new UserAllRes();
        res.setUsers(users);
        return res;
    }
}

