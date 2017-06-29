
package com.imomap.main;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;


public class KeyListner implements NativeKeyListener {

    int Count = 0;
    HashMap<String, KeyMapData> keyPressedEventMap;
    HashMap<String, KeyMapData> keyRelesedEventMap;
    HashMap<String, KeyMapData> keyTypedEventMap;
    String uniqueID;

    KeyListner() {
        keyPressedEventMap = new HashMap<String, KeyMapData>();
        keyRelesedEventMap = new HashMap<String, KeyMapData>();
        keyTypedEventMap = new HashMap<String, KeyMapData>();

    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        uniqueID = UUID.randomUUID().toString();
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        KeyMapData keyMapData = new KeyMapData();
        keyMapData.setAction(KeyActions.KEY_PRESSES.toString());
        keyMapData.setKeyCharactor(NativeKeyEvent.getKeyText(e.getKeyCode()));
        keyMapData.setKeyId(uniqueID);
        keyMapData.setTimestamp(new Timestamp(System.currentTimeMillis()));
        this.getKeyAggrigatedInfo(keyMapData);
    }


    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        KeyMapData keyMapData = new KeyMapData();
        keyMapData.setAction(KeyActions.KEY_RELEASED.toString());
        keyMapData.setKeyCharactor(NativeKeyEvent.getKeyText(e.getKeyCode()));
        keyMapData.setKeyId(uniqueID);
        keyMapData.setTimestamp(new Timestamp(System.currentTimeMillis()));
        this.getKeyAggrigatedInfo(keyMapData);


    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        KeyMapData keyMapData = new KeyMapData();
        keyMapData.setAction(KeyActions.KEY_TYPED.toString());
        keyMapData.setKeyCharactor(NativeKeyEvent.getKeyText(e.getKeyCode()));
        keyMapData.setKeyId(uniqueID);
        keyMapData.setTimestamp(new Timestamp(System.currentTimeMillis()));
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
        }


    }

    public static void main(String[] args) {

        HashMap<Integer, String> map = new HashMap<Integer, String>();
        try {

            GlobalScreen.registerNativeHook();

        } catch (NativeHookException ex) {

            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        //GlobalScreen.addNativeKeyListener();
        GlobalScreen.addNativeKeyListener(new KeyListner());


    }
}
