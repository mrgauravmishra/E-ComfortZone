package com.example.guest.discussionforum;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.discussionforum.adapters.FirebaseMessageViewHolder;
import com.example.guest.discussionforum.models.Message;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageListActivity extends AppCompatActivity {
    private DatabaseReference mMessageReference;
    private FirebaseRecyclerAdapter<Message, FirebaseMessageViewHolder> mFirebaseAdapter;

    @BindView(R2.id.messageRecyclerView) RecyclerView mMessageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);

        mMessageReference = FirebaseDatabase
                .getInstance().
                        getReference
                                (Constants.FIREBASE_MESSAGE);
        setUpFirebaseAdapter();
    }
    private void setUpFirebaseAdapter() {

        FirebaseRecyclerOptions<Message> options= new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(mMessageReference,Message.class)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Message, FirebaseMessageViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseMessageViewHolder viewHolder, int position, @NonNull Message model) {
                viewHolder.bindMessage(model);

            }

            @Override
            public FirebaseMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.message_list_item,parent,false);

                return new FirebaseMessageViewHolder(itemView);
            }
        };

        mMessageRecyclerView.setHasFixedSize(true);
        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mFirebaseAdapter.cleanup();
    }
}
