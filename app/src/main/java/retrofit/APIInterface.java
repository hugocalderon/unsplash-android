package retrofit;

import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto;

import java.util.List;

import retrofit.models.PhotoJson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import static utils.MyConfig.URL_SERVER;

/**
 * Created by 503consulting on 20/9/17.
 */

public interface APIInterface {

    @Headers("Authorization:  Client-ID k7ulZqGku77Lhzc0YavFYRR4VhI7VmeBM8-sdeA5azY")
    @GET(URL_SERVER)
    Call<List<PhotoJson>> getPhotos(@Query("page") Integer page);

}
