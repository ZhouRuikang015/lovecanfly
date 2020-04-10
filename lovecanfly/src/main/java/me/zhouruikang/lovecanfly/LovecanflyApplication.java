package me.zhouruikang.lovecanfly;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableScheduling
@MapperScan("me.zhouruikang.lovecanfly.dao")
@SpringBootApplication
public class LovecanflyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LovecanflyApplication.class, args);
    }

}
