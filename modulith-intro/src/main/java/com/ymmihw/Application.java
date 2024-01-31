package com.ymmihw;

import com.ymmihw.product.ProductDTO;
import com.ymmihw.product.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args).getBean(ProductService.class).create(new ProductDTO("baeldung", "course", 10));
    }
}