package com.wangxinenpu.springbootdemo.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class SpringBootSimpleJob implements SimpleJob {

    public void execute(ShardingContext shardingContext) {

        // do something
        log.info("任务执行"+new Date());
//        System.out.println("任务执行"+new Date());

    }
}




