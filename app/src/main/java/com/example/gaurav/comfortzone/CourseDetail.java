package com.example.gaurav.comfortzone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaurav.comfortzone.Common.Common;
import com.example.gaurav.comfortzone.Model.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseDetail extends AppCompatActivity {

    TextView course_name,course_price,course_description;
    ImageView course_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
   // ElegantNumberButton numberButton;

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    String courseId="";

    FirebaseDatabase database;
    DatabaseReference course;
    private ArrayList videoID;
    Bundle B ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        videoID = new ArrayList<String>();
        addVideoID();
        //FireBase
        database = FirebaseDatabase.getInstance();
        course = database.getReference("Course");
           B = new Bundle();
        //Init View
        //numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
       // btnCart = (FloatingActionButton)findViewById(R.id.btnCart);

        course_description = (TextView)findViewById(R.id.course_description);
        course_name = (TextView)findViewById(R.id.course_name);
        course_price = (TextView)findViewById(R.id.course_price);
        course_image = (ImageView)findViewById(R.id.img_course);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(CourseDetail.this, R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        final Intent youTube = new Intent(CourseDetail.this,Youtube.class);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch (position) {

                    case 1:
                        B.clear();
                        B.putString("videoID", (String) videoID.get(0));
                        youTube.putExtras(B);
                        startActivity(youTube);
                        break;
                    case 2:
                        Toast.makeText(parent.getContext(), "Unit 2", Toast.LENGTH_SHORT).show();
                        B.clear();
                        B.putString("videoID", (String) videoID.get(1));
                        youTube.putExtras(B);
                        startActivity(youTube);
                        break;
                    case 3:
                        Toast.makeText(parent.getContext(), "Unit 3", Toast.LENGTH_SHORT).show();
                        B.clear();
                        B.putString("videoID", (String) videoID.get(2));
                        youTube.putExtras(B);
                        startActivity(youTube);
                        break;
                    case 4:
                        Toast.makeText(parent.getContext(), "Unit 4", Toast.LENGTH_SHORT).show();
                        B.clear();
                        B.putString("videoID", (String) videoID.get(3));
                        youTube.putExtras(B);
                        startActivity(youTube);
                        break;
                    case 5:
                        Toast.makeText(parent.getContext(), "Unit 5", Toast.LENGTH_SHORT).show();
                        B.clear();
                        B.putString("videoID", (String) videoID.get(4));
                        youTube.putExtras(B);
                        startActivity(youTube);
                        break;
                    case 6:
                        Toast.makeText(parent.getContext(), "Unit 6", Toast.LENGTH_SHORT).show();
                        B.clear();
                        B.putString("videoID", (String) videoID.get(5));
                        youTube.putExtras(B);
                        startActivity(youTube);
                        break;


                    //Toast.makeText(CourseDetail.this, list.getItemAtPosition(position) + "",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //spinner.setHint("Select Country");

            }
        });

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        //Get Course id from intent
        if (getIntent() != null)
            courseId = getIntent().getStringExtra("CourseId");
        if (!courseId.isEmpty())
        {
            if (Common.isConnectedToInternet(getBaseContext()))
                getDetailCourse(courseId);
            else
            {
                Toast.makeText(CourseDetail.this, "Please check your Connection (-.-)!!",Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private void addVideoID() {
        videoID.add("YOG8zU79jL4");
        videoID.add("PbQWbx32uak");
        videoID.add("6E7hq1Ipq9U");
        videoID.add("CI9cl0HDomQ");
        videoID.add("9UIw3vzLKC4");
        videoID.add("ksmsp9OzAsI");

    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    private void getDetailCourse(String courseId) {
        course.child(courseId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Course course = dataSnapshot.getValue(Course.class);

                //set Image
                Picasso.with(getBaseContext()).load(course.getImage())
                        .into(course_image);

                collapsingToolbarLayout.setTitle(course.getName());

                course_price.setText(course.getPrice());

                course_name.setText(course.getName());

                course_description.setText(course.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
}
