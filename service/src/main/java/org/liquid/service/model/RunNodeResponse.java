package org.liquid.service.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.Future;

/**
 * @author linckye 2018-04-09
 */
@Data
@Accessors(chain = true)
public class RunNodeResponse {

    private Future runFuture;

}
