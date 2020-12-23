package com.wangxinenpu.springbootdemo.dataobject.vo.SiteMatters;


import com.wangxinenpu.springbootdemo.dataobject.dto.sitematters.SiteMattersDto;
import com.wangxinenpu.springbootdemo.dataobject.po.SiteBlock;
import com.wangxinenpu.springbootdemo.dataobject.vo.SiteMattersArea.SiteMattersAreaSaveVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SSMSiteMattersDetailInfoShowVO implements Serializable {
    private static final long serialVersionUID = 1L;

    SiteMattersDto siteMattersDto;

    List<SiteMattersAreaSaveVO> siteMattersAreaSaveVOS;

    SiteBlock siteBlock;

}
