package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("SttDetailRequest")
public class SttDetailReq {
    String time;
    String name;
    String stt;
}
