package com.wangxinenpu.springbootdemo.dataobject.vo.root;

import com.wangxinenpu.springbootdemo.dataobject.vo.getBatchPayInfoDTO.RequestDTO;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RootDTO {

    private RequestDTO REQUEST;

    private ResponseDTO RESPONSE;

}
