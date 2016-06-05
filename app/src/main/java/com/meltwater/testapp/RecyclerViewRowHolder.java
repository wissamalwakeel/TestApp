package com.meltwater.testapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;


/**
 * Created by wissam on 04/06/16.
 */
public class RecyclerViewRowHolder  extends RecyclerView.ViewHolder {

    protected NetworkImageView avatar;
    protected TextView posterName, bodyText;
    protected RelativeLayout relativeLayout;

    public RecyclerViewRowHolder(View itemView) {
        super(itemView);
        this.avatar = (NetworkImageView) itemView.findViewById(R.id.avatarImage);
        this.posterName = (TextView) itemView.findViewById(R.id.posterName);
        this.bodyText = (TextView) itemView.findViewById(R.id.bodyText);
    }
}
