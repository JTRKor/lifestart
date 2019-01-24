package kr.co.orng15.lifestartapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.orng15.lifestartapplication.R;
import kr.co.orng15.lifestartapplication.data.Board;
import kr.co.orng15.lifestartapplication.data.Mresult;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBoardAdapter extends BaseAdapter {
    ArrayList<Board> items;

    public AdminBoardAdapter(ArrayList<Board> items) {
        this.items = items;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        Holder holder = new Holder();
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminboard, parent, false);
            holder.txt_board_num = convertView.findViewById(R.id.txt_board_num);
            holder.txt_title = convertView.findViewById(R.id.txt_title);
            holder.txt_com = convertView.findViewById(R.id.txt_com);
            holder.txt_end_ymd = convertView.findViewById(R.id.txt_end_ymd);
            holder.btn_delete = convertView.findViewById(R.id.btn_delete);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final Board item = (Board) getItem(position);

        holder.txt_board_num.setText(item.getId().toString());
        holder.txt_title.setText(item.getBoard_title());
        holder.txt_com.setText(item.getBoard_name());
        String ymd = item.getBoard_end_year() + "-" + item.getBoard_end_month() + "-" + item.getBoard_end_day();
        holder.txt_end_ymd.setText(ymd);

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Mresult> observ = RetrofitService.getInstance().getRetrofitRequest().m_admin_delete(item.getId());
                observ.enqueue(new Callback<Mresult>() {
                    @Override
                    public void onResponse(Call<Mresult> call, Response<Mresult> response) {
                        Mresult result = response.body();
                        if(result.getResult() == 0) {
                            if (response.isSuccessful()) {
                                AlertDialog.Builder ad = new AlertDialog.Builder(parent.getContext());
                                ad.setTitle("경고창").setMessage("삭제하시겠습니까?").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        ((Activity)parent.getContext()).finish();
                                    }
                                }).create().show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Mresult> call, Throwable t) {

                    }
                });
            }
        });

        return convertView;
    }

    public class Holder {
        TextView txt_board_num;
        TextView txt_title;
        TextView txt_com;
        TextView txt_end_ymd;
        Button btn_delete;
    }
}
