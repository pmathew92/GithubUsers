package com.prince.githubusers.kotlin.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.prince.githubusers.kotlin.R
import com.prince.githubusers.kotlin.model.User
import timber.log.Timber
import java.util.*

class UserAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val userList: MutableList<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder?
        if (viewType == 1) {
            val dataBindingUtil: com.prince.githubusers.kotlin.databinding.LayoutUserListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_user_list, parent, false)
            viewHolder = UserViewHolder(dataBindingUtil)
        } else {
            val bindingUtil: com.prince.githubusers.kotlin.databinding.LayoutProgressBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_progress, parent, false)
            viewHolder = ProgressViewHolder(bindingUtil)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == userList.size - 1) 0 else 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            holder.bind(userList.get(holder.adapterPosition))
        } else {

        }
    }

    fun addData(userList: List<User>) {
        this.userList.addAll(userList)
        Timber.d("Data came")
        Timber.d("Data size ${this.userList.size}")
        notifyDataSetChanged()
    }

    inner class UserViewHolder(@NonNull private val binding: com.prince.githubusers.kotlin.databinding.LayoutUserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: User) {
            binding.model = model
        }
    }

    inner class ProgressViewHolder(@NonNull private val binding: com.prince.githubusers.kotlin.databinding.LayoutProgressBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}