package com.scraper.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.util.Optional;


@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {
    private static final String BASE_URI;
    private static final String protocol;
    private static final Optional<String> host;
    private static final String path;
    private static final Optional<String> port;

    static{
        protocol = "http://";
        host = Optional.ofNullable(System.getenv("HOSTNAME"));
        port = Optional.ofNullable(System.getenv("PORT"));
        path = "blog";
        BASE_URI = protocol + host.orElse("localhost") + ":" + port.orElse("8080") + "/" + path + "/";
    }

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }
}