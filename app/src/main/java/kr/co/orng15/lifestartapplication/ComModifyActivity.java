package kr.co.orng15.lifestartapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.data.Company_Member;
import kr.co.orng15.lifestartapplication.data.Member;
import kr.co.orng15.lifestartapplication.data.Member_Result;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComModifyActivity extends AppCompatActivity {

    LoginService loginService = LoginService.getInstance();
    @BindView(R.id.btn_main_info) Button btn_main_info;     //필수 정보
    @BindView(R.id.btn_both_info) Button btn_both_info;     //텍스트 바뀌는
    @BindView(R.id.txt_both_id) TextView txt_both_id;
    @BindView(R.id.ed_both_pw1) EditText ed_both_pw1;
    @BindView(R.id.ed_both_pw2) EditText ed_both_pw2;
    @BindView(R.id.txt_visible_modifypw) TextView txt_visible_modifypw;
    @BindView(R.id.btn_info_store) Button btn_info_store;

    String member_pw = "";
    String p_pw2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_modify);
        ButterKnife.bind(this);

        if(loginService.getLoginMember().getMember_kind() == 0) {
            btn_both_info.setText("이력서");
            txt_both_id.setText(loginService.getLoginId().toString());
        } else if(loginService.getLoginMember().getMember_kind() == 1) {
            btn_both_info.setText("기업정보");
            txt_both_id.setText(loginService.getLoginId().toString());
        }
    }

    //필수정보
    @OnClick(R.id.btn_main_info)
    void onMainInfo(View view) {
        if(loginService.getLoginMember().getMember_kind() == 0) {
            Intent intent = new Intent(ComModifyActivity.this, JoinActivity.class);
            startActivity(intent);
        } else if(loginService.getLoginMember().getMember_kind() == 1) {
            Intent intent = new Intent(ComModifyActivity.this, JoinCompanyActivity.class);
            startActivity(intent);
        }
    }

    //텍스트 변경
    @OnClick(R.id.btn_both_info)
    void onBothInfo(View view) {
        if(loginService.getLoginMember().getMember_kind() == 0) {
            if(loginService.getLoginMember().getMember_history() != 0) {
                Intent intent = new Intent(ComModifyActivity.this, ResumeActivity.class);
                startActivity(intent);
            } else {
                AlertDialog.Builder ad=new AlertDialog.Builder(ComModifyActivity.this);
                ad.setTitle("알림창").setMessage("이력서를 작성할 수 없습니다. 필수정보를 작성해주세요.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            }
        } else if(loginService.getLoginMember().getMember_kind() == 1) {
            if(loginService.getLoginMember().getMember_history() != 0) {
                Intent intent = new Intent(ComModifyActivity.this, CompanyDetailActivity.class);
                startActivity(intent);
            } else {
                AlertDialog.Builder ad=new AlertDialog.Builder(ComModifyActivity.this);
                ad.setTitle("알림창").setMessage("기업정보를 작성할 수 없습니다. 필수정보를 작성해주세요.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            }
        }
    }

    @OnClick(R.id.btn_info_store)
    void onInfoStore(View view) {
        member_pw = ed_both_pw1.getText().toString();
        p_pw2 = ed_both_pw2.getText().toString();

        if(member_pw.equals("") || p_pw2.equals("") || !member_pw.equals(p_pw2)) {
            txt_visible_modifypw.setVisibility(View.VISIBLE);
        } else {
            txt_visible_modifypw.setVisibility(View.GONE);
        }

        if(txt_visible_modifypw.getVisibility() == View.VISIBLE) {
            return;
        }

        Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().pw_Member(loginService.getLoginMember().getId(), member_pw);
        observ.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    AlertDialog.Builder ad=new AlertDialog.Builder(ComModifyActivity.this);
                    ad.setTitle("비밀번호 변경 완료").setMessage("비밀번호 변경이 완료되었습니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).create().show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });


        /*if(loginService.getLoginMember().getMember_kind() == 0) {
            Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().pw_Member(loginService.getLoginMember().getId(), member_pw);
            observ.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        AlertDialog.Builder ad=new AlertDialog.Builder(ComModifyActivity.this);
                        ad.setTitle("비밀번호 변경 완료").setMessage("비밀번호 변경이 완료되었습니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        } else if(loginService.getLoginMember().getMember_kind() == 1) {
            Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().pw_Member(loginService.getLoginMember().getId(), member_pw);
            observ.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        AlertDialog.Builder ad=new AlertDialog.Builder(ComModifyActivity.this);
                        ad.setTitle("비밀번호 변경 완료").setMessage("비밀번호 변경이 완료되었습니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }*/
    }
}
