package com.example.adefault.bytemeAPP;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class BugsListAdapter extends
        RecyclerView.Adapter<BugsListAdapter.ViewHolder> {

    Context context;
    private List<BugsListResponse> mBugsList;

    public BugsListAdapter(List<BugsListResponse> BugsList) {
        mBugsList = BugsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
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
        CircleImageView imageView = viewHolder.InsectImage;
        CardView cardView = viewHolder.cardView;

        textView.setText(bugs.getInsectName());
        Glide.with(context)
                .load(bugs.getInsectImage())
                .into(imageView);

        cardView.setOnClickListener(v -> Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show());
    }



    @Override
    public int getItemCount() {
        return mBugsList.size();
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
