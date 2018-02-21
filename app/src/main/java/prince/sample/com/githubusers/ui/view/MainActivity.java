package prince.sample.com.githubusers.ui.view;

import android.arch.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import prince.sample.com.githubusers.AppController;
import prince.sample.com.githubusers.R;
import prince.sample.com.githubusers.data.remote.ApiService;
import prince.sample.com.githubusers.model.User;
import prince.sample.com.githubusers.ui.adapter.UserListAdapter;
import prince.sample.com.githubusers.viewmodel.MainActivityViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.search_view) SearchView mSearchView;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private MainActivityViewModel mViewModel;
    private List<User> mUserList=new ArrayList<>();
    private UserListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mViewModel=ViewModelProviders.of(this).get(MainActivityViewModel.class);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        if(toolbar!=null)
            toolbar.setTitle(getApplicationInfo().loadLabel(getPackageManager()));

        mAdapter=new UserListAdapter(this,mUserList);

        mSearchView.setLayoutParams(new Toolbar.LayoutParams(Gravity.END));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mViewModel.getUserList().observe(this,userList -> {
            mAdapter.addData(userList);
        });

        ApiService apiReqInterface = ((AppController)getApplication()).getRetrofitClient().create(ApiService.class);

        apiReqInterface.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    mUserList=response.body();
                    mAdapter.addData(mUserList);
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });


    }
}
