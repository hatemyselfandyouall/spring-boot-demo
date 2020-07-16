 
package com.wangxinenpu.springbootdemo.service;

import com.github.pagehelper.PageInfo;
import com.wangxinenpu.springbootdemo.dataobject.User;
import com.wangxinenpu.springbootdemo.dataobject.vo.User.*;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.PageVO;
import com.wangxinenpu.springbootdemo.dataobject.vo.root.ResultVo;


public interface UserFacade{

	PageInfo<User> getUserList(UserListVO listVO);

    User getUserDetail(UserDetailVO detailVO);

    Integer saveUser(UserSaveVO saveVO);

    Integer deleteUser(UserDeleteVO deleteVO);


    ResultVo login(String userName, String passWord);

    PageInfo<UserDetailShowVO> userRanking(PageVO pageVO);

    ResultVo detail(Long userId);
}

 
