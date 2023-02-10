package com.ssafy.api.response;

import com.ssafy.db.entity.Group;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("GroupResponse")
public class GroupRes {
    @ApiModelProperty(name="Group ")
     Group groups;

    public static GroupRes of(Group group) {
        GroupRes res = new GroupRes();
        res.setGroups(group);
        return res;
    }
}
