package com.imomap.main.analytics;

import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

import java.util.LinkedList;
public class Realtimeduration {

        static int eventCount = 0;
        public void shiddhiQueryExecute(LinkedList<Object[]> streamList) throws InterruptedException {

            SiddhiManager siddhiManager = new SiddhiManager();

            String siddhiApp = "" +

                    "define stream cseEventStream (StudentId String, KeyId String,Keycharactor string, getKeyDuration long,KeyReleasedTime long);" +
                    "@info(name='counting query2')\n" +
                    "from cseEventStream [150 < getKeyDuration ] " +
                    "select *" +
                    "insert into outputStream;";


            SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

            siddhiAppRuntime.addCallback("outputStream", new StreamCallback() {

                @Override
                public void receive(Event[] events) {
                    for (Event event : events) {
                        // eventCount++;
                        System.out.println("Key :******************************************** ");
                        System.out.println("Key : " + (event.getData(0)));
                        System.out.println("Keyduration : " + (event.getData(3)));
                        System.out.println("Keypress : " + (event.getData(2)));
                        System.out.println("Keypress count : " + (event.getData(4)));
                    }
                }
            });

            InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
            //final String[] currentKeyPressed = new String[1];
            siddhiAppRuntime.start();

            System.out.println("sss"+ streamList);

            int count = 1;
            // count++;

            for (Object[] event:streamList
                    ) {

                inputHandler.send(event);

            }

            siddhiAppRuntime.shutdown();
            siddhiManager.shutdown();

        }
    }



