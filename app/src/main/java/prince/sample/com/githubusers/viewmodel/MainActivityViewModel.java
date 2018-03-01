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

/**
 * ViewModel class for the MainActivity class
 */
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

    /**
     * Method to return initial list of users
     * @return
     */
    public LiveData<Resource<List<User>>> getUserList(){
        return userList;
    }

    /**
     * Method to return status of loading progress
     * @return
     */
    public LiveData<Boolean> getProgressStatus(){
        return setProgress;
    }

    /**
     * Method to set whether loading completed or not
     * @param status
     */
    public void setProgressStatus(boolean status){
        setProgress.setValue(status);
    }

    /**
     * Method to fetch initial list of users
     */
    public void fetchUsers(){
        setProgressStatus(true);
        userList=userRepository.getUsers();
    }

    /**
     * Method to fetch user data for pagination since the last id available
     * @param userId
     * @return
     */
    public LiveData<Resource<List<User>>> getRemainingUser(int userId){
        return userRepository.getRemainingUser(userId);
    }

}
