package cn.itsource.fenggou.controller;

import cn.itsource.fenggou.util.RedisUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version: V1.0
 * @author: fan
 * @className: RedisController
 * @description: redis的存，取
 * @date: 2019/5/17
 **/
@RestController
public class RedisController {
    /**
     * 讲数存到redis里
     * @param key
     * @param value
     */
    @PostMapping("/redis")
    public void set(String key,String value){
        RedisUtils.INSTANCE.set(key,value);
    }

    /**
     * 根据key取数据
     * @param key
     * @return
     */
    @GetMapping("/redis")
    public String get(String key){
        return RedisUtils.INSTANCE.get(key);
    }
}
