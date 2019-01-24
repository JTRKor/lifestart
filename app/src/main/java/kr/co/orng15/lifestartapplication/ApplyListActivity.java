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
import kr.co.orng15.lifestartapplication.adapter.PerListAdapter;
import kr.co.orng15.lifestartapplication.data.Apply;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyListActivity extends AppCompatActivity {
    LoginService loginService = LoginService.getInstance();

    ArrayList<Apply> items;

    PerListAdapter perListAdapter;
    @BindView(R.id.lv_apply_list) ListView lv_apply_list;
    @BindView(R.id.btn_p_i_back) Button btn_p_i_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_list);
        ButterKnife.bind(this);

        refresh();

        lv_apply_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ApplyListActivity.this, BoardDetailActivity.class);
                intent.putExtra("id", items.get(position).getBoard_id());
                startActivityForResult(intent, 0);

            }
        });
    }

    @OnClick(R.id.btn_p_i_back)
    void onBack(View view) {
        finish();
    }

    public void refresh() {
        Call<ArrayList<Apply>> observ = RetrofitService.getInstance().getRetrofitRequest().m_personal_apply(loginService.getLoginMember().getId());
        observ.enqueue(new Callback<ArrayList<Apply>>() {
            @Override
            public void onResponse(Call<ArrayList<Apply>> call, Response<ArrayList<Apply>> response) {
                if (response.isSuccessful()) {
                    items = response.body();
                    perListAdapter = new PerListAdapter(items);
                    lv_apply_list.setAdapter(perListAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Apply>> call, Throwable t) {

            }
        });
    }
}
