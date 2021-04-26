package com.wangxinenpu.springbootdemo.util;

import com.wangxinenpu.springbootdemo.dataobject.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.Reader;

public class MybatisTester {
    public static void main(String[] args) throws IOException {
        //加载mybatis的全局配置文件
        String resources="mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resources);

        //根据配置文件构建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        //通过SqlSessionFactory创建SqlSession
        SqlSession sqlSession=sqlSessionFactory.openSession();

        //增
        User student = new User();
        student.setUserId(1l);
        student.setUsername("chy");
        //第一个参数对应映射文件中的namespace+id，通过namespace+id调用相应的sql语句，第二个参数是传给sql语句的参数
        sqlSession.insert("com.wangxinenpu.springbootdemo.dao.mapper.UserMapper.insertUser", student);

        //修改数据库（增、删、改）后，需要commit()提交给数据库，才会同步本次会话做的修改。只有查询的，可以不commit()。
        sqlSession.commit();
        //关闭会话
        sqlSession.close();
    }
}
