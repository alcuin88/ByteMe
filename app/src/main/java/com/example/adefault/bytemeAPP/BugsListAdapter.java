package com.example.adefault.bytemeAPP;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BugsListAdapter extends
        RecyclerView.Adapter<BugsListAdapter.ViewHolder> {

    private List<BugsListResponse> mBugsList;

    public BugsListAdapter(List<BugsListResponse> BugsList) {
        mBugsList = BugsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.bugs_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        BugsListResponse bugs = mBugsList.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.InsectName;
        ImageView imageView = viewHolder.InsectImage;
        textView.setText(bugs.getInsectName());
    }

    @Override
    public int getItemCount() {
        return mBugsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView InsectName;
        public ImageView InsectImage;

        public ViewHolder(View itemView){
            super(itemView);

            InsectName = itemView.findViewById(R.id.insect_name);
            InsectImage = itemView.findViewById(R.id.insect_image);
        }
    }
}
