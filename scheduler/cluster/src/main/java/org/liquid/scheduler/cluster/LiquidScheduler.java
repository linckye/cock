package org.liquid.scheduler.cluster;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author linckye 2018-03-31
 */
@SpringBootApplication
public class LiquidScheduler {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .web(WebApplicationType.NONE)
                .run(args);
    }

}
