package com.wangxinenpu.springbootdemo.dataobject.vo.root;

import com.wangxinenpu.springbootdemo.dataobject.vo.getBatchPayInfoDTO.ParamDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ResponseDTO {

    List<ParamDTO> PARAM;
}
