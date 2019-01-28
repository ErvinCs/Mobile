package com.example.csoka.lab_02_android.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.csoka.lab_02_android.EditPostActivity;
import com.example.csoka.lab_02_android.R;
import com.example.csoka.lab_02_android.model.Post;
import com.example.csoka.lab_02_android.repository.PostRepository;

import java.util.Collections;
import java.util.List;
//TRY TO TAKE THE PREVIOUS KOTLIN IMPL
//TODO - onBind
public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    public static final String intentMsg = "com.example.csoka.lab_02_android.ui.POST_TO_EDIT";

    final Integer UPDATE_POST_CODE = 2;

    class PostViewHolder extends RecyclerView.ViewHolder {
        //private final TextView userNameView;    //TODO - post per user
        private final TextView activityNameView;
        private final TextView memberLimitView;
        private final TextView dateView;
        private final TextView timeView;
        private final TextView locationView;
        private final TextView idTextView;

        private PostViewHolder(View itemView) {
            super(itemView);
            activityNameView = itemView.findViewById(R.id.activityName);
            memberLimitView = itemView.findViewById(R.id.members);
            dateView = itemView.findViewById(R.id.date);
            timeView = itemView.findViewById(R.id.time);
            locationView = itemView.findViewById(R.id.location);
            idTextView = itemView.findViewById(R.id.idTextView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Post> mPosts = Collections.emptyList();    //Copy of data

    private Context parentContext;

    public PostListAdapter(Context context) {
        this.parentContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, viewGroup, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int i) {
        //TODO - fix
        final Post current = mPosts.get(i);
        holder.activityNameView.setText(current.getActivityName());
        holder.memberLimitView.setText(current.getMemberLimit().toString());
        holder.dateView.setText(current.getDate());
        holder.timeView.setText(current.getTime());
        holder.locationView.setText(current.getLocation());

        holder.idTextView.setText("Id: " + current.getId().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(parentContext, EditPostActivity.class);
                intent.putExtra(intentMsg, current);
                ((Activity)parentContext).startActivityForResult(intent, UPDATE_POST_CODE);
            }
        });

    }

    public void setPosts(List<Post> posts) {
        mPosts = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }
}
