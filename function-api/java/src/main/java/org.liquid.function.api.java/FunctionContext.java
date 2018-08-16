package org.liquid.function.api.java;

import org.joda.time.DateTime;

/**
 * @author linckye 2018-08-07
 */
public interface FunctionContext {

    DateTime triggerTime();

    FunctionLogger functionLogger();

    Parameter requestParameter();

    FunctionContext requestParameter(Parameter requestParameter);

    Parameter responseParameter();

    FunctionContext responseParameter(Parameter responseParameter);

}
