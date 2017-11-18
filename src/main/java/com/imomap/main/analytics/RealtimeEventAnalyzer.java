package com.imomap.main.analytics;

import com.imomap.main.KeyActionData;
import com.imomap.main.KeyMapData;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
public class RealtimeEventAnalyzer {

    static int eventCount = 0;


    public void shiddhiQueryExecute(LinkedList<Object[]> streamList) throws InterruptedException {
        SiddhiManager siddhiManager = new SiddhiManager();

        String siddhiApp =
                " @From(eventtable='keyevents',jdbc.url='jdbc:mysql://localhost:3386/keytypeanalytics', table.name='emotion')"+

                        "define stream dataIn (keyId int,  string keycharactor,  );" +
                        " define stream insertStream( (keyId int, keycharactor string);" +

                        "define table roomTable (keyId int, keycharactor string);" +
                        "from dataIn[dataIn.roomType=='single']" +

                        "select  dataIn.keyId as keyId,dataIn.keycharactor as keycharactor" +
                        "insert into insertStream;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);

        siddhiAppRuntime.addCallback("insertStream", new StreamCallback() {
            public int eventCount = 0;
            public int timeSpent = 0;

            @Override

            public void receive(Event[] events) {
                for (Event event : events) {
                    eventCount++;

                    System.out.println("Key : " + (event.getData(0)));
                    System.out.println("Keyduration : " + (event.getData(1)));
                    System.out.println("Count :  " + (event.getData(2)));
                }
            }
        });

    }
}
