package prince.sample.com.githubusers.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import prince.sample.com.githubusers.GlideApp;
import prince.sample.com.githubusers.R;
import prince.sample.com.githubusers.model.User;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder>
        implements Filterable {

    private Context context;
    private List<User> userList=new ArrayList<>();
    private List<User> userListFiltered=new ArrayList<>();

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
        User user=userListFiltered.get(position);
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
        return userListFiltered.size();
    }

    /**
     * Method to update the userList with the new list
     * @param userList
     */
    public void addData(List<User> userList) {
        this.userList.addAll(userList);
        this.userListFiltered=this.userList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    userListFiltered = userList;
                } else {
                    List<User> filteredList = new ArrayList<>();
                    for (User row : userList) {
                        if (row.getLogin().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    userListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = userListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                userListFiltered= (List<User>) results.values;
                notifyDataSetChanged();
            }
        };
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
