package com.wangxinenpu.springbootdemo.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dao.mapper.ExamMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.ExamResultMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.QuestionMapper;
import com.wangxinenpu.springbootdemo.dataobject.Exam;
import com.wangxinenpu.springbootdemo.dataobject.ExamResult;
import com.wangxinenpu.springbootdemo.dataobject.Question;
import com.wangxinenpu.springbootdemo.dataobject.vo.Exam.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import com.wangxinenpu.springbootdemo.service.ExamFacade;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.wangxinenpu.springbootdemo.dataobject.Exam;
import com.wangxinenpu.springbootdemo.dataobject.vo.Exam.ExamDeleteVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.Exam.ExamDetailVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.Exam.ExamListVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.Exam.ExamSaveVO;

@Service
public class ExamServiceImpl implements ExamFacade {

    @Autowired
    ExamMapper examMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    ExamResultMapper examResultMapper;

    @Override
    public PageInfo<Exam> getExamList(ExamListVO examListVO) {
        if (examListVO==null||examListVO.getPageNum()==null||examListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(examListVO.getPageNum().intValue(),examListVO.getPageSize().intValue());
        Exam exampleObeject=new Exam();
        List<Exam> examList=examMapper.select(exampleObeject);
        PageInfo<Exam> examPageInfo=new PageInfo<>(examList);
        return examPageInfo;
    }

    @Override
    public Exam getExamDetail(ExamDetailVO examDetailVO) {
        if (examDetailVO==null||examDetailVO.getId()==null) {
            return null;
        };
        Exam exam=examMapper.selectByPrimaryKey(examDetailVO.getId());
        return exam;
    }

    @Override
    public Integer saveExam(ExamSaveVO examSaveVO) {
        if (examSaveVO==null){
            return 0;
        }
        Exam exam= new Exam();
        BeanUtils.copyProperties(examSaveVO,exam);
        if (exam.getId()==null){
            return examMapper.insertSelective(exam);
        }else {
            Example example=new Example(Exam.class);
            example.createCriteria().andEqualTo("id",exam.getId());
            return examMapper.updateByExampleSelective(exam,example);
        }
    }

    @Override
    public Integer deleteExam(ExamDeleteVO examDeleteVO) {
        if (examDeleteVO==null||examDeleteVO.getId()==null){
            return 0;
        }
        Exam exam=new Exam();
        Example example=new Example(Exam.class);
        example.createCriteria().andEqualTo("id",examDeleteVO.getId());
        return examMapper.updateByExampleSelective(exam,example);
    }

    @Override
    public PageInfo<ExamDetailShowVO> getExamList(PageVO pageVO) {
        if (pageVO==null||pageVO.getPageNum()==null||pageVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(pageVO.getPageNum().intValue(),pageVO.getPageSize().intValue());
        List<Exam> exams=examMapper.selectAll();
        List<ExamDetailShowVO> examDetailShowVOS=exams.parallelStream().map(i->{
            ExamDetailShowVO examDetailShowVO=new ExamDetailShowVO();
            BeanUtils.copyProperties(i,examDetailShowVO);
            return examDetailShowVO.setQuestionList(questionMapper.select(new Question().setExamId(i.getId())));
        }).collect(Collectors.toList());
        PageInfo<ExamDetailShowVO> examDetailShowVOPageInfo=new PageInfo<>(examDetailShowVOS);
        return examDetailShowVOPageInfo;
    }

    @Override
    public Integer saveExamResult(SaveExamResultVO saveExamResultVO) {
        ExamResult examResult=new ExamResult();
        BeanUtils.copyProperties(saveExamResultVO,examResult);
        return examResultMapper.insert(examResult);
    }
}
