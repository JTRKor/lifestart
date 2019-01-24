package kr.co.orng15.lifestartapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.orng15.lifestartapplication.R;
import kr.co.orng15.lifestartapplication.data.Member;
import kr.co.orng15.lifestartapplication.data.Mresult;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAdapter extends BaseAdapter {
    ArrayList<Member> items;

    public AdminAdapter(ArrayList<Member> items) {
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Holder holder = new Holder();
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminlist, parent, false);
            holder.txt_number = convertView.findViewById(R.id.txt_number);
            holder.txt_id = convertView.findViewById(R.id.txt_id);
            holder.ed_pw = convertView.findViewById(R.id.ed_pw);
            holder.spinner_kind = convertView.findViewById(R.id.spinner_kind);
            holder.spinner_status = convertView.findViewById(R.id.spinner_status);
            holder.btn_change = convertView.findViewById(R.id.btn_change);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final Member item = (Member) getItem(position);

        Log.d("hjh", "id="+item.getId());
        holder.txt_number.setText(item.getId().toString());
        holder.txt_id.setText(item.getMember_id().toString());
        holder.ed_pw.setText(item.getMember_pw().toString());

        ArrayAdapter kindAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.member_kind, android.R.layout.simple_spinner_item);
        kindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner_kind.setAdapter(kindAdapter);
        holder.spinner_kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                } else if(position == 1) {
                    ((TextView)parent.getChildAt(0)).setTextColor(Color.BLUE);
                }
                ((TextView)parent.getChildAt(0)).setTextSize(8);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter statusAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.member_status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner_status.setAdapter(statusAdapter);
        holder.spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("hjh", "position1="+position);
                if(position == 0) {
                    ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                } else if(position == 1) {
                    ((TextView)parent.getChildAt(0)).setTextColor(Color.RED);
                }
                ((TextView)parent.getChildAt(0)).setTextSize(8);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for(int i = 0; i< holder.spinner_kind.getAdapter().getCount(); i++) {
            Log.d("hjh", "i=" + i);
            Log.d("hjh", "get1=" + item.getMember_kind());
            Log.d("hjh" , item.toString());
            if(item.getMember_kind() == i) {
                holder.spinner_kind.setSelection(i);
            } /*else if(item.getMember_kind() == i) {
                holder.spinner_kind.setSelection(i);
            }*/
        }
        for(int i = 0; i< holder.spinner_status.getAdapter().getCount(); i++) {
            Log.d("hjh", "i=" + i);
            Log.d("hjh", "get2=" + item.getMember_status());
            Log.d("hjh" , item.toString());
            if(item.getMember_status() == i) {
                holder.spinner_status.setSelection(i);
            } /*else if(item.getMember_status() == 1) {
                holder.spinner_status.setSelection(i);
            }*/
        }

        final Holder finalHolder = holder;
        final Holder finalHolder1 = holder;
        final Holder finalHolder2 = holder;
        holder.btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw = finalHolder.ed_pw.getText().toString();
                String kind = "";
                String status = "";
                for(int i = 0; i< finalHolder1.spinner_kind.getAdapter().getCount(); i++) {
                    if(finalHolder1.spinner_kind.getAdapter().getItem(i).equals("0")) {
                        kind = finalHolder1.spinner_kind.getSelectedItem().toString();
                    } else if(finalHolder1.spinner_kind.getAdapter().getItem(i).equals("1")) {
                        kind = finalHolder1.spinner_kind.getSelectedItem().toString();
                    }
                }
                for(int i = 0; i< finalHolder2.spinner_status.getAdapter().getCount(); i++) {
                    if(finalHolder2.spinner_status.getAdapter().getItem(i).equals("0")) {
                        status = finalHolder2.spinner_status.getSelectedItem().toString();
                    } else if(finalHolder2.spinner_status.getAdapter().getItem(i).equals("1")) {
                        status = finalHolder2.spinner_status.getSelectedItem().toString();
                    }
                }

                Call<Mresult> observ = RetrofitService.getInstance().getRetrofitRequest().m_admin_change(item.getId().toString(), pw, kind, status);
                observ.enqueue(new Callback<Mresult>() {
                    @Override
                    public void onResponse(Call<Mresult> call, Response<Mresult> response) {
                        if (response.isSuccessful()) {
                            Mresult result = response.body();
                            if (result.getResult() == 0) {
                                AlertDialog.Builder ad = new AlertDialog.Builder(parent.getContext());
                                ad.setTitle("정보 변경 완료").setMessage("정보 변경이 완료되었습니다.").setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        ///////////////////////////////////////////////////////////////////////
                                        ((Activity)parent.getContext()).finish();
                                    }
                                }).create().show();
                            } else {
                                AlertDialog.Builder ad = new AlertDialog.Builder(parent.getContext());
                                ad.setTitle("알림창").setMessage("정보 변경이 실패하였습니다.").setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

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
        TextView txt_number;
        TextView txt_id;
        EditText ed_pw;
        Spinner spinner_kind;
        Spinner spinner_status;
        Button btn_change;
    }
}
