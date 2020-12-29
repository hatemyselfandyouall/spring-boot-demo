 
package com.wangxinenpu.springbootdemo.service.facade;

import com.github.pagehelper.PageInfo;

import com.wangxinenpu.springbootdemo.dataobject.po.OpenapiOrgShortname;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrgShortname.OpenapiOrgShortnameDeleteVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrgShortname.OpenapiOrgShortnameDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrgShortname.OpenapiOrgShortnameListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.OpenapiOrgShortname.OpenapiOrgShortnameSaveVO;
import star.vo.result.ResultVo;


public interface OpenapiOrgShortnameFacade{

	PageInfo<OpenapiOrgShortname> getOpenapiOrgShortnameList(OpenapiOrgShortnameListVO listVO);

    OpenapiOrgShortname getOpenapiOrgShortnameDetail(OpenapiOrgShortnameDetailVO detailVO);

    ResultVo saveOpenapiOrgShortname(OpenapiOrgShortnameSaveVO saveVO);

    Integer deleteOpenapiOrgShortname(OpenapiOrgShortnameDeleteVO deleteVO);

    Integer checkDelete(OpenapiOrgShortnameDeleteVO openapiOrgShortnameDeleteVO);
}

 
