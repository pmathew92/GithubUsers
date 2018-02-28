package prince.sample.com.githubusers.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import prince.sample.com.githubusers.AppController;
import prince.sample.com.githubusers.data.repository.UserRepository;
import prince.sample.com.githubusers.model.User;
import prince.sample.com.githubusers.utils.Resource;


public class MainActivityViewModel extends AndroidViewModel{

    private LiveData<Resource<List<User>>> userList;
    private MutableLiveData<Boolean> setProgress;
    private UserRepository userRepository;
    MainActivityViewModel(Application application){
        super(application);
        userRepository=((AppController)application).getRepository();
        setProgress=new MutableLiveData<>();
        fetchUsers();
    }

    public LiveData<Resource<List<User>>> getUserList(){
        return userList;
    }

    public LiveData<Boolean> getProgressStatus(){
        return setProgress;
    }

    public void setProgressStatus(boolean status){
        setProgress.setValue(status);
    }

    public void fetchUsers(){
        setProgressStatus(true);
        userList=userRepository.getUsers();
    }

}
