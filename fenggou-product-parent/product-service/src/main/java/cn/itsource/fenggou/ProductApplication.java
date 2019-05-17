package cn.itsource.fenggou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @version: V1.0
 * @author: fan
 * @className: ProductApplication
 * @description: todo
 * @date: 2019/5/16
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2 //标识使用swagger生成接口文档
@MapperScan("cn.itsource.fenggou.mapper")
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class,args);
    }
}
