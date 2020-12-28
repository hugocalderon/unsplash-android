package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto;

import java.util.List;

import retrofit.APIClient;
import retrofit.APIInterface;
import retrofit.models.PhotoJson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {
    MyRecyclerViewAdapter mAdapter;
    RecyclerView main_recycler_view;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        activity = MainActivity2.this;

      main_recycler_view = (RecyclerView) findViewById(R.id.main_recycler_view);

        main_recycler_view.setHasFixedSize(true);
        //main_recycler_view.itemAnimator = null;
        //main_recycler_view.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL):

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call call = apiInterface.getPhotos(1);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (response.code() == 200) {
                        List<PhotoJson> photos = (List<PhotoJson>) response.body();

                        mAdapter = new MyRecyclerViewAdapter(activity,photos);
                        main_recycler_view.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        //main_recycler_view.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
                        //mAdapter.setClickListener(this);
                        main_recycler_view.setAdapter(mAdapter);
                        Log.v("","");

                    } else {
                        Toast.makeText(MainActivity2.this,"Ha ocurrido un error desconocido", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    //Utils.showToast(activity, e.getMessage());
                    Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                //Log.v("", "Erorr > " + t.getMessage());
                //Utils.showToast(activity, activity.getString(R.string.an_unknown_error_occurred));
                Toast.makeText(MainActivity2.this, t.getMessage(), Toast.LENGTH_LONG).show();
                //closeProgress();
            }
        });
    }
}