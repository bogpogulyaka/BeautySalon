package org.salon.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ConnectionPool {
    public static void main(String[] args) {
        SpringApplication.run(ConnectionPool.class, args);
    }
}
