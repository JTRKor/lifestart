package kr.co.orng15.lifestartapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.data.Company_Member;
import kr.co.orng15.lifestartapplication.data.Company_Member_Result;
import kr.co.orng15.lifestartapplication.data.Idcheck;
import kr.co.orng15.lifestartapplication.data.JoinResult;
import kr.co.orng15.lifestartapplication.data.Member;
import kr.co.orng15.lifestartapplication.data.Member_Result;
import kr.co.orng15.lifestartapplication.data.Mresult;
import kr.co.orng15.lifestartapplication.data.Personal_Member;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinCompanyActivity extends AppCompatActivity {
    LoginService loginService = LoginService.getInstance();
    @BindView(R.id.ed_company_number) EditText ed_company_number;
    @BindView(R.id.txt_visible_number) TextView txt_visible_number;
    @BindView(R.id.ed_company_name) EditText ed_company_name;
    @BindView(R.id.txt_visible_name) TextView txt_visible_name;
    @BindView(R.id.ed_company_ceo) EditText ed_company_ceo;
    @BindView(R.id.txt_visible_ceo) TextView txt_visible_ceo;
    @BindView(R.id.ed_company_phone1) EditText ed_company_phone1;
    @BindView(R.id.ed_company_phone2) EditText ed_company_phone2;
    @BindView(R.id.ed_company_phone3) EditText ed_company_phone3;
    @BindView(R.id.txt_visible_phone) TextView txt_visible_phone;
    @BindView(R.id.ed_company_hp) EditText ed_company_hp;
    @BindView(R.id.txt_visible_hp) TextView txt_visible_hp;
    @BindView(R.id.btn_company_store) Button btn_company_store;

    //변경시 싱글턴 히스토리 값 변경해야함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_company);
        ButterKnife.bind(this);

        if(loginService.getLoginMember().getMember_history() != 0) {
            Call<Company_Member> observ = RetrofitService.getInstance().getRetrofitRequest().m_c_Info(loginService.getLoginMember().getId());
            observ.enqueue(new Callback<Company_Member>() {
                @Override
                public void onResponse(Call<Company_Member> call, Response<Company_Member> response) {
                    if (response.isSuccessful()) {
                        Company_Member result = response.body();

                        ed_company_number.setText(result.getCompany_number());
                        ed_company_name.setText(result.getCompany_name());
                        ed_company_ceo.setText(result.getCompany_ceoname());
                        String phoneArr[] = result.getCompany_phone().split("-");
                        ed_company_phone1.setText(phoneArr[0]);
                        ed_company_phone2.setText(phoneArr[1]);
                        ed_company_phone3.setText(phoneArr[2]);
                        ed_company_hp.setText(result.getCompany_homepage());
                    }
                }

                @Override
                public void onFailure(Call<Company_Member> call, Throwable t) {

                }
            });
        }
    }

    @OnClick(R.id.btn_company_store)
    void companyJoin(View view) {
        String company_name = ed_company_name.getText().toString();
        String company_number = ed_company_number.getText().toString();
        String company_ceoname = ed_company_ceo.getText().toString();
        String p_phone1 = ed_company_phone1.getText().toString();
        String p_phone2 = ed_company_phone2.getText().toString();
        String p_phone3 = ed_company_phone3.getText().toString();
        String company_phone = p_phone1 + "-" + p_phone2 + "-" + p_phone3;
        String company_homepage = ed_company_hp.getText().toString();

        if (company_number.equals("")) {
            txt_visible_number.setVisibility(View.VISIBLE);
        } else {
            txt_visible_number.setVisibility(View.GONE);
        }

        if (company_name.equals("")) {
            txt_visible_name.setVisibility(View.VISIBLE);
        } else {
            txt_visible_name.setVisibility(View.GONE);
        }

        if (company_ceoname.equals("")) {
            txt_visible_ceo.setVisibility(View.VISIBLE);
        } else {
            txt_visible_ceo.setVisibility(View.GONE);
        }

        if (p_phone2.equals("") || p_phone3.equals("")) {
            txt_visible_phone.setVisibility(View.VISIBLE);
        } else {
            txt_visible_phone.setVisibility(View.GONE);
        }

        if (company_homepage.equals("")) {
            txt_visible_hp.setVisibility(View.VISIBLE);
        } else {
            txt_visible_hp.setVisibility(View.GONE);
        }

        if (txt_visible_number.getVisibility() == View.VISIBLE || txt_visible_name.getVisibility() == View.VISIBLE ||
                txt_visible_ceo.getVisibility() == View.VISIBLE || txt_visible_phone.getVisibility() == View.VISIBLE ||
                txt_visible_hp.getVisibility() == View.VISIBLE) {
            return;
        }

        Log.d("hjh", "" + loginService.getLoginMember().getId());

        Call<Member_Result> observ = RetrofitService.getInstance().getRetrofitRequest().info_CMember(loginService.getLoginMember().getId(), company_number, company_name, company_ceoname, company_phone, company_homepage);
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
                        AlertDialog.Builder ad = new AlertDialog.Builder(JoinCompanyActivity.this);
                        ad.setTitle("정보 수정 완료").setMessage("정보 수정이 완료되었습니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();
                    }
                    else {
                        AlertDialog.Builder ad = new AlertDialog.Builder(JoinCompanyActivity.this);
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



