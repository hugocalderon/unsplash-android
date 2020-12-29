package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.retrofit.APIClient;
import com.example.myapplication.retrofit.APIInterface;
import com.example.myapplication.retrofit.models.PhotoJson;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.utils.MyConfig.SHARED_NAME;

public class ScrollingActivity extends AppCompatActivity {
    AdapterPhotos mAdapter;
    RecyclerView main_recycler_view;
    Activity activity;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        settings = getSharedPreferences(SHARED_NAME, MODE_PRIVATE);

        activity = ScrollingActivity.this;
        main_recycler_view = (RecyclerView) findViewById(R.id.main_recycler_view);
        main_recycler_view.setHasFixedSize(true);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call call = apiInterface.getPhotos(1);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (response.code() == 200) {
                        List<PhotoJson> photos = (List<PhotoJson>) response.body();

                        mAdapter = new AdapterPhotos(activity,settings,photos);
                        main_recycler_view.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                        //main_recycler_view.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
                        //mAdapter.setClickListener(this);
                        main_recycler_view.setAdapter(mAdapter);
                        Log.v("","");

                    } else {
                        Toast.makeText(activity,"Ha ocurrido un error desconocido", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    //Utils.showToast(activity, e.getMessage());
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                //Log.v("", "Erorr > " + t.getMessage());
                //Utils.showToast(activity, activity.getString(R.string.an_unknown_error_occurred));
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
                //closeProgress();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(activity,FavoriteActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}