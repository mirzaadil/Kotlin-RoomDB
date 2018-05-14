package com.mirzaadil.assignmentpayconiq.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirzaadil.assignmentpayconiq.R;
import com.mirzaadil.assignmentpayconiq.models.HomeAPIResponseModel;
import com.squareup.picasso.Picasso;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.RepositoriesViewHolder> {
    private Context context;
    private List<HomeAPIResponseModel> reposList;

    public MainRecyclerAdapter(Context context, List<HomeAPIResponseModel> reposList) {
        this.context = context;
        this.reposList = reposList;
    }


    @NonNull
    @Override
    public RepositoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_repositories, parent, false);
        return new RepositoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoriesViewHolder holder, int position) {

        holder.textViewReposName.setText(reposList.get(position).getFullName());
        holder.textViewReposDescription.setText(reposList.get(position).getDescription());
        holder.textViewId.setText(String.valueOf(reposList.get(position).getId()));
        Picasso.with(context).load(reposList.get(position).getOwner().getAvatarUrl()).fit()
                .into(holder.imageView_user);

    }

    @Override
    public int getItemCount() {
        return reposList.size();
    }

    class RepositoriesViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewReposName;
        private TextView textViewReposDescription;
        private TextView textViewId;
        private ImageView imageView_user;

        public RepositoriesViewHolder(View itemView) {
            super(itemView);

            textViewReposName = itemView.findViewById(R.id.tv_repo_name);
            textViewReposDescription = itemView.findViewById(R.id.tv_repo_description);
            textViewId = itemView.findViewById(R.id.main_product_id);
            imageView_user = itemView.findViewById(R.id.main_product_image);
        }
    }

}
