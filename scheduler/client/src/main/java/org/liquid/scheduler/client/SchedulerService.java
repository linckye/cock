package org.liquid.scheduler.client;

import org.liquid.scheduler.client.model.ScheduleDagRequest;
import org.liquid.scheduler.client.model.ScheduleDagResponse;

/**
 * @author linckye 2018-08-09
 */
public interface SchedulerService  {

    ScheduleDagResponse schedule(ScheduleDagRequest scheduleDagRequest);

}
