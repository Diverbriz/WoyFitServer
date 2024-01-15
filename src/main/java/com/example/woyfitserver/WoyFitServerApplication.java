package com.example.woyfitserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class WoyFitServerApplication {
    public static void main(String[] args) {
        String port = System.getenv("PORT");

        if(port != null){
            System.getProperties().put( "server.port",  port);
        }
        SpringApplication.run(WoyFitServerApplication.class, args);
    }

}
