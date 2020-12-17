package com.wangxinenpu.springbootdemo.dataobject.vo.getBatchPayInfoDTO;

import com.wangxinenpu.springbootdemo.dataobject.vo.getBatchPayInfoDTO.ParamDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RequestDTO {

    List<ParamDTO> PARAM;
}
