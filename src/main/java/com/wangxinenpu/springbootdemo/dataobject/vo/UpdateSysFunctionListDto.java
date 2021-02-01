package com.wangxinenpu.springbootdemo.dataobject.vo;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import star.vo.BaseVo;

@Data
public class UpdateSysFunctionListDto extends BaseVo {

    private JSONArray funList;

    private String channelCode ;

    private String funType;


}
