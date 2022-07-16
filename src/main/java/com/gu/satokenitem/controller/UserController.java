package com.gu.satokenitem.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gu.satokenitem.common.json.BaseController;
import com.gu.satokenitem.common.response.Result;
import com.gu.satokenitem.common.util.Encryption;
import com.gu.satokenitem.mapper.UserMapper;
import com.gu.satokenitem.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("jotter")
@Slf4j
@Api(tags = {"用户接口"})
public class UserController  extends BaseController {

    @Autowired
    private Encryption encryption;
    @Value("${MD5.key}")
    private String key;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/doLogin")
    @ApiOperation("登录")
    public Result doLogin(@RequestBody User user) {
        String pass = encryption.waybillTraceMD5(user.getUser() + user.getPassword() + key);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUser,user.getUser());
        User user1 = userMapper.selectOne(queryWrapper);
        if (user1 == null){
            return error().Message("用户不存在");
        }
        if(user1.getUser().equals(user.getUser()) && pass.equals(user1.getPassword())) {
            StpUtil.login(JSON.toJSONString(user1));
            return success().Message("登录成功").put("Token",StpUtil.getTokenValue());
        }
        return error().Message("账户或密码错误");
    }

    public static void main(String[] args) {
        String s = new Encryption().waybillTraceMD5("谷欢" + "803612" + "AF5581CBF45F7414");
        log.info(s);

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

 /*   @ApiOperation("注销")
    @GetMapping("/logout/{id}")
    public SaResult logout(@PathVariable("id") int id) {
        StpUtil.logout(id);
        return SaResult.ok() ;
    }*/

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
