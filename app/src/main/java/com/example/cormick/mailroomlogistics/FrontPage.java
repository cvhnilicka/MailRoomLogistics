package com.example.cormick.mailroomlogistics;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FrontPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
    }

    public void initiateNewBatch(View view) {
        // respond to the new batch button
        Intent intent = new Intent(this, NewBatchActivity.class);
        System.out.println("Starting new intent");
        startActivity(intent);
        System.out.println("Started successfully");
    }
}
