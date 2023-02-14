package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ApiModel("SttRequest")
public class SttReq {
    String Title;
    String name;
    Date date;

    String groupName;

    List<String> userName;

    List<String> stt;

}
