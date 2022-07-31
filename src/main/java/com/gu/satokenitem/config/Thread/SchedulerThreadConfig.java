package com.gu.satokenitem.config.Thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Configuration
public class SchedulerThreadConfig {
    // 用来存入线程执行情况, 方便于停止定时任务时使用
    public static ConcurrentHashMap<String, ScheduledFuture> map = new ConcurrentHashMap<String, ScheduledFuture>();
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.initialize();
        taskScheduler.setPoolSize(2);//我这里设置的线程数是2,可以根据需求调整 // 调度器shutdown被调用时等待当前被调度的任务完成
        return taskScheduler;
    }
}
