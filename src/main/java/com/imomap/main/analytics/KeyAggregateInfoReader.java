package com.imomap.main.analytics;

import com.imomap.main.KeyActions;
import com.imomap.main.KeyMapData;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.LinkedList;

public class KeyAggregateInfoReader {
    static int eventCount = 0;

    public static void main(String[] args) throws InterruptedException {

        LinkedList<Object[]> streamList = new LinkedList<>();
        streamList.add(new Object[]{"E", 75.6f, 100, System.currentTimeMillis()});
        streamList.add(new Object[]{"Backspace", 56.6f, 50, System.currentTimeMillis()});
        streamList.add(new Object[]{"E", 55.6f,100, System.currentTimeMillis()});
        streamList.add(new Object[]{"Backspace", 75.6f, 40, System.currentTimeMillis()});
        streamList.add(new Object[]{"Backspace", 75.6f, 80, System.currentTimeMillis()});

        RealtimeEventAnalyzer eventAnalyzer= new RealtimeEventAnalyzer();
        Realtimecounter counter = new Realtimecounter();
        eventAnalyzer.shiddhiQueryExecute(streamList);
       counter.shiddhiQueryExecute(streamList);
    }
}