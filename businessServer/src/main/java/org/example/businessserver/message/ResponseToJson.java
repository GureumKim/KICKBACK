package org.koko.kokopangmulti.Braodcast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.koko.kokopangmulti.Object.Channel;
import org.koko.kokopangmulti.Object.ChannelList;
import org.koko.kokopangmulti.Object.SessionsInChannel;

import java.util.*;

public class ToJson {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String chatToJson(String userName, String message) {
        HashMap<String, String> chatMap = new LinkedHashMap<>();

        chatMap.put("type", "chat");
        chatMap.put("userName", userName);
        chatMap.put("message", message);

        String chatJson = null;

        try {
            chatJson = objectMapper.writeValueAsString(chatMap) + '\n';
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chatJson;
    }

    // 로비 유저목록
    public static String lobbySessionsToJson() {
        Map<String, Object> data = new LinkedHashMap<>();
        List<Object> jsonArray = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : ChannelList.getLobby().getSessionList().entrySet()) {
            Map<String, Object> jsonObject = new LinkedHashMap<>();

            jsonObject.put("userName", entry.getKey());
            jsonObject.put("userId", entry.getValue());

            jsonArray.add(jsonObject);
        }

        data.put("type", "sessionList");
        data.put("data", jsonArray);

        try {
            return objectMapper.writeValueAsString(data) + '\n';
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 채널목록
    public static String channelListToJson() {
        Map<String, Object> data = new LinkedHashMap<>();
        List<Object> jsonArray = new ArrayList<>();

        for (Map.Entry<Integer, Channel> entry : ChannelList.getChannelList().entrySet()) {
            Map<String, Object> temp = new LinkedHashMap<>();

            temp.put("channelIndex", entry.getKey());
            temp.put("channelName", entry.getValue().getChannelName());
            temp.put("cnt", entry.getValue().getSessionsInChannel().getCnt());
            temp.put("isOnGame", entry.getValue().getOnGame());

            jsonArray.add(temp);
        }

        data.put("type", "channelList");
        data.put("data", jsonArray);

        try {
            return objectMapper.writeValueAsString(data) + '\n';
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String channelSessionListToJSON(Channel channel) {
        Map<String, Object> data = new LinkedHashMap<>();
        List<Object> jsonArray = new ArrayList<>();

        SessionsInChannel sic = channel.getSessionsInChannel();
        List<Integer> isExisted = sic.getIsExisted();
        int cnt = sic.getCnt();

        for (int i = 0; i < 6; i++) {

            if (isExisted.get(i) == 1) {
                Map<String, Object> temp = new LinkedHashMap<>();
                String userName = channel.getIdxToName().get(i);
                temp.put("userName", userName);
                temp.put("userId", channel.getSessionList().get(userName));
                temp.put("isReady", sic.getIsReady(i));
                Boolean isHost = false;
                if (i == 0) {
                    isHost = true;
                }
                temp.put("isHost", isHost);

                jsonArray.add(temp);
                cnt--;
            }

            if (cnt == 0) break;

        }
        data.put("type", "channelSessionList");
        data.put("data", jsonArray);

        try {
            return objectMapper.writeValueAsString(data) + '\n';
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String channelInfoToJson(int channelIndex) {
        Channel channel = ChannelList.getChannelInfo(channelIndex);

        Map<String, Object> temp = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        Map<String, Object> data = new LinkedHashMap<>();

        temp.put("type", "channelInfo");

        data.put("channelIndex", channelIndex);
        data.put("channelName", channel.getChannelName());
        data.put("cnt", channel.getSessionsInChannel().getCnt());
        data.put("isOnGame", channel.getOnGame());

        list.add(data);

        temp.put("data", list);

        try {
            return objectMapper.writeValueAsString(temp) + '\n';
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String loadingToJson(JSONObject json) {
        HashMap<String, Object> temp = new LinkedHashMap<>();

        temp.put("userName", json.getString("userName"));
        temp.put("isLoading", json.getBoolean("isLoading"));

        try {
            return objectMapper.writeValueAsString(temp) + '\n';
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String positionToJson(JSONObject json) {
        HashMap<String, Object> temp = new LinkedHashMap<>();

        temp.put("type", "changePos");
        temp.put("userId", json.getInt("userId"));
        temp.put("x", json.getFloat("x"));
        temp.put("y", json.getFloat("y"));
        temp.put("z", json.getFloat("z"));
        temp.put("rw", json.getFloat("rw"));
        temp.put("rx", json.getFloat("rx"));
        temp.put("ry", json.getFloat("ry"));
        temp.put("rz", json.getFloat("rz"));

        try {
            return objectMapper.writeValueAsString(temp) + '\n';
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String scoreToJson(JSONObject json) {
        HashMap<String, Object> temp = new LinkedHashMap<>();

        temp.put("type", "score");
        temp.put("userId", json.getInt("userId"));
        temp.put("score", json.getInt("score"));

        try {
            return objectMapper.writeValueAsString(temp) + '\n';
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String clearToJson(JSONObject data) {
        HashMap<String, Object> temp = new LinkedHashMap<>();

        temp.put("type", "clear");
        temp.put("userId", data.getInt("userId"));

        try {
            return objectMapper.writeValueAsString(temp) + '\n';
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
