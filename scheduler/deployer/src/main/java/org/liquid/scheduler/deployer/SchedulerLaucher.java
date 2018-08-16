package org.liquid.scheduler.deployer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author linckye 2018-08-09
 */
@SpringBootApplication
@ComponentScan({
        "org.liquid"
})
public class SchedulerLaucher {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SchedulerLaucher.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

}
