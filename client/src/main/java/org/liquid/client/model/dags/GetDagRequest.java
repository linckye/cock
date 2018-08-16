package org.liquid.client.model.dags;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.liquid.client.model.Request;

import java.io.Serializable;

/**
 * @author linckye 2018-08-16
 */
@Data
@Accessors(chain = true, fluent = true)
@EqualsAndHashCode(callSuper = true)
public class GetDagRequest
        extends Request
        implements Serializable {

    private String dagName;

}
