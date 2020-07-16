package com.wangxinenpu.springbootdemo.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dao.mapper.ExamMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.ExamResultMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.QuestionMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.UserMapper;
import com.wangxinenpu.springbootdemo.dataobject.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.Exam.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import com.wangxinenpu.springbootdemo.service.ExamFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import org.springframework.beans.BeanUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import com.wangxinenpu.springbootdemo.dataobject.Exam;
import com.wangxinenpu.springbootdemo.dataobject.vo.Exam.ExamDeleteVO;
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
    @Autowired
    UserMapper userMapper;

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
    public ExamDetailShowVO getExamDetail(Long id) {
        if (id==null) {
            return null;
        };
        Exam exam=examMapper.selectByPrimaryKey(id);
        ExamDetailShowVO examDetailShowVO=new ExamDetailShowVO();
        BeanUtils.copyProperties(exam,examDetailShowVO);
        return examDetailShowVO.setQuestionList(questionMapper.select(new Question().setExamId(exam.getId())));
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
        Example example=new Example(ExamResult.class);
        example.createCriteria().andEqualTo("userId",saveExamResultVO.getUserId()).andEqualTo("examId",saveExamResultVO.getExamId());
        examResultMapper.deleteByExample(example);
        Integer flag=examResultMapper.insert(examResult);
        User user=userMapper.selectByPrimaryKey(examResult.getUserId());
        user.setTotalCorrect(examResultMapper.select(new ExamResult().setUserId(user.getUserId())).parallelStream().map(ExamResult::getNumCorrect).reduce(0,(a,b)->a+b));
        user.setTotalAnswer(examResultMapper.select(new ExamResult().setUserId(user.getUserId())).parallelStream().map(ExamResult::getNumAnswered).reduce(0,(a,b)->a+b));
        userMapper.updateByPrimaryKeySelective(user);
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer importExample(MultipartFile file) throws Exception {
        String testJSON="";
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        StringBuilder sb=new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        testJSON=sb.toString();
        System.out.println(testJSON);
        ExamSaveVO examSaveVO= JSONObject.parseObject(testJSON,ExamSaveVO.class);
        Exam exam=new Exam();
        BeanUtils.copyProperties(examSaveVO,exam);
        examMapper.insertSelective(exam);
        Long examId=exam.getId();
        examSaveVO.getQuestions().forEach(i->{
            i.setId(null);
            i.setExamId(examId);
            questionMapper.insertSelective(i);
        });
        return 1;
    }
}
