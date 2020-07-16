package com.wangxinenpu.springbootdemo.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dao.mapper.ExamResultMapper;
import com.wangxinenpu.springbootdemo.dao.mapper.UserMapper;
import com.wangxinenpu.springbootdemo.dataobject.ExamResult;
import com.wangxinenpu.springbootdemo.dataobject.vo.User.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.ResultVo;
import com.wangxinenpu.springbootdemo.service.UserFacade;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

import com.wangxinenpu.springbootdemo.dataobject.User;

@Service
public class UserServiceImpl implements UserFacade {

    @Autowired
    UserMapper userMapper;
    @Autowired
    ExamResultMapper examResultMapper;

    @Override
    public PageInfo<User> getUserList(UserListVO userListVO) {
        if (userListVO==null||userListVO.getPageNum()==null||userListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(userListVO.getPageNum().intValue(),userListVO.getPageSize().intValue());
        User exampleObeject=new User();
        List<User> userList=userMapper.select(exampleObeject);
        PageInfo<User> userPageInfo=new PageInfo<>(userList);
        return userPageInfo;
    }

    @Override
    public User getUserDetail(UserDetailVO userDetailVO) {
        if (userDetailVO==null||userDetailVO.getId()==null) {
            return null;
        };
        User user=userMapper.selectByPrimaryKey(userDetailVO.getId());
        return user;
    }

    @Override
    public Integer saveUser(UserSaveVO userSaveVO) {
        if (userSaveVO==null){
            return 0;
        }
        User user= new User();
        BeanUtils.copyProperties(userSaveVO,user);
        if (user.getUserId()==null){
            return userMapper.insertSelective(user.setTotalCorrect(0).setTotalAnswer(0));
        }else {
            Example example=new Example(User.class);
            example.createCriteria().andEqualTo("id",user.getUserId());
            return userMapper.updateByExampleSelective(user,example);
        }
    }

    @Override
    public Integer deleteUser(UserDeleteVO userDeleteVO) {
        if (userDeleteVO==null||userDeleteVO.getId()==null){
            return 0;
        }
        User user=new User();
        Example example=new Example(User.class);
        example.createCriteria().andEqualTo("id",userDeleteVO.getId());
        return userMapper.updateByExampleSelective(user,example);
    }

    @Override
    public ResultVo<UserDetailShowVO> login(String userName, String passWord) {
      ResultVo resultVo=new ResultVo();
      if (StringUtils.isEmpty(userName)||StringUtils.isEmpty(passWord)){
          resultVo.setResultDes("用户名密码不能为空");
          return resultVo;
      }
      User user=userMapper.selectOne(new User().setUsername(userName));
      if (user==null){
          resultVo.setResultDes("用户不存在");
          return resultVo;
      }
      if (!user.getPassword().equals(passWord)){
          resultVo.setResultDes("密码错误");
          return resultVo;
      }
        UserDetailShowVO userDetailShowVO=new UserDetailShowVO();
      BeanUtils.copyProperties(user,userDetailShowVO);
        userDetailShowVO.setExamResults(examResultMapper.select(new ExamResult().setUserId(user.getUserId())));
       resultVo.setResult(userDetailShowVO);
       resultVo.setSuccess(true);
      return resultVo;
    }

    @Override
    public PageInfo<UserDetailShowVO> userRanking(PageVO pageVO) {
        if (pageVO==null||pageVO.getPageNum()==null||pageVO.getPageSize()==null) {
            return null;
        }
        Example example=new Example(User.class);
        example.setOrderByClause("total_correct desc");
        PageHelper.startPage(pageVO.getPageNum().intValue(),pageVO.getPageSize().intValue());
        List<User> users=userMapper.selectByExample(example);
        List<UserDetailShowVO> userDetailShowVOS=users.parallelStream().map(i->{
            UserDetailShowVO userDetailShowVO=new UserDetailShowVO();
            BeanUtils.copyProperties(i,userDetailShowVO);
            userDetailShowVO.setExamResults(examResultMapper.select(new ExamResult().setUserId(i.getUserId())));
            return userDetailShowVO;
        }).collect(Collectors.toList());
        PageInfo<UserDetailShowVO> userDetailShowVOPageInfo=new PageInfo<>(userDetailShowVOS);
        return userDetailShowVOPageInfo;
    }

    @Override
    public ResultVo detail(Long userId) {
        ResultVo resultVo=new ResultVo();
        User user=userMapper.selectByPrimaryKey(userId);
        UserDetailShowVO userDetailShowVO=new UserDetailShowVO();
        BeanUtils.copyProperties(user,userDetailShowVO);
        userDetailShowVO.setExamResults(examResultMapper.select(new ExamResult().setUserId(user.getUserId())));
        resultVo.setResult(userDetailShowVO);
        resultVo.setSuccess(true);
        return resultVo;
    }
}
