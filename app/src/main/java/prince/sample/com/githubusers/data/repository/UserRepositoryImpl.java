package prince.sample.com.githubusers.data.repository;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import java.util.List;

import prince.sample.com.githubusers.AppController;
import prince.sample.com.githubusers.data.remote.ApiService;
import prince.sample.com.githubusers.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepositoryImpl implements UserRepository{

    private final ApiService apiReqInterface;
    private static  UserRepositoryImpl instance;
    private final MutableLiveData<List<User>> data=new MutableLiveData<>();

    private UserRepositoryImpl(final Context context){
        apiReqInterface= ((AppController)context).getRetrofitClient().create(ApiService.class);
    }

    public static UserRepositoryImpl getInstance(final Context context) {
        if(instance==null){
            synchronized (UserRepositoryImpl.class){
                if(instance==null){
                    instance = new UserRepositoryImpl(context);
                }
            }
        }

        return instance;
    }



    @Override
    public LiveData<List<User>> getUsers() {
        apiReqInterface.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });

        return data;
    }
}
