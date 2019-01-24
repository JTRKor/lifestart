package kr.co.orng15.lifestartapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.data.JoinResult;
import kr.co.orng15.lifestartapplication.data.Member;
import kr.co.orng15.lifestartapplication.data.Member_Result;
import kr.co.orng15.lifestartapplication.data.Personal_Member;
import kr.co.orng15.lifestartapplication.data.Personal_Member_Result;
import kr.co.orng15.lifestartapplication.db.DBManager;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    @BindView(R.id.ed_id) EditText ed_id;
    @BindView(R.id.ed_pw) EditText ed_pw;
    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.btn_join) Button btn_join;
    @BindView(R.id.check_login_info) CheckBox check_login_info;
    @BindView(R.id.check_auto_login) CheckBox check_auto_login;
    SharedPreferences loginInformation;
    SharedPreferences.Editor editor;
    LoginService loginService = LoginService.getInstance();

    String member_id = "";
    String member_pw = "";
    Integer beforetmp = 0;
    Integer tmp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginInformation = getSharedPreferences("login",0);
        editor = loginInformation.edit();

        member_id = loginInformation.getString("member_id", "");
        member_pw = loginInformation.getString("member_pw","");

        ed_id.setText(member_id);
        ed_pw.setText(member_pw);

        check_login_info.setChecked(loginInformation.getBoolean("saveidpw", false));
        check_auto_login.setChecked(loginInformation.getBoolean("autocheck", false));

        if (loginInformation.getBoolean("autocheck", false)) {
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
                            if (check_auto_login.isChecked()) {
                                editor.putString("member_id", member_id);
                                editor.putString("member_pw", member_pw);
                                editor.putBoolean("saveidpw", true);
                                editor.putBoolean("autocheck", true);
                                editor.commit();
                            } else if(check_login_info.isChecked()) {
                                editor.putString("member_id", member_id);
                                editor.putString("member_pw", member_pw);
                                editor.putBoolean("saveidpw", true);
                                editor.putBoolean("autocheck", false);
                                editor.commit();
                            } else {
                                ed_id.setText("");
                                ed_pw.setText("");
                                editor.clear();
                                editor.commit();
                            }
                            if (loginService.getLoginMember().getMember_kind() == 2) {
                                Intent intent1 = new Intent(LoginActivity.this, MargerActivity.class);
                                startActivity(intent1);
                            } else {
                                Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                                startActivityForResult(intent, 0);
                            }
                        } else {
                            AlertDialog.Builder ad = new AlertDialog.Builder(LoginActivity.this);
                            ad.setTitle("로그인 실패").setMessage("로그인에 실패하였습니다.").setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

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

        if (check_login_info.isChecked()) {
            beforetmp += 1;
        }

        if (check_auto_login.isChecked()) {
            beforetmp += 2;
        }
        check_login_info.setOnCheckedChangeListener(LoginActivity.this);
        check_auto_login.setOnCheckedChangeListener(LoginActivity.this);
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
                        if (check_auto_login.isChecked()) {
                            editor.putString("member_id", member_id);
                            editor.putString("member_pw", member_pw);
                            editor.putBoolean("saveidpw", true);
                            editor.putBoolean("autocheck", true);
                            editor.commit();
                        } else if (check_login_info.isChecked()) {
                            editor.putString("member_id", member_id);
                            editor.putString("member_pw", member_pw);
                            editor.putBoolean("saveidpw", true);
                            editor.putBoolean("autocheck", false);
                            editor.commit();
                        } else {
                            ed_id.setText("");
                            ed_pw.setText("");
                            editor.clear();
                            editor.commit();
                        }
                        if (loginService.getLoginMember().getMember_kind() == 2) {
                            Intent intent1 = new Intent(LoginActivity.this, MargerActivity.class);
                            startActivity(intent1);
                        } else {
                            Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                            startActivityForResult(intent, 0);
                        }
                    } else {
                        AlertDialog.Builder ad = new AlertDialog.Builder(LoginActivity.this);
                        ad.setTitle("로그인 실패").setMessage("로그인에 실패하였습니다.").setNeutralButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Member_Result> call, Throwable t) {
                AlertDialog.Builder ad = new AlertDialog.Builder(LoginActivity.this);
                ad.setTitle("알림창").setMessage("인터넷 연결을 확인해주세요.").setNeutralButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();

                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.btn_join)
    void onJoinCall(View view) {
        Intent intent = new Intent(LoginActivity.this, JoinInputActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        tmp = 0;
        if (check_login_info.isChecked() == true) {
            tmp += 1;
        }
        if (check_auto_login.isChecked() == true) {
            tmp += 2;
        }
        if (beforetmp == 3 && tmp == 2) {
            check_login_info.setChecked(false);
            check_auto_login.setChecked(false);
        }
        else if (beforetmp == 0 && tmp == 2) {
            check_login_info.setChecked(true);
            check_auto_login.setChecked(true);
        }

        beforetmp = 0;
        if (check_login_info.isChecked() == true) {
            beforetmp += 1;
        }
        if (check_auto_login.isChecked() == true) {
            beforetmp += 2;
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}

