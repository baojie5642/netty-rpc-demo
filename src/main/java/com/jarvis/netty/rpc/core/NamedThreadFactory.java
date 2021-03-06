
package com.jarvis.netty.rpc.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂
 * @author jiayu.qiu
 */
public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger pollNumber=new AtomicInteger(1);

    private final AtomicInteger mThreadNum=new AtomicInteger(1);

    private final String prefix;

    private final boolean daemoThread;

    private final ThreadGroup threadGroup;

    public NamedThreadFactory() {
        this("rpcserver-threadpool-" + pollNumber.getAndIncrement(), false);
    }

    public NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    public NamedThreadFactory(String prefix, boolean daemo) {
        this.prefix=prefix + "-thread-";
        daemoThread=daemo;
        SecurityManager s=System.getSecurityManager();
        threadGroup=(s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String name=prefix + mThreadNum.getAndIncrement();
        Thread ret=new Thread(threadGroup, runnable, name, 0);
        ret.setDaemon(daemoThread);
        return ret;
    }

    public ThreadGroup getThreadGroup() {
        return threadGroup;
    }
}