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

public class KeyActionData {
    String keyCharactor;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    String keyId;
    Long keyPressedTime;

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

    Long keyReleasedTime;
    String action;

}
