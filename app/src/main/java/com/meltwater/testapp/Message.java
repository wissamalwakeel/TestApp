package com.meltwater.testapp;

import android.text.Html;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wissam on 04/06/16.
 */
public class Message implements Comparable{
    private static final String TAG =  Message.class.toString();
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
        Log.v(TAG, "message created with message id:" + this.getMessageId());
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
            Log.v(TAG, "error extracting "+ field + " stackTrace:" + e.toString());
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
            Log.v(TAG, "error extracting "+ field + " stackTrace:" + e.toString());
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

    @Override
    public int compareTo(Object another) {
        final int before = -1;
        final int equal = 0;
        final int after = 1;
        Message other;
        if (another instanceof Message) {
            other = (Message) another;
            if (this.getMessageId().equals(other.getMessageId())) {
                return equal;
            } else if (Integer.valueOf(this.getMessageId()) > Integer.valueOf(other.getMessageId())) {
                return before;
            }
            return after;
        }
        return 0;
    }
}
