package org.liquid.service.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author linckye 2018-04-09
 */
@Data
@Accessors(chain = true)
public class RunNodeRequest {

    private String nodeId;

}
