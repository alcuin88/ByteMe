package com.example.adefault.bytemeV3.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adefault.bytemeV3.BiteScanInput;
import com.example.adefault.bytemeV3.R;
import com.example.adefault.bytemeV3.databaseObjects.SignAndSymptomsResponse;

import java.util.ArrayList;
import java.util.List;

public class SignsAndSymptomsSpinnerAdapter extends ArrayAdapter<SignAndSymptomsResponse> {
    private Context mContext;
    private ArrayList<SignAndSymptomsResponse> listState;
    private SignsAndSymptomsSpinnerAdapter myAdapter;
    private boolean isFromView = false;
    private List<Integer> list;
    BiteScanInput biteScanInput;

    public SignsAndSymptomsSpinnerAdapter(Context context, int resource,
                                          List<SignAndSymptomsResponse> objects, BiteScanInput biteScanInput) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<SignAndSymptomsResponse>) objects;
        this.myAdapter = this;
        list = new ArrayList<>();
        this.biteScanInput = biteScanInput;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.signs_symptoms_item, null);
            holder = new ViewHolder();
            holder.mTextView = convertView
                    .findViewById(R.id.text);
            holder.mCheckBox = convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(listState.get(position).getName());

        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }

        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int getPosition = (Integer) buttonView.getTag();
            if (!isFromView) {
                listState.get(position).setSelected(isChecked);
                if(listState.get(position).isSelected()){
                    list.add(Integer.valueOf(listState.get(position).getID()));
                }else{
                    list.remove(new Integer(Integer.valueOf(listState.get(position).getID())));
                }
                if(listState.get(0).getName().equalsIgnoreCase("SELECT SIGNS"))
                    biteScanInput.setSignsList(list);
                else
                    biteScanInput.setSymptomsList(list);
            }
        });
        return convertView;
    }

    public List<Integer> getList(){
        return list;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }
}
