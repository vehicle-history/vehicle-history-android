package io.vehiclehistory.api;

/**
 * @author Piotr Makowski (<a href=\"mailto:Piotr.Makowski@allegrogroup.pl\">Piotr.Makowski@allegrogroup.pl</a>)
 */
public class MockMethodDelegate {

    public interface OnExecutionFinishedListener {
        void onExecutionFinished();
    }

    public void execute(final OnExecutionFinishedListener onExecutionFinishedListener) {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                onExecutionFinishedListener.onExecutionFinished();
            }
        });

        t.start();
    }
}
