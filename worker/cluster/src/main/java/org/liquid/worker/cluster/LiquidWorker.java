package org.liquid.worker.cluster;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author linckye 2018-03-31
 */
@SpringBootApplication
public class LiquidWorker {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .web(WebApplicationType.NONE)
                .run(args);
    }

}
