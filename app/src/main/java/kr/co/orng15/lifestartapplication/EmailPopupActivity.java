package kr.co.orng15.lifestartapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kr.co.orng15.lifestartapplication.data.FirstCertified;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailPopupActivity extends AppCompatActivity implements View.OnClickListener, Dialog.OnCancelListener{

    LoginService loginService = LoginService.getInstance();

    EditText authEmail;
    Button authBtn;

    String emailInput = "";
    Long cetifiedid = 0l;
    String code = "";

    LayoutInflater dialog; //LayoutInflater
    View dialogLayout; //layout을 담을 View
    Dialog authDialog; //dialog 객체

    TextView time_counter; //시간을 보여주는 TextView
    EditText emailAuth_number; //인증 번호를 입력 하는 칸
    Button emailAuth_btn; // 인증버튼
    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_popup);

        authEmail = findViewById(R.id.authEmail);
        authBtn = findViewById(R.id.authBtn);
        authBtn.setOnClickListener(this);
    }

    public void countDownTimer() { //카운트 다운 메소드

        time_counter = dialogLayout.findViewById(R.id.emailAuth_time_counter);
        //줄어드는 시간을 나타내는 TextView
        emailAuth_number = dialogLayout.findViewById(R.id.emailAuth_number);
        //사용자 인증 번호 입력창
        emailAuth_btn = dialogLayout.findViewById(R.id.emailAuth_btn);
        //인증하기 버튼


        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) { //(300초에서 1초 마다 계속 줄어듬)

                long emailAuthCount = millisUntilFinished / 1000;

                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                    time_counter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    time_counter.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }

                //emailAuthCount은 종료까지 남은 시간임. 1분 = 60초 되므로,
                // 분을 나타내기 위해서는 종료까지 남은 총 시간에 60을 나눠주면 그 몫이 분이 된다.
                // 분을 제외하고 남은 초를 나타내기 위해서는, (총 남은 시간 - (분*60) = 남은 초) 로 하면 된다.

            }


            @Override
            public void onFinish() { //시간이 다 되면 다이얼로그 종료

                authDialog.cancel();

            }
        }.start();

        emailAuth_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.authBtn :
                emailInput = authEmail.getText().toString();
                if(emailInput.equals("")) {
                    AlertDialog.Builder ad=new AlertDialog.Builder(EmailPopupActivity.this);
                    ad.setTitle("알림창").setMessage("이메일을 입력해주세요.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                } else {
                    Call<FirstCertified> observ = RetrofitService.getInstance().getRetrofitRequest().m_mailsend(loginService.getLoginMember().getId(), emailInput);
                    observ.enqueue(new Callback<FirstCertified>() {
                        @Override
                        public void onResponse(Call<FirstCertified> call, Response<FirstCertified> response) {
                            if (response.isSuccessful()) {
                                FirstCertified result = response.body();

                                dialog = LayoutInflater.from(EmailPopupActivity.this);
                                dialogLayout = dialog.inflate(R.layout.auth_dialog, null); // LayoutInflater를 통해 XML에 정의된 Resource들을 View의 형태로 반환 시켜 줌
                                authDialog = new Dialog(EmailPopupActivity.this);          //Dialog 객체 생성
                                authDialog.setContentView(dialogLayout);                   //Dialog에 inflate한 View를 탑재 하여줌
                                authDialog.setCanceledOnTouchOutside(false);               //Dialog 바깥 부분을 선택해도 닫히지 않게 설정함.
                                authDialog.show();                                         //Dialog를 나타내어 준다.
                                countDownTimer();

                                cetifiedid = result.getId();

                                //백버튼 클릭시 타이머 카운트 정지
                                authDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        countDownTimer.cancel();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<FirstCertified> call, Throwable t) {

                        }
                    });
                }
                break;

            case R.id.emailAuth_btn : //다이얼로그 내의 인증번호 인증 버튼을 눌렀을 시
                code = emailAuth_number.getText().toString();
                Call<FirstCertified> observ = RetrofitService.getInstance().getRetrofitRequest().m_certified_ok(cetifiedid, loginService.getLoginMember().getId(), code);
                observ.enqueue(new Callback<FirstCertified>() {
                    @Override
                    public void onResponse(Call<FirstCertified> call, Response<FirstCertified> response) {
                        if (response.isSuccessful()) {
                            FirstCertified result = response.body();
                            if(result.getId() == 0) {
                                AlertDialog.Builder ad=new AlertDialog.Builder(EmailPopupActivity.this);
                                ad.setTitle("알림창").setMessage("이메일 인증 성공").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = getIntent();
                                        intent.putExtra("email", authEmail.getText().toString());
                                        setResult(RESULT_OK, intent);
                                        authDialog.dismiss();
                                        finish();
                                    }
                                }).create().show();
                            } else {
                                AlertDialog.Builder ad=new AlertDialog.Builder(EmailPopupActivity.this);
                                ad.setTitle("알림창").setMessage("인증번호를 입력해주세요.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).create().show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FirstCertified> call, Throwable t) {

                    }
                });

                break;
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        countDownTimer.cancel();
    } //다이얼로그 닫을 때 카운트 다운 타이머의 cancel()메소드 호출
}
