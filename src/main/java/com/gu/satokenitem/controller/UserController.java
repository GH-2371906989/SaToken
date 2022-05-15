package com.gu.satokenitem.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("user")
@Slf4j
@Api(tags = {"用户接口"})
public class UserController {

    @PostMapping("doLogin")
    @ApiOperation("登录")
    public String doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }


    @ApiOperation("是否登录")
    @GetMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

    @ApiOperation("获取token信息参数")
    @GetMapping("tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    @ApiOperation("注销")
    @GetMapping("logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok() ;
    }
    @ApiOperation("封禁指定账号")
    @GetMapping("disable/{id}")
    public SaResult disable(@PathVariable Long id) {
            StpUtil.disable(id,10);
        return SaResult.ok("账号封禁") ;
    }
    @ApiOperation("账号封禁状态")
    @GetMapping("disablestate/{id}")
    public SaResult disablestate(@PathVariable Long id) {
        SaSession session = StpUtil.getSession();
        log.info("session:{}",session);
        boolean disable = StpUtil.isDisable(id);
        long disableTime = StpUtil.getDisableTime(id);
        return SaResult.ok("账号封禁").setData("账号状态:"+disable+".剩余时间:"+disableTime) ;
    }
}