
package com.imomap.main.analytics;

import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

import com.imomap.main.KeyActionData;
import com.imomap.main.KeyMapData;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

import java.util.HashMap;
import java.util.LinkedList;

import java.util.LinkedList;


public class Realtimecounter {


    static int eventCount = 0;
    public void shiddhiQueryExecute(LinkedList<Object[]> streamList) throws InterruptedException {

        SiddhiManager siddhiManager = new SiddhiManager();
        String siddhiApp = "" +
                "define stream cseEventStream (Keycharactor string, price float, KeyPressed long,KeyReleased long, timestamp long);" +
                "" +
                "@info(name = 'query2') " +
                "from cseEventStream ['Backspace' == Keycharactor] " +
                "select *" +
                "insert into outputStream ;";


        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("outputStream", new StreamCallback() {
            // public int eventCount = 0;

            public int timeSpent = 0;
            long startTime = System.currentTimeMillis();

            @Override
            public void receive(Event[] events) {
                for (Event event : events) {
                    eventCount++;

                    if (eventCount % 100 == 0) {
                        System.out.println("Throughput : " + (eventCount * 12));
                        System.out.println("Time spent :  " + (timeSpent * 1.0 / eventCount));
                        startTime = System.currentTimeMillis();
                        eventCount = 0;
                        timeSpent = 0;

                    }
                    System.out.println("Key : " + (event.getData(0)));
                    System.out.println("Keyduration : " + (event.getData(3)));
                    timeSpent = 0;
                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        final String[] currentKeyPressed = new String[1];
        siddhiAppRuntime.start();
        int count = 1;
        count++;
        for (Object[] event:streamList
                ) {
            inputHandler.send(event);
        }
    }
}


