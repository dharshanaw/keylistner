/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.imomap.main;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class KeyActionData {
    String keyCharactor;
    String keyId;
    Long keyPressedTime;
    Long keyReleasedTime;
    Long keyDuration;
    String action;
     Set<String> spamPhoneNumbers;
     long timestamp;
    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }


    public String getKeyCharactor() {
        return keyCharactor;
    }

    public void setKeyCharactor(String keyCharactor) {
        this.keyCharactor = keyCharactor;
    }

    public Long getKeyPressedTime() {
        return keyPressedTime;
    }

    public void setKeyPressedTime(Long keyPressedTime) {
        this.keyPressedTime = keyPressedTime;
    }

    public Long getKeyReleasedTime() {
        return keyReleasedTime;
    }

    public void setKeyReleasedTime(Long keyReleasedTime) {
        this.keyReleasedTime = keyReleasedTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getKeyDuration() {
        return keyDuration;
    }

    public void setKeyDuration(Long keyDuration) {
        this.keyDuration = keyDuration;
    }

    public KeyActionData() {
        updateSpamPhoneNumbers();
    }

    public Set<String> getSpamPhoneNumbers() {
        if(timestamp - System.currentTimeMillis() > 10 ) {
            updateSpamPhoneNumbers();
        }
        return spamPhoneNumbers;
    }

    private void updateSpamPhoneNumbers() {
        Set<String> newSpamPhoneNumbers = new HashSet<>();
        //populate set from file on server
        spamPhoneNumbers = Collections.unmodifiableSet(newSpamPhoneNumbers);
        timestamp = System.currentTimeMillis();
    }


}
