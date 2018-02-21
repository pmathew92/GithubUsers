package prince.sample.com.githubusers.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import prince.sample.com.githubusers.GlideApp;
import prince.sample.com.githubusers.R;
import prince.sample.com.githubusers.model.User;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;

    public UserListAdapter(Context context,List<User> userList){
        this.context=context;
        this.userList=userList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_user_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user=userList.get(position);
        holder.mUserName.setText(user.getLogin());
        holder.mUrl.setText(user.getHtmlUrl());
        GlideApp.with(context)
                .load(user.getAvatarUrl())
                .placeholder(R.drawable.ic_github)
                .centerCrop()
                .into(holder.mUserAvtar);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    /**
     * Method to update the userList with the new list
     * @param userList
     */
    public void addData(List<User> userList) {
        this.userList.addAll(userList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.user_name) TextView mUserName;
        @BindView(R.id.user_url)   TextView mUrl;
        @BindView(R.id.avtar_image) ImageView mUserAvtar;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
