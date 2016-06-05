package com.meltwater.testapp;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

/**
 * Created by wissam on 04/06/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyHolder> {



    private Context context;
    private ArrayList<Message> messageList;
    private ImageLoader imageLoader;
    SwipeRefreshLayout swipeRefreshLayout;

    public MyAdapter(Context context, ArrayList<Message> messageList, SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.messageList = messageList;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.imageLoader = MySingleton.getInstance(context).getImageLoader();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listiteam, parent, false);
        MyHolder holder = new MyHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.avatar.setImageUrl(messageList.get(position).getAvatarLink(),imageLoader);
        holder.posterName.setText(messageList.get(position).getPosterName());
        holder.bodyText.setText(messageList.get(position).getPostText());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(context, messageList.get(position).getPostText(),Toast.LENGTH_SHORT).show();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // add new messages heres
                messageList.add(0,messageList.get(0));
                MyAdapter.this.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        },3000);
    }

    public void clearAdapter() {
        messageList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != messageList ? messageList.size() : 0 );
    }

    public Context getContext() {
        return context;
    }

    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    public ImageLoader getmImageLoader() {
        return imageLoader;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }
}
