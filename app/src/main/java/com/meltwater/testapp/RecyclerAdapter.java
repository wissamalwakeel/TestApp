package com.meltwater.testapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

/**
 * Created by wissam on 04/06/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewRowHolder> {

    private List<Message> messageList;
    private Context mContext;
    private ImageLoader mImageLoader;
    private int focusedItem = 0;

    public RecyclerAdapter(Context context, List<Message> messageList) {
        this.mContext = context;
        this.messageList = messageList;
    }

    @Override
    public RecyclerViewRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listiteam, null);
        RecyclerViewRowHolder holder = new RecyclerViewRowHolder(view);
        // TODO: if we want to make an item a clickable we can add a listner here to lunch an activity to go to the original post using the url and showing the full articale in a webView
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewRowHolder holder, int position) {
        Message message = messageList.get(position);
        holder.itemView.setSelected(focusedItem == position);

        holder.getLayoutPosition();

        mImageLoader = MySingleton.getInstance(mContext).getImageLoader();

        // setting the avatar, and adding a default avatar just in case no url
        holder.avatar.setImageUrl(message.getAvatarLink(), mImageLoader);
        holder.avatar.setDefaultImageResId(R.mipmap.ic_launcher);

        holder.posterName.setText(message.getPosterName());
        holder.bodyText.setText(message.getPostText());

    }

    public void clearAdapter() {
        messageList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != messageList ? messageList.size() : 0 );
    }


}
