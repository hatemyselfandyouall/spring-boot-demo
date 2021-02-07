package com.wangxinenpu.springbootdemo.task;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleJobConfig {

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    /**
     * 自己实现的job
     * */
    @Bean
    public SimpleJob simpleJob() {
        return new SpringBootSimpleJob();
    }


    /**
     * 将自己实现的job加入调度中执行
     * */
    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final SimpleJob simpleJob, @Value("${simpleJob.cron}") final String cron,
                                           @Value("${simpleJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${simpleJob.shardingItemParameters}") final String shardingItemParameters) {

        return new SpringJobScheduler(simpleJob, zookeeperRegistryCenter,
                getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }

    /**
     * 作业的配置
     * */
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron,
                                                         final int shardingTotalCount, final String shardingItemParameters) {

        return LiteJobConfiguration.newBuilder(
                new SimpleJobConfiguration(
                        JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                                .shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();

    }

}

