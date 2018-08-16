package org.liquid.web.deployer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author linckye 2018-08-16
 */
@SpringBootApplication
@ComponentScan(
        "org.liquid.web"
)
public class WebLaucher {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebLaucher.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
