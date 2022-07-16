package com.gu.satokenitem.controller;

import com.alibaba.fastjson.JSON;
import com.gu.satokenitem.common.util.Encryption;
import com.gu.satokenitem.mapper.UserMapper;
import com.gu.satokenitem.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private UserMapper userMapper;

    @Value("${MD5.key}")
    private String key;

    @ApiOperation("测试")
    @GetMapping("/test")
    public String test() {

        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
    }
    @ApiOperation("注册账号")
    @GetMapping("/signing")
    public String signing(User user,String pass) {
        if (pass.equals("803612")){
            String s = new Encryption().waybillTraceMD5(user.getUser() + user.getPassword() + key);
            user.setPassword(s);
            userMapper.insert(user);
            return user.getUser()+"========>注册成功";
        }
        return "注册失败";
    }
}
