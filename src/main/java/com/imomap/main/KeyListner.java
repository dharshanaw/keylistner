
package com.imomap.main;

import com.imomap.main.analytics.KeyAggregateInfoReader;
import com.imomap.main.analytics.RealtimeEventAnalyzer;
import com.imomap.main.analytics.Realtimecounter;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;


public class KeyListner implements NativeKeyListener {
    int Count = 0;
    KeyAggregateInfoReader kl = new KeyAggregateInfoReader();

    HashMap<String, KeyMapData> keyPressedEventMap;
    HashMap<String, KeyMapData> keyRelesedEventMap;
    HashMap<String, KeyMapData> keyTypedEventMap;
    volatile HashMap<String, KeyActionData> resultMap;
    String uniqueID;
    String currentKeyPressed;


    public KeyListner() {
        keyPressedEventMap = new HashMap<String, KeyMapData>();
        keyRelesedEventMap = new HashMap<String, KeyMapData>();
        keyTypedEventMap = new HashMap<String, KeyMapData>();
        resultMap = new HashMap<String, KeyActionData>();

    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        uniqueID = UUID.randomUUID().toString();
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        KeyMapData keyMapData = new KeyMapData();
        keyMapData.setAction(KeyActions.KEY_PRESSES.toString());
        keyMapData.setKeyCharactor(NativeKeyEvent.getKeyText(e.getKeyCode()));
        keyMapData.setKeyId(uniqueID);
        keyMapData.setTimestamp(System.currentTimeMillis());
        currentKeyPressed = NativeKeyEvent.getKeyText(e.getKeyCode());
            this.getKeyAggrigatedInfo(keyMapData);

    }


    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        KeyMapData keyMapData = new KeyMapData();
        keyMapData.setAction(KeyActions.KEY_RELEASED.toString());
        keyMapData.setKeyCharactor(NativeKeyEvent.getKeyText(e.getKeyCode()));
        keyMapData.setKeyId(uniqueID);
        keyMapData.setTimestamp(System.currentTimeMillis());
       this.getKeyAggrigatedInfo(keyMapData);
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        KeyMapData keyMapData = new KeyMapData();
        keyMapData.setAction(KeyActions.KEY_TYPED.toString());
        keyMapData.setKeyCharactor(NativeKeyEvent.getKeyText(e.getKeyCode()));
        keyMapData.setKeyId(uniqueID);
        keyMapData.setTimestamp(System.currentTimeMillis());
        this.getKeyAggrigatedInfo(keyMapData);

    }


    private void getKeyAggrigatedInfo(KeyMapData keyMapData) {
        if (keyMapData.getAction().equals(KeyActions.KEY_PRESSES.toString())) {
            keyPressedEventMap.put(keyMapData.getKeyId(), keyMapData);
        } else if (keyMapData.getAction().equals(KeyActions.KEY_RELEASED.toString())) {
            keyRelesedEventMap.put(keyMapData.getKeyId(), keyMapData);
        } else if (keyMapData.getAction().equals(KeyActions.KEY_TYPED.toString())) {
            keyTypedEventMap.put(keyMapData.getKeyId(), keyMapData);
        } else {
            return;
        };



        Runnable task = () -> {
            KeyActionData actionData = new KeyActionData();
            KeyActionData timecal = new KeyActionData();
            Object[] uuidArray = keyPressedEventMap.keySet().toArray();
            for (int a = 0; a <= uuidArray.length - 1; a++) {
                KeyMapData keyPressedData = keyPressedEventMap.get(uuidArray[a].toString());
                if (keyRelesedEventMap.containsKey(uuidArray[a].toString())) {
                    KeyMapData keyRelesedData = keyRelesedEventMap.get(uuidArray[a].toString());
                    actionData.setAction(keyPressedData.getAction());
                    actionData.setKeyCharactor(keyPressedData.getKeyCharactor());
                    actionData.setKeyId(keyPressedData.getKeyId());
                    actionData.setKeyPressedTime(keyPressedData.getTimestamp());
                    actionData.setKeyReleasedTime(keyRelesedData.getTimestamp());
                    actionData.setKeyDuration(keyRelesedData.getTimestamp() - keyPressedData.getTimestamp());
                    resultMap.put(uuidArray[a].toString(), actionData);
                    KeyAggregateInfoReader infoReader = new KeyAggregateInfoReader();
                    //infoReader.analyzeEvent(resultMap);
                  //  infoReader.notifyAll();
                    keyPressedEventMap.remove(uuidArray[a].toString());
                    keyRelesedEventMap.remove(uuidArray[a].toString());

                }
            }
        };
        if (currentKeyPressed == "Enter"){

            task.run();
            Thread thread = new Thread(task);
            thread.start();
            LinkedList<Object[]> streamList = new LinkedList<>();
            resultMap.entrySet().forEach(entry -> {
                streamList.add(new Object[]{entry.getValue().getKeyCharactor(), 75.6f, entry.getValue().getKeyDuration(), entry.getValue().getKeyReleasedTime()});
            });
//System.out.println(streamList.toString());



           // RealtimeEventAnalyzer eventAnalyzer= new RealtimeEventAnalyzer();
           RealtimeEventAnalyzer counter = new RealtimeEventAnalyzer();

            try {
                counter.shiddhiQueryExecute(streamList);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }





        }

        }
    public static void main(String[] args) {

        try {

            GlobalScreen.registerNativeHook();

        } catch (NativeHookException ex) {

            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);

        }

        GlobalScreen.addNativeKeyListener(new KeyListner());

    }
}
