
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
import org.wso2.siddhi.core.util.EventPrinter;

import java.util.HashMap;
import java.util.LinkedList;

import java.util.LinkedList;




public class Realtimecounter {

    static int eventCount = 0;
    public void shiddhiQueryExecute(LinkedList<Object[]> streamList) throws InterruptedException {

        SiddhiManager siddhiManager = new SiddhiManager();


 /* String siddhiApp = "define table counterTable (KeyId int, KeyCharactor String, keyCount long);" +

         "define stream cseEventStream (Keycharactor string, price float, getKeyDuration float,KeyReleasedTime long);" +
        "@info(name='query1')\n" +
        "from cseEventStream ['Backspace' == Keycharactor] " +
                "select count(Keycharactor) as keyCount\n" +
                "update counterTable\n" +
                " on counterTable.keyCount == keyCount;" +
                "@info(name='processing allValues')\n" +
                "from cseEventStream JOIN counterTable\n" +
                "select *" +
                "insert into outputStream;"; */

        String siddhiApp = "" +
                "define table counterTable (keyCount long);" +
                "define stream cseEventStream (KeyId String,Keycharactor string, Key String, getKeyDuration float,KeyReleasedTime long);" +
                "@info(name='counting allValues')\n" +
                "from cseEventStream ['Backspace' == Keycharactor] " +
                "select count(Keycharactor) as keyCount\n" +
                "insert into counterTable;"+
        "@info(name='processing allValues')\n" +
        "from cseEventStream JOIN counterTable\n" +
                "select *" +
                "insert into outputStream;";
//
//                "define stream cseEventStream (Keycharactor string, price float, KeyDuration long ,KeyReleasedTime long);" +
//                "define table counterTable (keyCount int);" +
//                "" +
//                "@info(name = 'query1') " +
//                "from cseEventStream['Backspace' == Keycharactor] " +
//                "select count(Keycharactor) as keyCount\n" +
//
//                "from cseEventStream join counterTable"+
//        "on counterTable.Keycharactor == cseEventStream.Keycharactor"+
//
//                "insert into outputStream ";
//       String siddhiApp1 = "" +
//                "define stream cseEventStream (Keycharactor string, price float, KeyDuration long ,KeyReleasedTime long);" +
//                "" +
//        "@info(name = 'query2') " +
//                "from " +
//                "select KeyDuration\n" +
//                "insert into outputStream ;";

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(siddhiApp);


        siddhiAppRuntime.addCallback("outputStream", new StreamCallback() {

            @Override
            public void receive(Event[] events) {
                for (Event event : events) {
                    // eventCount++;
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
