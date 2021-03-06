package com.meltwater.testapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by wissam on 05/06/16.
 */
public class MyHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
    NetworkImageView avatar;
    TextView posterName;
    TextView bodyText;
    ItemClickListener itemClickListener;


    public MyHolder(View itemView) {
        super(itemView);

        avatar = (NetworkImageView) itemView.findViewById(R.id.avatarImage);
        posterName = (TextView) itemView.findViewById(R.id.posterName);
        bodyText = (TextView) itemView.findViewById(R.id.bodyText);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(getLayoutPosition());
    }
}
