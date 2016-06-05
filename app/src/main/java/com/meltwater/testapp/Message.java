package com.meltwater.testapp;

import android.text.Html;

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
    private String messageId;

    /**
     * Constructor that will receive a message JsonObject and call the filtering methods and assign the values
     * *** Not a god approach to add logic into the constructor usually i do it by initializing the object in a different method or pass already filtered values.
     * @param message
     */
    public Message(JSONObject message) {
        this.postText = extractMessageData(message, "text");
        this.posterName = extractUserData(message, "username");
        this.avatarLink = extractUserData(message, "avatar_image");
        this.messageId = extractMessageData(message, "id");
        this.posterName = this.posterName + "    Message ID:" + extractMessageData(message, "id");
    }


    /**
     * Extract the text of a message from the Json object
     * @param message
     * @return
     */
    private String extractMessageData(JSONObject message, String field) {
        try {
            if (message.has(field)) {
                String text = Html.fromHtml(message.getString(field)).toString();
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

    public String getAvatarLink() {
        return avatarLink;
    }

    public String getPosterName() {
        return posterName;
    }

    public String getPostText() {
        return postText;
    }

    public String getMessageId() {
        return this.messageId;
    }
}
