package com.meltwater.testapp;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import java.net.URL;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {
    private static final Logger logger = Logger.getLogger(MainActivity.class.toString());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<Message> newMessages = getMessagesList();

    }

    /**
     * This method will call other methods to get the content and return a List of Message objects
     * @return List <Message>
     */

    private List<Message> getMessagesList() {
        JSONObject object = new JSONObject();
        try {
            // Retrieving the messages from the given url using the https protocol.
            URL url = new URL("https://alpha-api.app.net/stream/0/posts/stream/global");
            object = httpsConnectionHandler(url);
        } catch (MalformedURLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        // Filtering the received JSON response and putting it into a list of Message objects
        return messageJsonArrayExtractor(object);
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
    private List<Message> messageJsonArrayExtractor(JSONObject jsonIn) {
        List<Message> messages = new LinkedList<>();
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

    @Override
    public void onStop() {
        super.onStop();
    }
}
