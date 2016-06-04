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

    /**
     * Constructor that will receive a message JsonObject and call the filtering methods and assign the values
     * *** Not a god approach to add logic into the constructor usually i do it by initializing the object in a different method or pass already filtered values.
     * @param message
     */
    public Message(JSONObject message) {
        this.postText = extractText(message);
        this.posterName = extractUserData(message, "username");
        this.avatarLink = extractUserData(message, "avatar_image");
    }

    /**
     * Extract the text of a message from the Json object
     * @param message
     * @return
     */
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

    /**
     * Extract the name and the avatar url from the json object depending on what field you wish to extract
     * @param message
     * @param field
     * @return
     */
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
