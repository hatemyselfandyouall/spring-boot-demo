 
package com.wangxinenpu.springbootdemo.service;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.Exam;
import com.wangxinenpu.springbootdemo.dataobject.vo.Exam.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import org.springframework.web.multipart.MultipartFile;


public interface ExamFacade{

	PageInfo<Exam> getExamList(ExamListVO listVO);

    ExamDetailShowVO getExamDetail(Long detailVO);

    Integer saveExam(ExamSaveVO saveVO);

    Integer deleteExam(ExamDeleteVO deleteVO);


    PageInfo<ExamDetailShowVO> getExamList(PageVO pageVO);

    Integer saveExamResult(SaveExamResultVO saveExamResultVO);

    Integer importExample(MultipartFile file) throws Exception;
}

 
