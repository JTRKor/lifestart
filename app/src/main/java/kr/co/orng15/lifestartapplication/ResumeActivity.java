package kr.co.orng15.lifestartapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.data.Mresult;
import kr.co.orng15.lifestartapplication.data.Personal_Detail;
import kr.co.orng15.lifestartapplication.data.Personal_Member;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumeActivity extends AppCompatActivity {

    @BindView(R.id.btn_resume_manager) Button btn_resume_manager;
    @BindView(R.id.ed_resume_name) EditText ed_resume_name;
    @BindView(R.id.txt_visible_R_name) TextView txt_visible_R_name;
    @BindView(R.id.ed_resume_y) TextView ed_resume_y;
    @BindView(R.id.ed_resume_m) TextView ed_resume_m;
    @BindView(R.id.ed_resume_d) TextView ed_resume_d;
    @BindView(R.id.txt_visible_R_ymd) TextView txt_visible_R_ymd;
    @BindView(R.id.ed_resume_gender) EditText ed_resume_gender;
    @BindView(R.id.txt_visible_R_gender) TextView txt_visible_R_gender;
    @BindView(R.id.txt_resume_address0) TextView txt_resume_address0;
    @BindView(R.id.btn_resume_find_address) Button btn_resume_find_address;
    @BindView(R.id.txt_resume_address1) TextView txt_resume_address1;
    @BindView(R.id.ed_resume_address2) EditText ed_resume_address2;
    @BindView(R.id.txt_visible_R_address) TextView txt_visible_R_address;
    @BindView(R.id.ed_resume_school) EditText ed_resume_school;
    @BindView(R.id.txt_visible_R_school) TextView txt_visible_R_school;
    @BindView(R.id.btn_resume_store) Button btn_resume_store;
    @BindView(R.id.btn_per_cal) Button btn_per_cal;
    @BindView(R.id.ed_resume_school_detail) EditText ed_resume_school_detail;
    LoginService loginService = LoginService.getInstance();
    Integer year;
    Integer month;
    Integer day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        ButterKnife.bind(this);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        refresh();

        Call<Personal_Member> observ = RetrofitService.getInstance().getRetrofitRequest().m_p_Info(loginService.getLoginMember().getId());
        observ.enqueue(new Callback<Personal_Member>() {
            @Override
            public void onResponse(Call<Personal_Member> call, Response<Personal_Member> response) {
                if (response.isSuccessful()) {
                    Personal_Member result = response.body();
                    ed_resume_name.setText(result.getMember_name().toString());
                }
            }

            @Override
            public void onFailure(Call<Personal_Member> call, Throwable t) {

            }
        });

        Call<Mresult> observ2 = RetrofitService.getInstance().getRetrofitRequest().m_personal_detail_count(loginService.getLoginMember().getId());
        observ2.enqueue(new Callback<Mresult>() {
            @Override
            public void onResponse(Call<Mresult> call, Response<Mresult> response) {
                if (response.isSuccessful()) {
                    Mresult result = response.body();
                    if (result.getResult() != 0) {
                        Call<Personal_Detail> observ3 = RetrofitService.getInstance().getRetrofitRequest().m_personal_detail_get(loginService.getLoginMember().getId());
                        observ3.enqueue(new Callback<Personal_Detail>() {
                            @Override
                            public void onResponse(Call<Personal_Detail> call, Response<Personal_Detail> response) {
                                if (response.isSuccessful()) {
                                    Personal_Detail result = response.body();
                                    ed_resume_y.setText(result.getDetail_birth_year());
                                    ed_resume_m.setText(result.getDetail_birth_month());
                                    ed_resume_d.setText(result.getDetail_birth_day());
                                    ed_resume_gender.setText(result.getDetail_gender());
                                    String tmpaddress[] = result.getDetail_address().split("\\$");
                                    txt_resume_address0.setText(tmpaddress[0]);
                                    txt_resume_address1.setText(tmpaddress[1]);
                                    ed_resume_address2.setText(tmpaddress[2]);
                                    ed_resume_school.setText(result.getDetail_learn());
                                    ed_resume_school_detail.setText(result.getDetail_learn_finish());
                                }
                            }

                            @Override
                            public void onFailure(Call<Personal_Detail> call, Throwable t) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<Mresult> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.btn_per_cal)
    void findCal(View view) {
        Calendar c = Calendar.getInstance();
        int cyear = year;
        int cmonth = month;
        int cday = day;

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int tmp_year, int tmp_month, int tmp_day) {
                year = tmp_year;
                month = tmp_month;
                day = tmp_day;

                refresh();
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(ResumeActivity.this,dateSetListener,cyear,cmonth,cday);
        dialog.show();
    }

    @OnClick(R.id.btn_resume_find_address)
    void findAddress(View view) {
        Intent intent = new Intent(ResumeActivity.this, AddressPopupActivity.class);
        startActivityForResult(intent, 0);
    }

    @OnClick(R.id.btn_resume_manager)
    void onResume(View view) {
        Intent intent = new Intent(ResumeActivity.this, ResumeManagerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_resume_store)
    void onResumeStore(View view) {
        String detail_name = ed_resume_name.getText().toString();
        String detail_birth_year = ed_resume_y.getText().toString();
        String detail_birth_month = ed_resume_m.getText().toString();
        String detail_birth_day = ed_resume_d.getText().toString();
        String detail_gender = ed_resume_gender.getText().toString();
        String address0 = txt_resume_address0.getText().toString();
        String address1 = txt_resume_address1.getText().toString();
        String address2 =  ed_resume_address2.getText().toString();
        String detail_address = address0 + "$" + address1 + "$" + address2;
        String detail_learn = ed_resume_school.getText().toString();
        String detail_learn_finish = ed_resume_school_detail.getText().toString();

        if(detail_name.equals("")) {
            txt_visible_R_name.setVisibility(View.VISIBLE);
        } else {
            txt_visible_R_name.setVisibility(View.GONE);
        }

        if(detail_birth_year.equals("") || detail_birth_month.equals("") || detail_birth_day.equals("")) {
            txt_visible_R_ymd.setVisibility(View.VISIBLE);
        } else {
            txt_visible_R_ymd.setVisibility(View.GONE);
        }

        if(detail_gender.equals("")) {
            txt_visible_R_gender.setVisibility(View.VISIBLE);
        } else {
            txt_visible_R_gender.setVisibility(View.GONE);
        }

        if(address0.equals("") || address1.equals("") || address2.equals("")) {
            txt_visible_R_address.setVisibility(View.VISIBLE);
        } else {
            txt_visible_R_address.setVisibility(View.GONE);
        }

        if(detail_learn.equals("") || detail_learn_finish.equals("")) {
            txt_visible_R_school.setVisibility(View.VISIBLE);
        } else {
            txt_visible_R_school.setVisibility(View.GONE);
        }

        if (txt_visible_R_name.getVisibility() == View.VISIBLE || txt_visible_R_ymd.getVisibility() == View.VISIBLE || txt_visible_R_gender.getVisibility() == View.VISIBLE
                || txt_visible_R_address.getVisibility() == View.VISIBLE || txt_visible_R_school.getVisibility() == View.VISIBLE) {
            return ;
        }
        else {
            Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().m_personal_detail_ok(loginService.getLoginMember().getId(),
                    detail_birth_year, detail_birth_month, detail_birth_day, detail_gender, detail_address, detail_learn, detail_learn_finish);
            observ.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(requestCode == 0) {
                Log.d("hjh", data.toString());
                String tmpAddress = data.getStringExtra("addr");
                if(tmpAddress != null) {
                    String addressArr[] = tmpAddress.split("\\(");
                    String tmpAddress0 = addressArr[0];
                    String tmpAddress1 = addressArr[1];
                    String address1Arr[] = tmpAddress1.split("\\) ");
                    String address0 = address1Arr[0];
                    String address1 = address1Arr[1];

                    txt_resume_address0.setText(address0);
                    txt_resume_address1.setText(address1);
                }
            }
        }
    }

    public void refresh() {
        ed_resume_y.setText(year.toString());
        ed_resume_m.setText("" + (month+1));
        ed_resume_d.setText(day.toString());
    }
}
