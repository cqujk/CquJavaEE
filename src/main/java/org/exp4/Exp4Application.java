package org.exp4;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.exp4.repository.dao")
public class Exp4Application {

    public static void main(String[] args) {
        SpringApplication.run(Exp4Application.class, args);
    }

}
