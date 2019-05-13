package cn.itsource.fenggou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @version: V1.0
 * @author: fan
 * @className: PlatApplication
 * @description: todo
 * @date: 2019/5/11
 **/
@SpringBootApplication
@EnableEurekaClient
public class PlatApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlatApplication.class,args);
    }
}
