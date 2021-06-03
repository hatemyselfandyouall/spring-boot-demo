package com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.Link;

import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class LinkListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("所属文件夹id，为空则不属于任何文件夹")
    @Column(name = "folder_id")
    private Long folderId;

    @ApiModelProperty("搜索关键词")
    private String keyWord;

    @ApiModelProperty("数据连接类型编码")
    private String linkTypeCode;
}
