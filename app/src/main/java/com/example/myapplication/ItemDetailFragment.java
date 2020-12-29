package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.dummy.DummyContent;

import java.util.List;

import retrofit.APIClient;
import retrofit.APIInterface;
import retrofit.models.PhotoJson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    MyRecyclerViewAdapter mAdapter;
    RecyclerView main_recycler_view;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
       activity = getActivity();
        main_recycler_view = (RecyclerView) rootView.findViewById(R.id.main_recycler_view);
        main_recycler_view.setHasFixedSize(true);
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

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
           // ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
        }

        return rootView;
    }
}