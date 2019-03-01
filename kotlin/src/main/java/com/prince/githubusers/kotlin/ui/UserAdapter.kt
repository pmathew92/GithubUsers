package com.prince.githubusers.kotlin.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.prince.githubusers.kotlin.R
import com.prince.githubusers.kotlin.model.User

class UserAdapter(private val context: Context) : PagedListAdapter<User, UserAdapter.UserViewHolder>(DIFF_UTIL) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {

        val dataBindingUtil: com.prince.githubusers.kotlin.databinding.LayoutUserListBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_user_list, parent, false)
        return UserViewHolder(dataBindingUtil)
    }


    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val user = getItem(position)
        user?.let {
            holder.bind(it)
        }
    }

    inner class UserViewHolder(@NonNull private val binding: com.prince.githubusers.kotlin.databinding.LayoutUserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: User) {
            binding.model = model
        }
    }


    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return newItem == oldItem
            }
        }
    }
}