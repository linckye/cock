package org.liquid.scheduler.client;

import org.liquid.scheduler.client.models.ScheduleDagRequest;
import org.liquid.scheduler.client.models.ScheduleDagResponse;

/**
 * @author linckye 2018-08-09
 */
public interface SchedulerService  {

    ScheduleDagResponse schedule(ScheduleDagRequest scheduleDagRequest);

}
