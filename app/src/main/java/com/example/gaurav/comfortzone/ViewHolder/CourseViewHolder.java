package com.example.gaurav.comfortzone.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaurav.comfortzone.Interface.ItemClickListener;
import com.example.gaurav.comfortzone.R;

/**
 * Created by Gaurav on 3/20/2018.
 */

public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView course_name;
    public ImageView course_image, share_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CourseViewHolder(View itemView) {
        super(itemView);

        course_name = (TextView)itemView.findViewById(R.id.course_name);
        course_image = (ImageView)itemView.findViewById(R.id.course_image);
        share_image = (ImageView)itemView.findViewById(R.id.btnShare);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
