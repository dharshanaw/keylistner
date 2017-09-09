package com.imomap.main.analytics;


import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

public class PerceptionCalculator{

    public static void main(String[] args) throws InterruptedException {
        SiddhiManager siddhiManager = new SiddhiManager();

        String siddhiApp = "" +

        "define  stream cseEventStream (KeyCharactor String, volume long, timestamp long);" +

                "@info(name = 'query1') " +
                "SELECT COUNT(KeyCharactor) as toatal" +
                " FROM keyevents"+
                "where(KeyCharactor = 'Backspace')"+
                "insert into outputStream;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        Thread.sleep(1000 * 10);

        siddhiAppRuntime.addCallback("outputStream", new StreamCallback() {
            public int eventCount = 0;
            public int timeSpent = 0;
            long startTime = System.currentTimeMillis();

            @Override
            public void receive(Event[] events) {

                for (Event event : events) {
                    eventCount++;

                    if (eventCount % 1000000 == 0) {
                        System.out.println("Throughput : " + (eventCount * 12));
                        System.out.println("Time spent :  " + (timeSpent * 1.0 / eventCount));
                        startTime = System.currentTimeMillis();
                        eventCount = 0;
                        timeSpent = 0;
                    }
                }
            }

        });
    }
}
