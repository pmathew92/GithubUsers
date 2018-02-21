package prince.sample.com.githubusers.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import prince.sample.com.githubusers.model.User;


public class MainActivityViewModel extends ViewModel{

    private LiveData<List<User>> userList;
    MainActivityViewModel(){

    }

    public LiveData<List<User>> getUserList(){
        return userList;
    }
}
