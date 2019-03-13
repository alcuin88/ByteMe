package com.example.adefault.bytemeV3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.adefault.bytemeV3.databaseObjects.TreatmentResponse;
import java.util.List;


public class SliderAdapter extends PagerAdapter {

    private Context context;
    private List<TreatmentResponse> list;

    SliderAdapter(Context context, List<TreatmentResponse> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (LinearLayout)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.slide_image);
        TextView slideHeading = view.findViewById(R.id.slide_heading);
        TextView slideDescription = view.findViewById(R.id.slide_desc);

        Glide.with(context)
                .load(list.get(position).getImage())
                .into(slideImageView);
        slideHeading.setText(list.get(position).getDescription());
        String steps = "";
        for(int i = 0; i < list.get(position).getSteps().size(); i++){
            steps += "- " + list.get(position).getSteps().get(i).getDescription() + "\n";
        }
        slideDescription.setText(steps);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
