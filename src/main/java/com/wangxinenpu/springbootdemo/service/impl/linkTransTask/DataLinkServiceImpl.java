package com.wangxinenpu.springbootdemo.service.impl.linkTransTask;

import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.Link;
import com.wangxinenpu.springbootdemo.dataobject.po.linkTask.LinkDetail;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.Link.LinkDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.Link.LinkShowVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.LinkTransferTask.table.TableInfo;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.ResultVo;
import com.wangxinenpu.springbootdemo.service.facade.linkTransTask.DataLinkFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DataLinkServiceImpl implements DataLinkFacade {

//    @Autowired
//    Cacehe

//    @Autowired
//    LinkMapper dataLinkMapper;
//    @Autowired
//    LinkDetailMapper dataLinkDetailMapper;

    @Override
    public LinkShowVO getDataLinkDetail(LinkDetailVO dataLinkDetailVO) throws Exception {
//        return new CacheKeyLock(cachesKeyService, 3600) {

//                if (dataLinkDetailVO == null || dataLinkDetailVO.getId() == null) {
                    return null;
//                }
//                LinkShowVO dataLinkShowVO = getDataLinkDetailWithOutSqlLink(dataLinkDetailVO.getId());
//                ResultVo<List<TableInfo>> resultVo = DataLinkUtil.dataSourceConnect(dataLinkShowVO.getLink().getLinkTypeCode(), dataLinkShowVO.getLinkDetail());
//                if (!resultVo.isSuccess()) {
//                    log.info("出现数据库连接异常"+resultVo.getResultDes());
//            throw new Exception("建立数据库连接异常" + resultVo.getResultDes());
//                }
//                dataLinkShowVO.setTableInfos(resultVo.getResult());

//                return dataLinkShowVO;

    }

//    public LinkShowVO getDataLinkDetailWithOutSqlLink(Long id) {
//        LinkShowVO dataLinkShowVO = new LinkShowVO();
//        Link dataLink = dataLinkMapper.selectByPrimaryKey(id);
//        if (dataLink == null) {
//            return null;
//        }
//        LinkDetail exampleDetail = new LinkDetail();
//        exampleDetail.setLinkId(dataLink.getId());
//        LinkDetail dataLinkDetail = dataLinkDetailMapper.selectOne(exampleDetail);
//        dataLinkShowVO.setLink(dataLink);
//        dataLinkShowVO.setLinkDetail(dataLinkDetail);
//        return dataLinkShowVO;
//    }

}
