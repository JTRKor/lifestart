package kr.co.orng15.lifestartapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.data.Member;
import kr.co.orng15.lifestartapplication.data.Member_Result;
import kr.co.orng15.lifestartapplication.db.DBManager;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityBak extends AppCompatActivity {

    @BindView(R.id.ed_id) EditText ed_id;
    @BindView(R.id.ed_pw) EditText ed_pw;
    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.btn_join) Button btn_join;
    @BindView(R.id.check_login_info) CheckBox check_login_info;
    DBManager dbManager;
    Integer checkId = -1;
    LoginService loginService = LoginService.getInstance();

    String member_id = "";
    String member_pw = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        dbManager = new DBManager(LoginActivityBak.this, "login_member.db", null, 1);
        checkId = dbManager.checkLoginmember();

        if (checkId == 0) {
             ed_id.setText("");
             ed_pw.setText("");
        } else if(checkId == 1) {
            check_login_info.setChecked(true);
            member_id = dbManager.member_id();
            member_pw = dbManager.member_pw();
            ed_id.setText(member_id);
            ed_pw.setText(member_pw);
        }

        /*if(loginService.getLoginMember().getMember_kind() == 0) {
            member_kind = 0;    // 개인
        } else if(loginService.getLoginMember().getMember_kind() == 1) {
            member_kind = 1;    // 기업
        }*/
    }

    @OnClick(R.id.btn_login)
    void onLoginCall(View view){
        member_id = ed_id.getText().toString();
        member_pw = ed_pw.getText().toString();

        Call<Member_Result> observ = RetrofitService.getInstance().getRetrofitRequest().loginMember(member_id, member_pw);
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
                        if (check_login_info.isChecked()) {
                            dbManager.deleteLoginmember();
                            dbManager.insertLoginMember(1, member_id, member_pw);
                        } else {
                            dbManager.deleteLoginmember();
                            ed_id.setText("");
                            ed_pw.setText("");
                        }
                        Intent intent = new Intent(LoginActivityBak.this, IndexActivity.class);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder ad = new AlertDialog.Builder(LoginActivityBak.this);
                        ad.setTitle("로그인 실패").setMessage("로그인에 실패하였습니다.").setNeutralButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (!check_login_info.isChecked()) {
                                    dbManager.deleteLoginmember();
                                }
                            }
                        }).create().show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Member_Result> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.btn_join)
    void onJoinCall(View view) {
        Intent intent = new Intent(LoginActivityBak.this, JoinInputActivity.class);
        startActivity(intent);
    }
}

