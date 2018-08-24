package org.liquid.function.api.java;

import org.joda.time.DateTime;

import java.util.Optional;

/**
 * @author linckye 2018-08-07
 */
public interface Parameter {

    Optional<Parameter> get(String key);

    <T> Optional<T> getObject(String key, Class<T> mappedClass);

    Optional<String> getString(String key);

    Optional<Integer> getInteger(String key);

    Optional<Float> getFloat(String key);

    Optional<Double> getDouble(String key);

    Optional<DateTime> getTime(String key);

    void set(String key, Object value);

}
