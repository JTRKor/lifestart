package kr.co.orng15.lifestartapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.orng15.lifestartapplication.R;
import kr.co.orng15.lifestartapplication.bus.BusProvider;
import kr.co.orng15.lifestartapplication.data.Personal_Detail_Resume;
import kr.co.orng15.lifestartapplication.event.TotalChange;

public class JobApplyAdapter extends BaseAdapter {
    ArrayList<Personal_Detail_Resume> items;
    private int mSelectedVariation = -1;

    public JobApplyAdapter(ArrayList<Personal_Detail_Resume> items) {
        this.items =items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_apply, parent, false);
            holder.radio_btn = convertView.findViewById(R.id.radio_btn);
            holder.ed_resume_name = convertView.findViewById(R.id.ed_resume_name);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Personal_Detail_Resume item = (Personal_Detail_Resume)getItem(position);

        holder.ed_resume_name.setText(item.getDetail_title());

        holder.radio_btn.setOnCheckedChangeListener(null);
        if (position==mSelectedVariation) {
            holder.radio_btn.setChecked(true);
        }
        else {
            holder.radio_btn.setChecked(false);
        }


        holder.radio_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    mSelectedVariation = position;
                    notifyDataSetChanged();
                    TotalChange event = new TotalChange();
                    event.setPosition(position);
                    BusProvider.getInstance().getBus().post(event);
                }
            }
        });

        return convertView;
    }

    public class Holder {
        RadioButton radio_btn;
        TextView ed_resume_name;
    }
}
