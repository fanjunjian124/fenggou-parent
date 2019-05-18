package cn.itsource.fenggou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @version: V1.0
 * @author: fan
 * @className: CommonApplication
 * @description: todo
 * @date: 2019/5/17
 **/
@SpringBootApplication
@EnableEurekaClient
//@EnableFeignClients 调用的时候 在要调用的服务器上加 feign的注解
public class CommonApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class,args);
    }
}
