package org.liquid.core.dag;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.liquid.common.util.Blank;
import org.liquid.test.concurrent.RunTrace;

/**
 * @author linckye 2018-07-29
 */
@ToString
@Accessors(chain = true, fluent = true)
public class LocalNode implements Node , Runnable {

    @Getter
    private RunTrace runTrace;

    @Getter@Setter
    private String msg;

    public synchronized void run() {
        if (!Blank.isNull(runTrace)) throw new RuntimeException(this + " already run");
        runTrace = RunTrace.get();
    }

}
