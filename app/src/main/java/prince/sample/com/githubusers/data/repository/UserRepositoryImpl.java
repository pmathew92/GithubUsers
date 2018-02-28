package prince.sample.com.githubusers.data.repository;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import prince.sample.com.githubusers.AppController;
import prince.sample.com.githubusers.data.remote.ApiService;
import prince.sample.com.githubusers.model.User;
import prince.sample.com.githubusers.utils.Resource;
import prince.sample.com.githubusers.utils.StringConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepositoryImpl implements UserRepository{

    private final ApiService apiReqInterface;
    private static  UserRepositoryImpl instance;
    private final MutableLiveData<Resource<List<User>>> data=new MutableLiveData<>();

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
    public LiveData<Resource<List<User>>> getUsers() {
        apiReqInterface.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if(response.isSuccessful()){
                    data.setValue(Resource.success((List<User>) response.body()));
                }else{
                    switch (response.code()){
                        case 404:
                            data.setValue(Resource.error(StringConstants.DATA_NOT_FOUND,null));
                            break;
                        case 500:
                            data.setValue(Resource.error(StringConstants.SERVER_ERROR,null));
                            break;
                        default:
                            data.setValue(Resource.error(StringConstants.UNRECOGNIZED_ERROR,null));
                            break;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                data.setValue(Resource.error(t.getLocalizedMessage(),null));
            }
        });
        return data;
    }

    @Override
    public LiveData<Resource<List<User>>> getRemainingUser(int userId) {
        return null;
    }
}
