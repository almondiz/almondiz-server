package org.almondiz.almondiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AlmondizApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlmondizApplication.class, args);
    }

}
