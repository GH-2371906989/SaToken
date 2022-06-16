package com.gu.satokenitem.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.gu.satokenitem.common.json.BaseController;
import com.gu.satokenitem.common.response.Result;
import com.gu.satokenitem.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@CrossOrigin
@RequestMapping("rest")
@Slf4j
@Api(tags = {"测试rest接口"})
public class restController extends BaseController {


    @ApiOperation("测试1")
    public Result r1(@RequestBody String user) {
        System.out.println("user = " + user);
        return error();
    }

}
