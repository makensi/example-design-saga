package com.eitypic.designsaga;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer {

    // standalone run
    public static void main(String[] args) {
        configureApplication(new SpringApplicationBuilder()).run(args);
    }

    // web run
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return configureApplication(builder);
    }

    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

}
