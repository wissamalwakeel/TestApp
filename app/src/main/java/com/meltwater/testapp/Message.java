package com.meltwater.testapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by wissam on 04/06/16.
 */
public class Message {
    private static final Logger logger = Logger.getLogger(Message.class.toString());
    private String avatarLink;
    private String posterName;
    private String postText;

    public Message(JSONObject message) {
        this.postText = extractText(message);
        this.posterName = extractUserData(message, "username");
        this.avatarLink = extractUserData(message, "avatar_image");
    }

    private String extractText(JSONObject message) {
        try {
            if (message.has("text")) {
                String text = message.getString("text");
                return text;
            }
        } catch (JSONException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return "";
    }

    private String extractUserData(JSONObject message, String field) {
        try {
            JSONObject userData = message.getJSONObject("user");
            String data = userData.getString(field);
            if (field.equals("avatar_image")) {
                JSONObject avatar = new JSONObject(data);
                data = avatar.getString("url");
            }
            return data;
        } catch (JSONException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return "";
    }
}
