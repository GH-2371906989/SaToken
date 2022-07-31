package com.gu.satokenitem;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.concurrent.ScheduledFuture;

@SpringBootTest
class SatokenitemApplicationTests {
    @Autowired
    RestTemplate restTemplate;
//    @Autowired
//    TaskScheduler taskScheduler;
    @Test
    void t1() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.initialize();
        taskScheduler.setPoolSize(2);
        ScheduledFuture scheduledFuture = taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("vhengg");
            }
        }, new CronTrigger("0/1 * * * * ?"));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
