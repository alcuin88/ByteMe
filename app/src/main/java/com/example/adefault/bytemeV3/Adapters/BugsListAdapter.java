package com.example.adefault.bytemeV3.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.adefault.bytemeV3.R;
import com.example.adefault.bytemeV3.Result;
import com.example.adefault.bytemeV3.databaseObjects.BugsListResponseV1;

import java.io.Serializable;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class BugsListAdapter extends
        RecyclerView.Adapter<BugsListAdapter.ViewHolder> {

    Context context;
    List<BugsListResponseV1> mBugsList;

    public BugsListAdapter(List<BugsListResponseV1> BugsList, Context context) {
        mBugsList = BugsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.bugs_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        BugsListResponseV1 bugs = mBugsList.get(position);

        // Set item views based on your views and data model

        TextView textView = viewHolder.InsectName;
        CircleImageView imageView = viewHolder.InsectImage;
        CardView cardView = viewHolder.cardView;

        textView.setText(bugs.getBugName());
        Glide.with(context)
                .load(bugs.getBugImage())
                .into(imageView);

        cardView.setOnClickListener(v -> {
            Intent scanIntent = new Intent(context, Result.class);
            scanIntent.putExtra("result", bugs.getBugName());
            context.startActivity(scanIntent);
        });
    }


    @Override
    public int getItemCount() {
        int arr = 0;

        try{
            if(this.mBugsList.size()==0){

                arr = 0;

            }
            else{

                arr=this.mBugsList.size();
            }



        }catch (Exception e){

            Toast.makeText(context, e + "", Toast.LENGTH_SHORT).show();

        }

        return arr;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView InsectName;
        public CircleImageView InsectImage;
        public CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);
            InsectName = itemView.findViewById(R.id.insect_name);
            InsectImage = itemView.findViewById(R.id.insect_image);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
