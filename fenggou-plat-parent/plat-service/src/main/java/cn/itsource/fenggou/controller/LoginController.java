package cn.itsource.fenggou.controller;

import cn.itsource.util.AjaxResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @version: V1.0
 * @author: fan
 * @className: PlatController
 * @description: todo
 * @date: 2019/5/11
 **/
@RestController
public class LoginController {
    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
    public AjaxResult login(@RequestBody Map<String,Object> params){

        String username = (String) params.get("username");
        String password = (String) params.get("password");

        if("admin".equals(username)&&"admin".equals(password)){
            //登录成功
            return AjaxResult.me();
        }else{
            //登录失败
            return AjaxResult.me().setSuccess(false).setMessage("用户名或密码错误!");
        }

    }

}
