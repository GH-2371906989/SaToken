package com.gu.satokenitem;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class SatokenitemApplicationTests {
    @Autowired
    RestTemplate restTemplate;
    @Test
    void t1() {
        String t1 = "hello t1";
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(t1);
        ResponseEntity<String> forEntity = restTemplate.postForEntity("http://localhost:7778/rest/r1", stringHttpEntity, String.class);
        String body = forEntity.getBody();
    }

}
