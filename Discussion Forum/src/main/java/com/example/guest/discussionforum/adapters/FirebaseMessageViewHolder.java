package com.example.guest.discussionforum.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.guest.discussionforum.Constants;
import com.example.guest.discussionforum.R;
import com.example.guest.discussionforum.models.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseMessageViewHolder extends RecyclerView.ViewHolder {
    View mView;
    Context mContext;

    public FirebaseMessageViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindMessage(Message message) {
        TextView messageTitleTextView = (TextView) mView.findViewById(R.id.titleMessage);
        TextView messageContentTextView = (TextView) mView.findViewById(R.id.contentMessage);

        messageTitleTextView.setText(message.getTitle());
        messageContentTextView.setText(message.getContent());
    }

}
