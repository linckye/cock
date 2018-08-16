package org.liquid.worker.deployer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author linckye 2018-08-16
 */
@SpringBootApplication
@ComponentScan({
        "org.liquid"
})
public class WorkerLaucher {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WorkerLaucher.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

}
