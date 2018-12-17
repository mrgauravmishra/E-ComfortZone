package com.example.gaurav.comfortzone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gaurav.comfortzone.Common.Common;
import com.example.gaurav.comfortzone.Interface.ItemClickListener;
import com.example.gaurav.comfortzone.Model.Course;
import com.example.gaurav.comfortzone.ViewHolder.CourseViewHolder;
import com.facebook.CallbackManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class CourseList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference courseList;

    String categoryId="";

    FirebaseRecyclerAdapter<Course,CourseViewHolder> adapter;

    //Facebook Share
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    //create target from picasso
    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            //create Photo from bitmap
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();
            if (ShareDialog.canShow(SharePhotoContent.class))
            {
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content);
            }

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);


        //Init Facebook
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        //FireBase
        database = FirebaseDatabase.getInstance();
        courseList = database.getReference("Course");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_course);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get Intent Here
        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null)
        {
            if (Common.isConnectedToInternet(getBaseContext()))
                loadListCourse(categoryId);
            else
            {
                Toast.makeText(CourseList.this, "Please check your Connection (-.-)!!",Toast.LENGTH_SHORT).show();
                return;
            }
        }


    }

    @Override
    protected void onResume() {

        super.onResume();
        loadListCourse(categoryId);
    }


    private void loadListCourse(String categoryId) {

        Query listCourseByCategoryId = courseList.orderByChild("menuId").equalTo(categoryId);


        FirebaseRecyclerOptions<Course> options =
                new FirebaseRecyclerOptions.Builder<Course>()
                        .setQuery(listCourseByCategoryId, Course.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Course, CourseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CourseViewHolder viewHolder, final int position, @NonNull final Course model) {
                viewHolder.course_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.course_image);


                //click to sare
                viewHolder.share_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Picasso.with(getApplicationContext())
                                .load(model.getImage())
                                .into(target);
                    }
                });

                final Course local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int postion, boolean isLongClick) {
                        //Start new Activity
                        Intent courseDetail = new Intent(CourseList.this,CourseDetail.class);
                        courseDetail.putExtra("CourseId",adapter.getRef(position).getKey());   //send CourseId to new Activity
                        startActivity(courseDetail);

                        //Toast.makeText(CourseList.this, "" + local.getName(), Toast.LENGTH_SHORT).show();

                    }
                });

            }


        @Override
        public CourseViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.course_item, parent, false);
            return new CourseViewHolder(itemView);
        }
        };
        adapter.startListening();

        //set Adapter
       // Log.d("TAG",""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);

    }




    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
