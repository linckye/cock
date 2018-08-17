package org.liquid.scheduler.deployer;

import org.liquid.scheduler.client.SchedulerService;
import org.liquid.scheduler.client.models.ScheduleDagRequest;
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
        SchedulerService schedulerService = new SpringApplicationBuilder(SchedulerLaucher.class)
                .web(WebApplicationType.NONE)
                .run(args).getBean(SchedulerService.class);
        System.out.println(schedulerService.schedule(null));
        System.out.println(schedulerService.schedule(new ScheduleDagRequest().dagName("f")));
    }

}
