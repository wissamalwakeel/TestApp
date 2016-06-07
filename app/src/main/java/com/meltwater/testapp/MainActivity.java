package com.meltwater.testapp;

import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity {
    private static final Logger logger = Logger.getLogger(MainActivity.class.toString());

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyAdapter adapter;

    private ArrayList<Message> messageList;
    private MessageHandler messageHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageHandler = new MessageHandler();
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // making the first call to populate the list
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        messageList = messageHandler.getMessagesList();
        adapter = new MyAdapter(this, messageList, swipeRefreshLayout);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    public void onStop() {
        super.onStop();
    }



}
