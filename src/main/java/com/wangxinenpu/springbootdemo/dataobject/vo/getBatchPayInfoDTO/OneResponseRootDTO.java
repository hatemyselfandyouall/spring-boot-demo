package com.wangxinenpu.springbootdemo.dataobject.vo.getBatchPayInfoDTO;

import com.wangxinenpu.springbootdemo.dataobject.vo.root.IsSuccessDTO;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OneResponseRootDTO {

    private RequestDTO REQUEST;

    private IsSuccessDTO IS_SUCCESS;

    private String ERRMSG;

    private OneResponseDTO RESPONSE;
}
