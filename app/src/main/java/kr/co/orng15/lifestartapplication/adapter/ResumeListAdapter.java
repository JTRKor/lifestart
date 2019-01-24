package kr.co.orng15.lifestartapplication.adapter;

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
import kr.co.orng15.lifestartapplication.data.Personal_Detail_Resume;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumeListAdapter extends BaseAdapter {
    ArrayList<Personal_Detail_Resume> items;

    public ResumeListAdapter(ArrayList<Personal_Detail_Resume> items) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_resume_ensure, parent, false);
            holder.txt_resume_title = convertView.findViewById(R.id.txt_resume_title);
            holder.btn_delete_resume = convertView.findViewById(R.id.btn_delete_resume);

            convertView.setTag(holder);
        } else {
            holder = (Holder)convertView.getTag();
        }

        final Personal_Detail_Resume item = (Personal_Detail_Resume)getItem(position);
        holder.txt_resume_title.setText(item.getDetail_title());

        holder.btn_delete_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                alertDialog.setTitle("경고");
                alertDialog.setMessage("정말 삭제하시겠습니까?");
                alertDialog.setPositiveButton("네", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().m_personal_detail_resume_del(item.getId());
                        observ.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    items.remove(position);
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                });
                alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });

        return convertView;
    }

    public class Holder {
        TextView txt_resume_title;
        Button btn_delete_resume;
    }
}
