package org.liquid.scheduler.services;

import lombok.Data;
import lombok.experimental.Accessors;
import org.liquid.client.DagService;
import org.liquid.scheduler.client.SchedulerService;
import org.liquid.scheduler.client.model.ScheduleDagRequest;
import org.liquid.scheduler.client.model.ScheduleDagResponse;
import org.liquid.scheduler.core.dags.DagScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linckye 2018-08-09
 */
@Service
@Data
@Accessors(chain = true, fluent = true)
public class SchedulerServiceServer
        implements SchedulerService {

    @Autowired
    private DagService dagService;

    @Autowired
    private DagScheduler dagScheduler;

    @Override
    public ScheduleDagResponse schedule(ScheduleDagRequest scheduleDagRequest) {
        // TODO
        return null;
    }

}
