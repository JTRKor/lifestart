package kr.co.orng15.lifestartapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.orng15.lifestartapplication.R;
import kr.co.orng15.lifestartapplication.data.Apply;

public class ComListAdapter extends BaseAdapter {
    ArrayList<Apply> items;

    public ComListAdapter(ArrayList<Apply> items) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comlist, parent, false);
            holder.txt_com_list_title = convertView.findViewById(R.id.txt_com_list_title);
            holder.txt_com_list_name = convertView.findViewById(R.id.txt_com_list_name);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Apply item = (Apply) getItem(position);
        holder.txt_com_list_title.setText(item.getBoard_title());
        holder.txt_com_list_name.setText(item.getApply_name());

        return convertView;
    }

    public class Holder {
        TextView txt_com_list_title;
        TextView txt_com_list_name;
    }
}
