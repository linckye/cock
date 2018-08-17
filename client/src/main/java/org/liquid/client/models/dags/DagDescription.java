package org.liquid.client.models.dags;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author linckye 2018-08-16
 */
@Data
@Accessors(chain = true, fluent = true)
public class DagDescription
        implements Serializable {

    private String dagName;

}
