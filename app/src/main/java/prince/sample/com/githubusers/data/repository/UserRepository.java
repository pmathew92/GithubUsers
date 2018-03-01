package prince.sample.com.githubusers.data.repository;


import android.arch.lifecycle.LiveData;

import java.util.List;

import prince.sample.com.githubusers.model.User;
import prince.sample.com.githubusers.utils.Resource;

/**
 * Repository interface to fetch data from ApiService class.
 */
public interface UserRepository {

    LiveData<Resource<List<User>>> getUsers();

    LiveData<Resource<List<User>>> getRemainingUser(int userId);
}
