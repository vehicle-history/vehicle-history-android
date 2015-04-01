package io.vehiclehistory.api.method;

/**
 * Created by Dawid on 2015-03-16.
 */
public class AsyncMethodExecutor {
    public <T> void execute(final Method<T> method) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                method.makeRequest();
            }
        });
        t.start();
    }
}
