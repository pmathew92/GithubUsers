package prince.sample.com.githubusers.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import prince.sample.com.githubusers.AppController;
import prince.sample.com.githubusers.data.repository.UserRepository;
import prince.sample.com.githubusers.model.User;


public class MainActivityViewModel extends AndroidViewModel{

    private LiveData<List<User>> userList;
    private UserRepository userRepository;
    MainActivityViewModel(Application application){
        super(application);
        userRepository=((AppController)application).getRepository();
        userList=userRepository.getUsers();
    }

    public LiveData<List<User>> getUserList(){
        return userList;
    }
}
