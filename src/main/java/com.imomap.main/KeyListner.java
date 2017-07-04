
package com.imomap.main;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.HashMap;
import java.util.UUID;


public class KeyListner implements NativeKeyListener {

    int Count = 0;
    HashMap<String, KeyMapData> keyPressedEventMap;
    HashMap<String, KeyMapData> keyRelesedEventMap;
    HashMap<String, KeyMapData> keyTypedEventMap;
    volatile HashMap<String, KeyActionData> resultMap;
    String uniqueID;
    String currentKeyPressed;

    KeyListner() {
        keyPressedEventMap = new HashMap<String, KeyMapData>();
        keyRelesedEventMap = new HashMap<String, KeyMapData>();
        keyTypedEventMap = new HashMap<String, KeyMapData>();
        resultMap = new HashMap<String, KeyActionData>();

    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        uniqueID = UUID.randomUUID().toString();
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
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
        }
        Runnable task = () -> {
            KeyActionData actionData = new KeyActionData();
            System.out.println("Hello " + keyPressedEventMap.values().size());
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
                    keyPressedEventMap.remove(uuidArray[a].toString());
                    keyRelesedEventMap.remove(uuidArray[a].toString());
                }
            }
        };
        if (currentKeyPressed == "Enter") {

            task.run();

            Thread thread = new Thread(task);
            thread.start();

            System.out.println("Done!");
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
