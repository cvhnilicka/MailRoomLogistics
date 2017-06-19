package com.example.cormick.mailroomlogistics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ViewBatchesActivity extends AppCompatActivity {

    Button bViewBatches;

    ListView batchlist;

    ArrayList<Batch> batches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_batches);

        batchlist = (ListView) findViewById(R.id.batchList);

        SQLDatabaseHandler db = new SQLDatabaseHandler(this);

        batches = (ArrayList<Batch>)db.getAllBatches();

//        Log.v("Batch size", batches.size() + " <-");
//
//        Batch tempBatch = batches.get(0);
//
//        Log.v("Batch dest", tempBatch.getDestination());

//        ArrayAdapter<Batch> arrayAdapter = new ArrayAdapter<Batch>(this,
//                android.R.layout.simple_list_item_1, batches);

        ArrayAdapter<Batch> batchAdapter = new batchArrayAdapter(this, 0, batches);


        AdapterView.OnItemClickListener adapterviewlistener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewBatchesActivity.this, viewBatch.class);

                Batch selectedBatch = batches.get(position);

                intent.putExtra("SELECTED_BATCH", selectedBatch);


                startActivity(intent);
            }


        };

        batchlist.setOnItemClickListener(adapterviewlistener);

        batchlist.setAdapter(batchAdapter);





    }

//    public void viewBatches(View view) {
//        SQLDatabaseHandler db = new SQLDatabaseHandler(this);
//        List<Batch> list = db.getAllBatches();
//
//        System.out.println(list);
//
//        Batch tempbatch = list.get(0);
//
////        byte[] temparr = tempbatch.getSignatureArray();
////
////        for(int i = 0; i < temparr.length; i++) {
////
//////            System.out.println("TEMPARR " + temparr[i]);
////        }
//
////        Bitmap bmp = BitmapFactory.decodeByteArray(temparr,0,temparr.length);
//        signatureView = (ImageView) findViewById (R.id.showSignature);
//
//        String imageUrl = tempbatch.getPhoto_path();
//
//        Log.v("Sanity: ", imageUrl);
//
//        File file = new File(imageUrl);
//
//        if(file == null) {
//            Log.v("DEBUG", "FUCK");
//        }
//
//        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//
//        signatureView.setImageBitmap(myBitmap);
//
////        if (bmp == null ) {
////            Log.v("DEBUG", "FUCK");
////        }
//
////        DisplayMetrics dm = new DisplayMetrics();
////        getWindowManager().getDefaultDisplay().getMetrics(dm);
////
////        signatureView.setMinimumHeight(dm.heightPixels);
////        signatureView.setMinimumWidth(dm.widthPixels);
////        signatureView.setImageBitmap(bmp);
//          signatureView.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, signatureView.getWidth(),
//                signatureView.getHeight(), false));
////        signatureView.setImageBitmap(bmp);
//
//
//    }


}
