package kr.co.orng15.lifestartapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.data.Company_Member;
import kr.co.orng15.lifestartapplication.data.Idcheck;
import kr.co.orng15.lifestartapplication.data.JoinResult;
import kr.co.orng15.lifestartapplication.data.Member;
import kr.co.orng15.lifestartapplication.data.Member_Result;
import kr.co.orng15.lifestartapplication.data.Mresult;
import kr.co.orng15.lifestartapplication.data.Personal_Member;
import kr.co.orng15.lifestartapplication.db.DBManager;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    LoginService loginService = LoginService.getInstance();
    @BindView(R.id.ed_personal_name) EditText ed_personal_name;
    @BindView(R.id.txt_visible_name) TextView txt_visible_name;
    @BindView(R.id.spinner_phone_first) Spinner spinner_phone_first;
    @BindView(R.id.ed_phone_second) EditText ed_phone_second;
    @BindView(R.id.ed_phone_third) EditText ed_phone_third;
    @BindView(R.id.txt_visible_phone) TextView txt_visible_phone;
    @BindView(R.id.ed_personal_email) TextView ed_personal_email;
    @BindView(R.id.txt_visible_email) TextView txt_visible_email;
    @BindView(R.id.btn_personal_store) Button btn_personal_store;
    @BindView(R.id.btn_email) Button btn_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);

        ArrayAdapter phoneAdapter = ArrayAdapter.createFromResource(this, R.array.phone_first, android.R.layout.simple_spinner_item);
        phoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_phone_first.setAdapter(phoneAdapter);

        if (loginService.getLoginMember().getMember_history() != 0) {
            Call<Personal_Member> observ = RetrofitService.getInstance().getRetrofitRequest().m_p_Info(loginService.getLoginMember().getId());
            observ.enqueue(new Callback<Personal_Member>() {
                @Override
                public void onResponse(Call<Personal_Member> call, Response<Personal_Member> response) {
                    if (response.isSuccessful()) {
                        Personal_Member result = response.body();

                        ed_personal_name.setText(result.getMember_name());
                        String phoneArr[] = result.getMember_phone().split("-");
                        for(int i=0; i<spinner_phone_first.getAdapter().getCount(); i++) {
                            if(spinner_phone_first.getAdapter().getItem(i).equals("010")) {
                                spinner_phone_first.setSelection(i);
                            } else if(spinner_phone_first.getAdapter().getItem(i).equals("011")) {
                                spinner_phone_first.setSelection(i);
                            } else if(spinner_phone_first.getAdapter().getItem(i).equals("017")) {
                                spinner_phone_first.setSelection(i);
                            } else if(spinner_phone_first.getAdapter().getItem(i).equals("018")) {
                                spinner_phone_first.setSelection(i);
                            }
                        }
                        ed_phone_second.setText(phoneArr[1]);
                        ed_phone_third.setText(phoneArr[2]);
                        ed_personal_email.setText(result.getMember_email());
                    }
                }

                @Override
                public void onFailure(Call<Personal_Member> call, Throwable t) {

                }
            });
        }
    }

    @OnClick(R.id.btn_email)
    void btnEmail(View view) {
        Intent intent = new Intent(JoinActivity.this, EmailPopupActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                String tmpEmail = data.getStringExtra("email");
                ed_personal_email.setText(tmpEmail);
            }
        }
    }

    @OnClick(R.id.btn_personal_store)
    void personalJoin(View view) {
        String member_name = ed_personal_name.getText().toString();
        String p_phone1 = spinner_phone_first.getSelectedItem().toString();
        String p_phone2 = ed_phone_second.getText().toString();
        String p_phone3 = ed_phone_third.getText().toString();
        String member_phone = p_phone1 + "-" + p_phone2 + "-" + p_phone3;
        String member_email = ed_personal_email.getText().toString();

        if(member_name.equals("")) {
            txt_visible_name.setVisibility(View.VISIBLE);
        } else {
            txt_visible_name.setVisibility(View.GONE);
        }

        if(p_phone2.equals("") || p_phone3.equals("")) {
            txt_visible_phone.setVisibility(View.VISIBLE);
        } else {
            txt_visible_phone.setVisibility(View.GONE);
        }

        if(member_email.equals("")) {
            txt_visible_email.setVisibility(View.VISIBLE);
        } else {
            txt_visible_email.setVisibility(View.GONE);
        }

        if (txt_visible_name.getVisibility() == View.VISIBLE || txt_visible_phone.getVisibility() == View.VISIBLE || txt_visible_email.getVisibility() == View.VISIBLE) {
            return ;
        }

        Call<Member_Result> observ = RetrofitService.getInstance().getRetrofitRequest().info_PMember(loginService.getLoginMember().getId(), member_name, member_phone, member_email);
        observ.enqueue(new Callback<Member_Result>() {
            @Override
            public void onResponse(Call<Member_Result> call, Response<Member_Result> response) {
                if (response.isSuccessful()) {
                    Member_Result result = response.body();
                    if (result.getResult() == 0) {
                        Member login_member = new Member(result.getId(), result.getMember_id(), result.getMember_pw(), result.getMember_kind(), result.getMember_history(), result.getMember_history_date(), result.getMember_status(), result.getMember_status_date());
                        loginService.setLoginId(result.getMember_id());
                        loginService.setLoginPw(result.getMember_pw());
                        loginService.setLoginMember(login_member);
                        AlertDialog.Builder ad=new AlertDialog.Builder(JoinActivity.this);
                        ad.setTitle("정보 수정 완료").setMessage("정보 수정이 완료되었습니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();
                    }
                    else {
                        AlertDialog.Builder ad=new AlertDialog.Builder(JoinActivity.this);
                        ad.setTitle("정보 수정 실패").setMessage("정보 수정이 실패하였습니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Member_Result> call, Throwable t) {

            }
        });
    }
}
