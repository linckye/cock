package org.liquid.scheduler.core.dags;

import javax.annotation.Nonnull;
import java.util.EventListener;

/**
 * {@code Dag} 调度监听器，负责监听 {@code Dag} 的调度情况。
 *
 * @author linckye 2018-07-29
 */
public interface DagScheduleListener
        extends EventListener {

    /** {@code Dag} 调度成功时对 {@code dagSchedule} 产生回调。**/
    void onSuccess(@Nonnull DagSchedule dagSchedule);

    /** {@code Dag} 调度失败时对 {@code dagSchedule} 产生回调。**/
    void onFailure(@Nonnull DagSchedule dagSchedule);

}
