package prince.sample.com.githubusers.ui.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import prince.sample.com.githubusers.R;
import prince.sample.com.githubusers.model.User;
import prince.sample.com.githubusers.ui.adapter.UserListAdapter;
import prince.sample.com.githubusers.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_loading)
    RelativeLayout mLoading;

    private MainActivityViewModel mViewModel;
    private List<User> mUserList = new ArrayList<>();
    private UserListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null)
            toolbar.setTitle(getApplicationInfo().loadLabel(getPackageManager()));

        mAdapter = new UserListAdapter(this, mUserList);

        mSearchView.setLayoutParams(new Toolbar.LayoutParams(Gravity.END));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mViewModel.getUserList().observe(this, userList -> {
            mViewModel.setProgressStatus(false);
            mAdapter.addData(userList);
        });


        mViewModel.getProgressStatus().observe(this, isLoading -> {
            if (isLoading!=null && isLoading)
                mLoading.setVisibility(View.VISIBLE);
            else
                mLoading.setVisibility(View.GONE);
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

}
