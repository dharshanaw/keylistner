package com.imomap.main.analytics.performance;



import com.imomap.main.dataWriter.ResultSetWriter;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import static java.awt.SystemColor.info;
import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;

public class PerceptionCalculator {


    public class JoinRDBMSTableTestCase {
        private int inEventCount;
        private int EventCount;
        private boolean eventArrived;

        public void init() {


            inEventCount = 0;
            EventCount = 0;
            eventArrived = false;


            String siddhiApp = "" +
                    "define stream cseEventStream (keyPressedTime Long, keyReleasedTime Long, keyDuration Long, action String,keyCharactor String); " +
                    "" +
                    "@info(name ='query1') " +
                    "from keyevents " +
                    "select keyPressedTime,keyReleasedTime,keyDuration,keyCharactor " +
                    "insert into outputStream ;";


        }

        @Override
        public void receive(Event[] inEvents, Event[] Events) {

            if (keyCharactor = 'Backspace') {
                inEventCount++;
                EventCount = inEventCount;
                System.out.println("backspace frequency" + EventCount);
            }

        }

    }
}
