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

public class calculation {


    public class JoinRDBMSTableTestCase {
        private int inEventCount;
        private int EventCount;
        private boolean eventArrived;

        public void init() {


            inEventCount = 0;
            EventCount = 0;
            eventArrived = false;
            define StockStream (keyPressedTime Long, keyReleasedTime Long, keyDuration Long, action String, keyCharactor String);
            @from keyevents
            define StockTable (keyPressedTime Long, keyReleasedTime Long, keyDuration Long, keyCharactor String);

            @info(name = 'query1')
            from StockStream
            insert into StockTable;


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
