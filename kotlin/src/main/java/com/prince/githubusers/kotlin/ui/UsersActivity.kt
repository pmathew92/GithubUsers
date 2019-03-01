package com.prince.githubusers.kotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.prince.githubusers.kotlin.R
import com.prince.githubusers.kotlin.model.User
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.content_users.*
import javax.inject.Inject

class UsersActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: UserViewModelFactory
    private val viewModel by lazy {
        ViewModelProviders.of(this, factory).get(UsersViewModel::class.java)
    }

    private lateinit var mBinding: com.prince.githubusers.kotlin.databinding.ActivityUsersBinding

    @Inject
    lateinit var mAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_users)
        setSupportActionBar(toolbar)

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.itemAnimator = DefaultItemAnimator()

        recycler_view.adapter = mAdapter

        mBinding.viewModel = viewModel

        observeData()
    }


    fun observeData() {
        viewModel.getUsers().observe(this, Observer<List<User>> {
            mAdapter.submitList(it)
//            mAdapter.addData(it)
        })
    }
}
