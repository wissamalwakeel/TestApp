package com.meltwater.testapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by wissam on 05/06/16.
 */
public class MessageHandler {
    private static final Logger logger = Logger.getLogger(MessageHandler.class.toString());
    /**
     * This method will call other methods to get the content and return a List of Message objects
     * @return List <Message>
     */

    public ArrayList<Message> getMessagesList() {
        JSONObject object = new JSONObject();
        try {
            // Retrieving the messages from the given url using the https protocol.
            URL url = new URL("https://alpha-api.app.net/stream/0/posts/stream/global");
            object = httpsConnectionHandler(url);
        } catch (MalformedURLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        // Filtering the received JSON response and putting it into a list of Message objects
        return sortMessageList(messageJsonArrayExtractor(object));
    }

    /**
     *  Macke the Https connection to the given url and returns a json object formated content
     * @param url teh api url to receive the messages
     * @return JSONObject the json formatted response
     */
    private JSONObject httpsConnectionHandler(URL url) {
        JSONObject object= new JSONObject();
        try {
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            InputStream in = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            object = new JSONObject(br.readLine());
            br.close();
        } catch (IOException | JSONException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return object;
    }

    /**
     *  extruct the messages from the Json response and return a list of Message Objects
     * @param jsonIn
     * @return List <Message>
     */
    private ArrayList<Message> messageJsonArrayExtractor(JSONObject jsonIn) {
        ArrayList<Message> messages = new ArrayList<>();
        if (jsonIn != null) {
            try {
                JSONArray data = new JSONArray(jsonIn.getString("data"));
                if (data !=null && data.length() > 0 ) {
                    for (int i = 0 ; i< data.length() ; i++) {
                        JSONObject message = data.getJSONObject(i);
                        messages.add(new Message(message));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return messages;
    }


    public ArrayList<Message> addNewMessages(ArrayList<Message> messageList) {
        ArrayList<Message> tempMessageList = getMessagesList();
        ArrayList<String> tempIds = getIds(tempMessageList);
        ArrayList<String> ids = getIds(messageList);

        for (String id: ids) {
            if (tempIds.contains(id)) {
                tempIds.remove(id);
            }
        }

        for (String id : tempIds) {
            for (Message message : tempMessageList) {
                if (message.getMessageId().equals(id)) {
                    messageList.add(message);
                }
            }
        }

        return sortMessageList(messageList);
    }

    public ArrayList<Message> sortMessageList(ArrayList<Message> messageList) {
        ArrayList<Message> sortedMessageList = new ArrayList<>();
        ArrayList<String> ids = getIds(messageList);
        Collections.sort(ids);
        Collections.reverse(ids);

        for (String id : ids) {
            for (Message message : messageList) {
                if (message.getMessageId() == id) {
                    sortedMessageList.add(message);
                }
            }
        }
        return sortedMessageList;
    }


    private ArrayList<String> getIds(ArrayList<Message> messageList) {
        ArrayList<String> ids = new ArrayList<>();
        for (Message message : messageList) {
            ids.add(message.getMessageId());
        }
        return ids;
    }

}
