/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wmq.hms.nearby.beaconmanager.beaconbase.restfulapi;

import com.wmq.hms.nearby.beaconmanager.beaconbase.BeaconBaseLog;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Scheduled Executors
 *
 * @since 2020-01-13
 */
public class ScheduledExecutors {
    private static final String TAG = "ScheduledExecutors";

    private static final ScheduledThreadFactory FACTORY = new ScheduledThreadFactory();

    private static final ScheduledUncaughtExceptionHandler HANDLER = new ScheduledUncaughtExceptionHandler();

    /**
     * Create a new scheduled thread pool.
     *
     * @param corePoolSize core pool size.
     * @return ScheduledExecutorService
     */
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new ScheduledThreadPoolExecutor(corePoolSize, FACTORY);
    }

    private static class ScheduledThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(null, r, "BeaconBase-" + threadNumber.getAndIncrement(), 0);
            thread.setUncaughtExceptionHandler(HANDLER);
            return thread;
        }
    }

    private static class ScheduledUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            BeaconBaseLog.e(TAG, t.getName() + "catch an exception: " + e.getMessage());
        }
    }
}
