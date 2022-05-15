package com.gu.satokenitem.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@CrossOrigin
@RequestMapping("test")
@Slf4j
@Api(tags = {"测试接口"})
public class TestController {
    @ApiOperation("测试")
    @GetMapping("/test")
    public String test() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
    }
}
