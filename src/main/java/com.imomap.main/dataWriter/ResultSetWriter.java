package com.imomap.main.dataWriter;

import com.imomap.main.KeyActionData;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class ResultSetWriter {
    public void writeResultSet(HashMap<String, KeyActionData> resultMap) throws SQLException {
        for(Map.Entry<String, KeyActionData> entry : resultMap.entrySet()) {
            String key = entry.getKey();


            KeyActionData value = entry.getValue();
            JDBCDriverConnector.getDbCon().insert("INSERT INTO keyevents (keyId, KeyCharactor, Action, KeyPressedTime, KeyReleasedTime, KeyDuration)\n" +
                    "VALUES ('"+value.getKeyId()+"','"+value.getKeyCharactor()+"','"+value.getAction()+"',"+value.getKeyPressedTime()+","+value.getKeyReleasedTime()+","+value.getKeyDuration()+")");

        }


    }
}




