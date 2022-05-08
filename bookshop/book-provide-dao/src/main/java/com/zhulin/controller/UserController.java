package com.zhulin.controller;

import com.zhulin.bean.JsonModel;
import com.zhulin.commons.CommonBean;
import com.zhulin.dto.SignUpParam;
import com.zhulin.entities.User;
import com.zhulin.service.UserService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author:ZHULIN
 * @Description: 用户信息控制层
 */
@RestController
@RequestMapping("/provide")
@Api(tags = "用户模块")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/user/getRandomId/{uName}")
    @ApiOperation("生成随机数")
    @ApiImplicitParam(name = "uName", value = "用户名", required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "随机数生成成功"),
            @ApiResponse(code = 444, message = "该用户不存在")})
    public JsonModel<String> getRandomId(@PathVariable String uName) {
        String randomId = userService.getRandomId(uName);
        if (randomId == null) {
            return new JsonModel<>(444, "该用户不存在");
        }
        return new JsonModel<>(200, "随机数生成成功", randomId);
    }

    @PostMapping("/user/login")
    @ApiOperation("用户登录")
    @ApiImplicitParam(type = "body", dataType = "User", name = "user", value = "用户信息", required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "登录成功"),
            @ApiResponse(code = 444, message = "登录失败")})
    public JsonModel<User> login(@RequestBody User user) {
        User isLogin = userService.login(user);
        if (isLogin == null) {
            return new JsonModel<>(444, "登录失败");
        }
        return new JsonModel<>(200, "登录成功", isLogin);
    }

    @PostMapping("user/signUp")
    @ApiOperation("用户注册")
    @ApiImplicitParam(type = "body", dataType = "SignUpParam", name = "signUpParam", value = "用户注册信息", required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "注册成功"),
            @ApiResponse(code = 444, message = "注册失败"),
            @ApiResponse(code = 443, message = "未发送验证码"),
            @ApiResponse(code = 445, message = "验证码不正确")})
    public JsonModel signUp(@RequestBody SignUpParam signUpParam) {
        // 判断用户是否发送验证码
        if (CommonBean.CodeMap.get("code") == null) {
            return new JsonModel(443, "未发送验证码");
        }
        // 取出验证码
        int trueCode = CommonBean.CodeMap.get("code");
        if (trueCode != signUpParam.getCode()) {
            return new JsonModel(445, "验证码不正确");
        }
        Boolean isSignUp = userService.signUpUser(signUpParam);
        if (isSignUp) {
            return new JsonModel(200, "注册成功");
        }
        return new JsonModel(444, "注册失败");
    }
}
