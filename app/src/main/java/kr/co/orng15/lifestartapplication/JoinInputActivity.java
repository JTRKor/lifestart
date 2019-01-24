package kr.co.orng15.lifestartapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.data.Idcheck;
import kr.co.orng15.lifestartapplication.data.JoinResult;
import kr.co.orng15.lifestartapplication.data.Member;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinInputActivity extends AppCompatActivity {

    @BindView(R.id.ed_join_id) EditText ed_join_id;
    @BindView(R.id.btn_id_check) Button btn_id_check;
    @BindView(R.id.txt_visible_id) TextView txt_visible_id;
    @BindView(R.id.ed_join_pw1) EditText ed_join_pw1;
    @BindView(R.id.ed_join_pw2) EditText ed_join_pw2;
    @BindView(R.id.txt_visible_pw) TextView txt_visible_pw;
    @BindView(R.id.rg_test) RadioGroup rg_test;
    @BindView(R.id.btn_personal) RadioButton btn_personal;
    @BindView(R.id.btn_company) RadioButton btn_company;
    @BindView(R.id.txt_visible_both) TextView txt_visible_both;
    @BindView(R.id.btn_input_join) Button btn_input_join;
    String tmpId = "";
    Integer member_kind = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_input);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_input_join)
    void inputJoin(View view) {
        final String member_id = ed_join_id.getText().toString();
        final String member_pw = ed_join_pw1.getText().toString();
        String i_pw2 = ed_join_pw2.getText().toString();

        if(tmpId.equals("") || !member_id.equals(tmpId)) {
            txt_visible_id.setVisibility(View.VISIBLE);
        } else {
            txt_visible_id.setVisibility(View.GONE);
        }

        if(!member_pw.equals(i_pw2) || member_pw.equals("") || i_pw2.equals("")) {
            txt_visible_pw.setVisibility(View.VISIBLE);
        } else {
            txt_visible_pw.setVisibility(View.GONE);
        }

        if (btn_company.isChecked() || btn_personal.isChecked()) {
            RadioButton rd = findViewById(rg_test.getCheckedRadioButtonId());
            String result = rd.getText().toString();
            if (result.equals("개인용")) {
                member_kind = 0;
            } else if (result.equals("기업용")) {
                member_kind = 1;
            }
            txt_visible_both.setVisibility(View.GONE);
        } else if(!btn_company.isChecked() || !btn_personal.isChecked()){
            txt_visible_both.setVisibility(View.VISIBLE);
        }

        if (txt_visible_id.getVisibility() == View.VISIBLE || txt_visible_pw.getVisibility() == View.VISIBLE || txt_visible_both.getVisibility() == View.VISIBLE) {
            return ;
        }

        Call<JoinResult> observ = RetrofitService.getInstance().getRetrofitRequest().insertMember(member_id, member_pw, member_kind);
        observ.enqueue(new Callback<JoinResult>() {
            @Override
            public void onResponse(Call<JoinResult> call, Response<JoinResult> response) {
                if (response.isSuccessful()) {
                    JoinResult result = response.body();
                    if (result.getResult() == 0) {
                        AlertDialog.Builder ad = new AlertDialog.Builder(JoinInputActivity.this);
                        ad.setTitle("가입완료").setMessage("회원 가입이 완료되었습니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();
                    }
                    else {
                        AlertDialog.Builder ad = new AlertDialog.Builder(JoinInputActivity.this);
                        ad.setTitle("가입실패").setMessage("회원 가입이 실패하였습니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JoinResult> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.btn_id_check)
    void idCheck(View view) {
        final String member_id = ed_join_id.getText().toString();
        Log.d("hjh", "id" + member_id);

        if(!member_id.equals("")) {
            Call<Idcheck> observ = RetrofitService.getInstance().getRetrofitRequest().m_idCheck(member_id);
            observ.enqueue(new Callback<Idcheck>() {
                @Override
                public void onResponse(Call<Idcheck> call, Response<Idcheck> response) {
                    if (response.isSuccessful()) {
                        Idcheck result = response.body();
                        if (result.getIdcount() == 0) {
                            AlertDialog.Builder ad=new AlertDialog.Builder(JoinInputActivity.this);
                            ad.setTitle("사용가능").setMessage("사용가능한 아이디 입니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int which) {
                                    tmpId = member_id;
                                }
                            }).create().show();
                        }
                        else {
                            AlertDialog.Builder ad=new AlertDialog.Builder(JoinInputActivity.this);
                            ad.setTitle("사용불가").setMessage("동일한 아이디가 존재합니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int which) {
                                    tmpId = "";
                                }
                            }).create().show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Idcheck> call, Throwable t) {

                }
            });
        } else {
            AlertDialog.Builder ad=new AlertDialog.Builder(JoinInputActivity.this);
            ad.setTitle("알림창").setMessage("아이디를 작성해주세요.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create().show();
        }
    }

}
