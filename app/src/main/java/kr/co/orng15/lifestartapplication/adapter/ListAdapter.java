package kr.co.orng15.lifestartapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.orng15.lifestartapplication.R;
import kr.co.orng15.lifestartapplication.data.Board;

public class ListAdapter extends BaseAdapter {
    ArrayList<Board> items;

    public ListAdapter(ArrayList<Board> items) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_post, parent, false);
            holder.txt_board_title = convertView.findViewById(R.id.txt_board_title);
            holder.txt_job_app = convertView.findViewById(R.id.txt_job_app);
            holder.txt_kind = convertView.findViewById(R.id.txt_kind);
            holder.txt_new = convertView.findViewById(R.id.txt_new);
            holder.txt_year = convertView.findViewById(R.id.txt_year);
            holder.txt_month = convertView.findViewById(R.id.txt_month);
            holder.txt_day = convertView.findViewById(R.id.txt_day);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Board item = (Board)getItem(position);
        holder.txt_board_title.setText(item.getBoard_title());
        holder.txt_job_app.setText(item.getBoard_name());
        holder.txt_kind.setText(item.getBoard_jobkind());
        holder.txt_new.setText(item.getBoard_new());
        holder.txt_year.setText(item.getBoard_end_year());
        holder.txt_month.setText(item.getBoard_end_month());
        holder.txt_day.setText(item.getBoard_end_day());



        return convertView;
    }

    public class Holder {
        TextView txt_board_title;
        TextView txt_job_app;
        TextView txt_kind;
        TextView txt_new;
        TextView txt_year;
        TextView txt_month;
        TextView txt_day;
    }
}
