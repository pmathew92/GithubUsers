package prince.sample.com.githubusers.data.remote;


import java.util.List;

import prince.sample.com.githubusers.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();

    @GET("users")
    Call<List<User>> getRemainingUsers(@Query("since") int userId);
}

