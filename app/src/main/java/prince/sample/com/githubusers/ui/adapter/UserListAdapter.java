package prince.sample.com.githubusers.ui.adapter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import prince.sample.com.githubusers.GlideApp;
import prince.sample.com.githubusers.R;
import prince.sample.com.githubusers.model.User;


public class UserListAdapter extends RecyclerView.Adapter implements Filterable {

    private Context context;
    private List<User> userList=new ArrayList<>();
    private List<User> userListFiltered=new ArrayList<>();
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private UserListAdapterListener adapterListener;

    public UserListAdapter(Context context,List<User> userList, RecyclerView recyclerView
            ,UserListAdapterListener adapterListener ){
        this.context=context;
        this.userList=userList;
        this.adapterListener=adapterListener;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount=linearLayoutManager.getItemCount();
                lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
                if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (adapterListener != null) {
                        adapterListener.onLoadMore();
                    }
                    loading = true;
                }
            }
        });

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_list, parent, false);
            viewHolder = new UserListViewHolder(view);
        } else {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_progress, parent, false);
            viewHolder = new ProgressViewHolder(layoutView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof UserListViewHolder){
            User user=userListFiltered.get(position);
            ((UserListViewHolder)holder).mUserName.setText(user.getLogin());
            ((UserListViewHolder)holder).mUrl.setText(user.getHtmlUrl());
            GlideApp.with(context)
                    .load(user.getAvatarUrl())
                    .placeholder(R.drawable.ic_github)
                    .centerCrop()
                    .into(((UserListViewHolder)holder).mUserAvtar);
        }else{
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return userListFiltered.size();
    }

    @Override
    public int getItemViewType(int position) {
        return userList.get(position) != null ? 1 : 0;
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

    public void setLoaded() {
        loading = false;
    }

    class UserListViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.user_name) TextView mUserName;
        @BindView(R.id.user_url)   TextView mUrl;
        @BindView(R.id.avtar_image) ImageView mUserAvtar;
        UserListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface UserListAdapterListener{

        void onLoadMore();
    }
}
