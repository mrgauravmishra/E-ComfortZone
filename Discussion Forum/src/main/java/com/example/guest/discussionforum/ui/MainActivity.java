package com.example.guest.discussionforum.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.guest.discussionforum.MessageListActivity;
import com.example.guest.discussionforum.R;
import com.example.guest.discussionforum.R2;
import com.example.guest.discussionforum.adapters.CategoriesAdapter;
import com.example.guest.discussionforum.models.Category;
import com.example.guest.discussionforum.models.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mMessagesReference;
    private ValueEventListener mMessagesReferenceListener;
    private CategoriesAdapter mCategoriesAdapter;
    private String selectedCategoryKey;

    @BindView(R2.id.createMessage) Button mCreateMessage;
    @BindView(R2.id.viewMessages) Button mViewMessages;
    @BindView(R2.id.viewCategories) Button mViewCategories;
    @BindView(R2.id.content) EditText mContent;
    @BindView(R2.id.title) EditText mTitle;
    @BindView(R2.id.categorySpinner) Spinner mCategorySpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mMessagesReference = FirebaseDatabase
                .getInstance()
                .getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mCreateMessage.setOnClickListener(this);
        mViewMessages.setOnClickListener(this);
        mViewCategories.setOnClickListener(this);

        mMessagesReferenceListener = mMessagesReference.child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<Category> categories = new ArrayList<Category>();

                for(DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String categoryName = areaSnapshot.child("name").getValue(String.class);
                    String categoryKey = areaSnapshot.getKey();
                    Category category = new Category(categoryName, categoryKey);
                    categories.add(category);
                }

                Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
                mCategoriesAdapter = new CategoriesAdapter(MainActivity.this, android.R.layout.simple_spinner_item, categories);
                mCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorySpinner.setAdapter(mCategoriesAdapter);

                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedCategoryKey = categories.get(i).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v == mCreateMessage) {
            String title = mTitle.getText().toString();
            String content = mContent.getText().toString();

            Message message = new Message(title, content, selectedCategoryKey);
            saveMessageToFirebase(message);
            Toast.makeText(MainActivity.this, "Message Saved", Toast.LENGTH_SHORT).show();
        }
        if(v == mViewMessages) {
            Intent intent = new Intent(MainActivity.this, MessageListActivity.class);
            startActivity(intent);
        }
        if(v == mViewCategories) {
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            startActivity(intent);
        }
    }

    public void saveMessageToFirebase(Message message) {
        mMessagesReference.child("messages").push().setValue(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMessagesReference.removeEventListener(mMessagesReferenceListener);
    }


}
