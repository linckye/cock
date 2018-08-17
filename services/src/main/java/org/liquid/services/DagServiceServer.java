package org.liquid.services;

import lombok.Data;
import lombok.experimental.Accessors;
import org.liquid.client.DagService;
import org.liquid.client.models.Codes;
import org.liquid.client.models.dags.GetDagRequest;
import org.liquid.client.models.dags.GetDagResponse;
import org.springframework.stereotype.Service;

/**
 * @author linckye 2018-08-16
 */
@Service
@Data
@Accessors(chain = true, fluent = true)
public class DagServiceServer
        implements DagService {

    @Override
    public GetDagResponse get(GetDagRequest getDagRequest) {
        // TODO
        return new GetDagResponse().code(Codes.NOT_FOUND).message("not fount");
    }

}
