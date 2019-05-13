package cn.itsource.fenggou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @version: V1.0
 * @author: fan
 * @className: ConfigApplication
 * @description: 远端配置服务器
 * @date: 2019/5/12
 **/
@SpringBootApplication
@EnableConfigServer
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class,args);
    }
}
