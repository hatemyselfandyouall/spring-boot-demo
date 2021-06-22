package com.wangxinenpu.springbootdemo.config;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DruidTest {

    public static void main(String[] args) {
        DruidDataSource datasource=new DruidDataSource();
        datasource.setUrl("jdbc:mysql://10.85.94.189/datacdc?jdbcCompliantTruncation=false&characterEncoding=UTF-8&allowMultiQueries=true");
        datasource.setUsername("datacdc");
        datasource.setPassword("Epsoft_2021");
        //configuration
        try (Connection connection = datasource.getConnection()){
            connection.createStatement().executeQuery("select count(*) from link");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
