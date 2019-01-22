package com.rakuten.fullstackrecruitmenttest;

import com.rakuten.fullstackrecruitmenttest.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class FullstackrecruitmenttestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FullstackrecruitmenttestApplication.class, args);
    }
}
