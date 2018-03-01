package prince.sample.com.githubusers;

import android.app.Application;

import prince.sample.com.githubusers.data.remote.RetrofitClient;
import prince.sample.com.githubusers.data.repository.UserRepositoryImpl;
import retrofit2.Retrofit;

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Retrofit getRetrofitClient(){
        return RetrofitClient.getInstance();
    }

    public UserRepositoryImpl getRepository(){
        return UserRepositoryImpl.getInstance(this);
    }

}


