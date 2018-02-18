package prince.sample.com.githubusers.data.remote;


import java.util.List;

import prince.sample.com.githubusers.model.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();
}

