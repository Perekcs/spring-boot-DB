package org.example.springbootpr1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SpringBootPr1Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootPr1Application.class, args);
    }
}
