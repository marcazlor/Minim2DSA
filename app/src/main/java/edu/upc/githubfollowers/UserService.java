package edu.upc.githubfollowers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {
    @GET("{user}/followers")
    Call<List<Followers>> getFollowers(@Path("user") String user);
    @GET("{user}")
    Call<User>getUser(@Path("user") String user);

}
