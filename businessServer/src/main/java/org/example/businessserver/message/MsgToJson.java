package org.example.businessserver.config;

import org.json.JSONObject;

import java.io.IOException;

public class MsgToJson {
    public static JSONObject changeMsg(byte[] request) throws IOException {
        String jsonString = new String(request);
        JSONObject json = null;
        if (jsonString.indexOf("{") == 0) {
            json = new JSONObject(jsonString);
        } else if (jsonString.indexOf("{") == 1) {
            json = new JSONObject(jsonString.substring(1));
        } else if (jsonString.indexOf("{") == 2) {
            json = new JSONObject(jsonString.substring(2));
        } else if (jsonString.indexOf("{") == 3) {
            json = new JSONObject(jsonString.substring(3));
        }

        return json;
    }
}
