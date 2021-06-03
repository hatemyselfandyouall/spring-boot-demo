package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.table;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class TableInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("表名")
    private String TableName;
    @ApiModelProperty("表备注")
    private String TableRemark;

    @ApiModelProperty("表数据总数")
    private Integer totalCount;

    private List<FieldInfo> fieldInfos;

    @ApiModelProperty("表数据json")
    private List<JSONObject> tableDatas;


}
