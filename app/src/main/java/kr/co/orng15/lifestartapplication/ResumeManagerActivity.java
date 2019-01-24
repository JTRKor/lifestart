package kr.co.orng15.lifestartapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.adapter.ResumeListAdapter;
import kr.co.orng15.lifestartapplication.data.Personal_Detail_Resume;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumeManagerActivity extends AppCompatActivity {
    LoginService loginService = LoginService.getInstance();
    @BindView(R.id.btn_r_m_back) Button btn_r_m_back;
    @BindView(R.id.lv_r_m) ListView lv_r_m;
    @BindView(R.id.btn_plus_resume) Button btn_plus_resume;
    ArrayList<Personal_Detail_Resume> items;
    ResumeListAdapter resumeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_manager);
        ButterKnife.bind(this);

        refresh();

        lv_r_m.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ResumeManagerActivity.this, ResumeDetailActivity.class);
                intent.putExtra("id", items.get(position).getId());
                startActivityForResult(intent, 1);
            }
        });
    }

    @OnClick(R.id.btn_r_m_back)
    void onBack(View view) {
        finish();
    }

    @OnClick(R.id.btn_plus_resume)
    void plusResume(View view) {
        Intent intent = new Intent(ResumeManagerActivity.this, ResumeDetailActivity.class);
        intent.putExtra("id", 0);
        startActivityForResult(intent, 0);
    }

    public void refresh() {
        Call<ArrayList<Personal_Detail_Resume>> observ = RetrofitService.getInstance().getRetrofitRequest().m_personal_detail_resume_get(loginService.getLoginMember().getId());
        observ.enqueue(new Callback<ArrayList<Personal_Detail_Resume>>() {
            @Override
            public void onResponse(Call<ArrayList<Personal_Detail_Resume>> call, Response<ArrayList<Personal_Detail_Resume>> response) {
                if (response.isSuccessful()) {
                    items = response.body();
                    resumeListAdapter = new ResumeListAdapter(items);
                    lv_r_m.setAdapter(resumeListAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Personal_Detail_Resume>> call, Throwable t) {

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                refresh();
            }
            else if (requestCode == 1) {
                refresh();
            }
        }
    }
}
