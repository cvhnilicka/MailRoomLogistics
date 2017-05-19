package com.example.cormick.mailroomlogistics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewBatchesActivity extends AppCompatActivity {

    Button bViewBatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_batches);
    }

    public void viewBatches(View view) {
        SQLDatabaseHandler db = new SQLDatabaseHandler(this);
        db.getAllBatches();
    }


}
