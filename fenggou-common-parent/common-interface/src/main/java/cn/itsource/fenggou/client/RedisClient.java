package cn.itsource.fenggou.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("FENGGOU-COMMON")
public interface RedisClient {
    /**
     * 讲数存到redis里
     * @param key
     * @param value
     */
    @PostMapping("/redis")
     void set(@RequestParam("key")String key, @RequestParam("value")String value);


    /**
     * 根据key取数据
     * @param key
     * @return
     */
    @GetMapping("/redis")
     String get(@RequestParam("key")String key);


}
