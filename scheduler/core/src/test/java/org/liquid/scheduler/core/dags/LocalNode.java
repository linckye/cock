package org.liquid.scheduler.core.dags;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.liquid.common.utils.Blank;
import org.liquid.scheduler.core.dags.Node;
import org.liquid.test.concurrent.RunTrace;

/**
 * @author linckye 2018-07-29
 */
@ToString
@Accessors(chain = true, fluent = true)
public class LocalNode implements Node, Runnable {

    @Getter
    private RunTrace runTrace;

    @Getter@Setter
    private String msg;

    public synchronized void run() {
        Preconditions.checkState(Blank.isNull(runTrace), this + " already run");
        runTrace = RunTrace.get();
    }

}
