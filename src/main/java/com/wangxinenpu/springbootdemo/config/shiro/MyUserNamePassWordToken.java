package com.wangxinenpu.springbootdemo.config.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.StringUtils;

/**
 * Created by young on 2016/5/24.
 */
@SuppressWarnings("serial")
public class MyUserNamePassWordToken extends UsernamePasswordToken {


    public MyUserNamePassWordToken() {
        super();
    }

    public MyUserNamePassWordToken(String username, char[] password, String host) {
        super(username, password, host);
    }

    public MyUserNamePassWordToken(String username, String password) {
        super(StringUtils.clean(username), StringUtils.clean(password));
    }
}
