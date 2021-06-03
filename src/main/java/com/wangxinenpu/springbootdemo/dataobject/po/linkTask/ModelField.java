
package com.wangxinenpu.springbootdemo.dataobject.po.linkTask;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;


@Data
@Accessors(chain = true)
public class ModelField implements Serializable {


    //========== properties ==========

    @ApiModelProperty("")
    @Column(name = "id")
    private Long id;

    @ApiModelProperty("数据模型id")
    @Column(name = "model_id")
    private Long modelId;

    @ApiModelProperty("字段所属表id，可以为空")
    @Column(name = "table_id")
    private Long tableId;

    @ApiModelProperty("数据模型字段名")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("数据库字段名")
    @Column(name = "column_name")
    private String columnName;

    @ApiModelProperty("字段数据类型")
    @Column(name = "measure_type_id")
    private String measureTypeId;

    @ApiModelProperty("字段是表原生字段还是自定义字段，1原生0自定义")
    @Column(name = "is_original")
    private Integer isOriginal;

    @ApiModelProperty("自定义字段的定义函数")
    @Column(name = "measure_function")
    private String measureFunction;

    @ApiModelProperty("1-set字段2-where字段")
    @Column(name = "is_update")
    private String isUpdate;

    @ApiModelProperty("列类型")
    private String columnType;
}
