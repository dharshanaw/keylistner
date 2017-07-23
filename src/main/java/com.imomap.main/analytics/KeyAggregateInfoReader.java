/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.imomap.main.analytics;

import com.imomap.main.KeyActionData;
import com.imomap.main.KeyActions;
import com.imomap.main.KeyMapData;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

import java.util.HashMap;
import java.util.UUID;


public class KeyAggregateInfoReader {
    static int eventCount = 0;

    public void analyzeEvent(HashMap<String, KeyActionData> resultMap)
    {
        KeyMapData keyMapData = new KeyMapData();

keyMapData.getAction();


    }

    public static void main(String[] args) throws InterruptedException {
        SiddhiManager siddhiManager = new SiddhiManager();

        String siddhiApp = "" +
                "define stream cseEventStream (Keycharactor string, price float, KeyDuration long, timestamp long);" +
                "" +
                "@info(name = 'query1') " +
                "from cseEventStream['Backspace' == Keycharactor] " +
                "select * " +
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
                    timeSpent += (System.currentTimeMillis() - (Long) event.getData(3));

                    System.out.println("Key : " + (event.getData(0)));
                    System.out.println("Count :  " + eventCount);
                    timeSpent = 0;

                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cseEventStream");
        final String[] currentKeyPressed = new String[1];
        siddhiAppRuntime.start();
        int count = 1;
        count++;


        inputHandler.send(new Object[]{});
        inputHandler.send(new Object[]{"S", 75.6f, 100, System.currentTimeMillis()});
        inputHandler.send(new Object[]{"Backspace", 75.6f, 100, System.currentTimeMillis()});
        inputHandler.send(new Object[]{"E", 55.6f, 100, System.currentTimeMillis()});

    }
}
