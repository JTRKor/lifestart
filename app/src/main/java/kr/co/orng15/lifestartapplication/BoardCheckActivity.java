package kr.co.orng15.lifestartapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.adapter.JobApplyAdapter;
import kr.co.orng15.lifestartapplication.adapter.ResumeListAdapter;
import kr.co.orng15.lifestartapplication.bus.BusProvider;
import kr.co.orng15.lifestartapplication.data.Mresult;
import kr.co.orng15.lifestartapplication.data.Personal_Detail_Resume;
import kr.co.orng15.lifestartapplication.data.Personal_Detail_Resume_Use;
import kr.co.orng15.lifestartapplication.event.TotalChange;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardCheckActivity extends AppCompatActivity {

    Bus bus = BusProvider.getInstance().getBus();
    LoginService loginService = LoginService.getInstance();
    ArrayList<Personal_Detail_Resume> items;
    JobApplyAdapter jobApplyAdapter;
    @BindView(R.id.lv_confirm) ListView lv_confirm;
    @BindView(R.id.btn_resume_apply) Button btn_resume_apply;

    Integer position = -1;
    Long member_id = 0l;
    Long board_id = 0l;
    Long resume_id = 0l;
    String resume_title = "";
    EditText ed_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_check);
        ButterKnife.bind(this);
        bus.register(this);

        refresh();
    }

    @OnClick(R.id.btn_resume_apply)
    void onConfirm(View view) {
        if (position == -1) {
            AlertDialog.Builder ad=new AlertDialog.Builder(BoardCheckActivity.this);
            ad.setTitle("알림창").setMessage("자기소개서 항목을 선택해주세요.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create().show();
        }
        else {
            AlertDialog.Builder ad = new AlertDialog.Builder(BoardCheckActivity.this);
            ad.setView(ed_test);
            ad.setTitle("입사지원 확인").setMessage("자소서 명을 변경하시겠습니까?").setNeutralButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    resume_title = ed_test.getText().toString();
                    String tmpId = loginService.getLoginMember().getId().toString();
                    member_id = Long.parseLong(tmpId);

                    Call<Mresult> observ = RetrofitService.getInstance().getRetrofitRequest().m_board_apply(member_id, board_id, resume_id, resume_title);
                    observ.enqueue(new Callback<Mresult>() {
                        @Override
                        public void onResponse(Call<Mresult> call, Response<Mresult> response) {
                            Mresult result = response.body();
                            Log.d("hjh", "result=" + result.toString());
                            if (result.getResult() == 0) {
                                AlertDialog.Builder ad1 = new AlertDialog.Builder(BoardCheckActivity.this);
                                ad1.setTitle("알림창").setMessage("접수되었습니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = getIntent();
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                }).create().show();
                            }
                            else {
                                Log.d("hjh", "51");
                                AlertDialog.Builder ad2 = new AlertDialog.Builder(BoardCheckActivity.this);
                                ad2.setTitle("알림창").setMessage("마감되었습니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).create().show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Mresult> call, Throwable t) {

                        }
                    });
                }
            });
            ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                }
            }).create().show();
        }
    }

    public void refresh() {
        Intent intent = getIntent();
        board_id = intent.getLongExtra("board_id", 0);
        Call<ArrayList<Personal_Detail_Resume>> observ = RetrofitService.getInstance().getRetrofitRequest().m_personal_detail_resume_get(loginService.getLoginMember().getId());
        observ.enqueue(new Callback<ArrayList<Personal_Detail_Resume>>() {
            @Override
            public void onResponse(Call<ArrayList<Personal_Detail_Resume>> call, Response<ArrayList<Personal_Detail_Resume>> response) {
                if (response.isSuccessful()) {
                    items = response.body();
                    jobApplyAdapter = new JobApplyAdapter(items);
                    lv_confirm.setAdapter(jobApplyAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Personal_Detail_Resume>> call, Throwable t) {

            }
        });
    }

    @Subscribe
    public void cardUse(TotalChange event) {
        ed_test = new EditText(BoardCheckActivity.this);
        position = event.getPosition();

        resume_id = items.get(position).getId();

        ed_test.setText(items.get(position).getDetail_title().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
