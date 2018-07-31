package org.liquid.core.dag;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import javax.inject.Named;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author linckye 2018-07-27
 */
@SpringBootApplication
public class DAGApplication {

    @Bean(value = "scheduledPool", destroyMethod = "shutdown")
    public ExecutorService scheduledPool() {
        return Executors.newCachedThreadPool();
    }

    @Bean(value = "distributedPool", destroyMethod = "shutdown")
    public ExecutorService distributedPool() {
        return Executors.newCachedThreadPool();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(DAGApplication.class)
                .web(WebApplicationType.NONE)
                .build()
                .run(args);
    }

}
