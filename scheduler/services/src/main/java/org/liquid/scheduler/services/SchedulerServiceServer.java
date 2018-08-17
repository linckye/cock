package org.liquid.scheduler.services;

import com.google.common.base.Converter;
import lombok.Data;
import lombok.experimental.Accessors;
import org.liquid.client.DagService;
import org.liquid.client.models.dags.DagDescription;
import org.liquid.client.models.dags.GetDagRequest;
import org.liquid.client.models.dags.GetDagResponse;
import org.liquid.scheduler.client.SchedulerService;
import org.liquid.scheduler.client.models.ScheduleDagRequest;
import org.liquid.scheduler.client.models.ScheduleDagResponse;
import org.liquid.scheduler.core.dags.Dag;
import org.liquid.scheduler.core.dags.DagScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.liquid.common.utils.Blank.*;
import static org.liquid.scheduler.client.models.SchedulerCodes.*;

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

    private DagScheduler dagScheduler;

    private Converter<DagDescription, Dag> dagConverter;

    @Override
    public ScheduleDagResponse schedule(ScheduleDagRequest scheduleDagRequest) {
//        throw new NullPointerException();
        System.out.println("do");

        // check args
        if (isNullOrEmpty(scheduleDagRequest.dagName())) return new ScheduleDagResponse()
                .code(ILLEGAL_ARGUMENT)
                .message("Null dag name");

        GetDagResponse getDagResponse = dagService.get(new GetDagRequest().dagName(scheduleDagRequest.dagName()));
        if (getDagResponse.failed()) return new ScheduleDagResponse().from(getDagResponse);

        getDagResponse.dagDescription();

        return null;
    }

}
