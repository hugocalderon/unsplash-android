package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.myapplication.retrofit.models.PhotoJson;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.myapplication.utils.MyConfig.SHARED_NAME;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView  recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Activity activity;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle("Favoritos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        activity = FavoriteActivity.this;

        settings = getSharedPreferences(SHARED_NAME, MODE_PRIVATE);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<PhotoJson> photos = new ArrayList<>();

        Map<String, ?> allEntries =settings.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());

            Gson gson = new Gson();
            photos.add(gson.fromJson(entry.getValue().toString(), PhotoJson.class));
        }

        //List<PhotoJson> photos = (List<PhotoJson>) settings.getAll();

        // specify an adapter (see also next example)
        mAdapter = new AdapterFavorites(activity,settings,photos);
        recyclerView.setAdapter(mAdapter);





    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, ScrollingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}