package prince.sample.com.githubusers.data.repository;


import android.arch.lifecycle.LiveData;

import java.util.List;

import prince.sample.com.githubusers.model.User;

public interface UserRepository {

    LiveData<List<User>> getUsers();
}
