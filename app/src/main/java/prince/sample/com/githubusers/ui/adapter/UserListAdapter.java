package prince.sample.com.githubusers.ui.adapter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import prince.sample.com.githubusers.GlideApp;
import prince.sample.com.githubusers.R;
import prince.sample.com.githubusers.model.User;

/**
 * Adapter class for the recycler view
 */
public class UserListAdapter extends RecyclerView.Adapter implements Filterable {

    private Context context;
    private List<User> userList = new ArrayList<>();
    private List<User> userListFiltered = new ArrayList<>();
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean error,loading;
    private String errorMsg;
    private UserListAdapterListener adapterListener;

    /**
     * Constructor for the adapter.Pagination logic is handled here
     *
     * @param context
     * @param userList
     * @param recyclerView
     * @param adapterListener
     */
    public UserListAdapter(Context context, List<User> userList, RecyclerView recyclerView
            , UserListAdapterListener adapterListener) {
        this.context = context;
        this.userList = userList;
        this.adapterListener = adapterListener;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
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

        if (holder instanceof UserListViewHolder) {
            User user = userListFiltered.get(position);
            ((UserListViewHolder) holder).mUserName.setText(user.getLogin());
            ((UserListViewHolder) holder).mUrl.setText(user.getHtmlUrl());
            GlideApp.with(context)
                    .load(user.getAvatarUrl())
                    .placeholder(R.drawable.ic_github)
                    .centerCrop()
                    .into(((UserListViewHolder) holder).mUserAvtar);
        } else {
            if(error){
                ((ProgressViewHolder) holder).progressBar.setVisibility(View.GONE);
                ((ProgressViewHolder)holder).errorLayout.setVisibility(View.VISIBLE);
                ((ProgressViewHolder)holder).errorMsg.setText(errorMsg);
                ((ProgressViewHolder)holder).refresh.setOnClickListener(view->{
                    setProgressError(false,null);
                    adapterListener.onLoadMore();
                });
            }else {
                ((ProgressViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
                ((ProgressViewHolder)holder).errorLayout.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return userListFiltered != null ? userListFiltered.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position == (userList.size()-1) ? 0 : 1;
    }

    /**
     * Method to update the userList with the new list
     *
     * @param userList
     */
    public void addData(List<User> userList) {
        this.userList.addAll(userList);
        this.userListFiltered = this.userList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                loading = true;
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    userListFiltered = userList;
                    loading = false;
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
                userListFiltered = (List<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    /**
     * Method to set pagination loading status to false
     */
    public void setLoaded() {
        loading = false;
    }

    /**
     * Method to set error screen when pagination error occurs
     * @param errorStatus
     * @param message
     */
    public void setProgressError(boolean errorStatus,String message){
        this.error=errorStatus;
        this.errorMsg=message;
        notifyItemChanged(userList.size()-1);
    }

    class UserListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_name)
        TextView mUserName;
        @BindView(R.id.user_url)
        TextView mUrl;
        @BindView(R.id.avtar_image)
        ImageView mUserAvtar;

        UserListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.layout_progress_error)
        LinearLayout errorLayout;
        @BindView(R.id.txt_error_msg)
        TextView errorMsg;
        @BindView(R.id.btn_refresh)
        Button refresh;

        ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    /**
     * Callback to inform activity to fetch more data from API
     */
    public interface UserListAdapterListener {

        void onLoadMore();
    }
}
