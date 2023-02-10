package com.ssafy.api.response;

import com.ssafy.db.entity.Group;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("GroupUserResponse")
public class GroupUserRes {
    @ApiModelProperty(name="Group id")
    int groupid;
    @ApiModelProperty(name="Owner id")
    int ownerid;
    @ApiModelProperty(name="Group name")
    String name;
    public static GroupUserRes of(Group group) {
        GroupUserRes res = new GroupUserRes();
        res.setGroupid(group.getId());
        res.setOwnerid(group.getOwnerid().getId());
        res.setName(group.getName());
        return res;
    }
}
