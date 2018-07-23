package org.liquid.test.concurrent;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * @author linckye 2018-07-21
 */
public class RunTraceTest {

    @Test
    public void test() throws Exception {
        RunTrace runTrace1 = RunTrace.get();
        RunTrace runTrace2 = RunTrace.get();
        RunTrace runTrace3;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        List<RunTrace> runTraces = Lists.newArrayList();
        new Thread(() -> {
            runTraces.add(RunTrace.get());
            countDownLatch.countDown();
        }).start();
        countDownLatch.await();
        runTrace3 = runTraces.get(0);

        assertTrue(runTrace1.isRunBefore(runTrace2));
        assertTrue(runTrace2.isRunBefore(runTrace3));

        assertTrue(runTrace1.isRunOnTheSameThread(runTrace2));
        assertTrue(runTrace3.isRunOnTheDifferentThread(runTrace1));
    }

}