package com.wangxinenpu.springbootdemo.dataobject.vo.getBatchPayInfoDTO;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class BatchResponseDTO {

    private BatchResponseResultDTO RESULT;
}
