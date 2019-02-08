package yummy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EntityScan(basePackages = "yummy.entity")
@EnableJpaRepositories(basePackages = "yummy.dao")
@EnableScheduling
public class yummy {
    public static void main(String[] args) {
        SpringApplication.run(yummy.class, args);
    }
}
