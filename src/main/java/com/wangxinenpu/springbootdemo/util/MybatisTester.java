package com.wangxinenpu.springbootdemo.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisTester {
    public static void main(String[] args) {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder=new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory=sqlSessionFactoryBuilder.build();
        SqlSession sqlSession=sqlSessionFactory.openSession();
        sqlSession.selectList();
    }
}
