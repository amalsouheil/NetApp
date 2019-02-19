package amal.souheil.netapp.Utils;

import java.util.List;

import amal.souheil.netapp.Models.GithubUser;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Souheil Amal on 2019-02-19
 */
public interface GithubService {
    @GET("users/{username}/following")
    Call<List<GithubUser>> getFollowing(@Path("username") String username);
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
