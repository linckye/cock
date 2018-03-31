package org.liquid.api;

import lombok.Getter;

/**
 * @author linckye 2018-03-31
 */
public abstract class Node
        implements Runnable {

    @Getter
    private boolean available;

    public void init() {
        available = true;
    }

    public void destroy() {
        available = false;
    }

}
