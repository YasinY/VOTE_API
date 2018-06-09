package com.yasin.voteapi;

import com.yasin.voteapi.tasks.TaskHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Yasin
 */

@SpringBootApplication (exclude = { DataSourceAutoConfiguration.class })
public class Launcher {

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
        new TaskHandler().init();
    }
}
