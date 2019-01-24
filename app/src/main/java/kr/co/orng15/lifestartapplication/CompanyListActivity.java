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
import kr.co.orng15.lifestartapplication.adapter.ComListAdapter;
import kr.co.orng15.lifestartapplication.data.Apply;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyListActivity extends AppCompatActivity {
    LoginService loginService = LoginService.getInstance();
    ArrayList<Apply> items;
    ComListAdapter comListAdapter;
    @BindView(R.id.lv_com_list) ListView lv_com_list;
    @BindView(R.id.btn_c_i_back) Button btn_c_i_back;
    Long board_id = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        ButterKnife.bind(this);

        Intent intent2 = getIntent();
        Long kind = intent2.getLongExtra("kind", 0l);
        Log.d("hjh", "kind=" + kind);
        if (kind.toString().equals("1")) {
            Log.d("hjh", "1");
            refresh();
        }
        else if (kind.toString().equals("2")) {
            Log.d("hjh", "3");
            board_id = intent2.getLongExtra("board_id", 0l);
            refresh2();
        }

        lv_com_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CompanyListActivity.this, ApplyConfirmActivity.class);
                intent.putExtra("id", items.get(position).getId());
                startActivityForResult(intent, 0);
            }
        });
    }

    @OnClick(R.id.btn_c_i_back)
    void onBack(View view) {
        finish();
    }

    public void refresh() {
        Log.d("hjh", "re1");
        Call<ArrayList<Apply>> observ = RetrofitService.getInstance().getRetrofitRequest().m_company_list(loginService.getLoginMember().getId());
        observ.enqueue(new Callback<ArrayList<Apply>>() {
            @Override
            public void onResponse(Call<ArrayList<Apply>> call, Response<ArrayList<Apply>> response) {
                Log.d("hjh", "re2");
                if (response.isSuccessful()) {
                    items = response.body();
                    comListAdapter = new ComListAdapter(items);
                    Log.d("hjh", items.toString());
                    lv_com_list.setAdapter(comListAdapter);
                    Log.d("hjh", "re3");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Apply>> call, Throwable t) {

            }
        });
    }

    public void refresh2() {
        Log.d("hjh", "rere1");
        Call<ArrayList<Apply>> observ = RetrofitService.getInstance().getRetrofitRequest().m_company_select_list(board_id, Long.parseLong(loginService.getLoginMember().getId().toString()));
        observ.enqueue(new Callback<ArrayList<Apply>>() {
            @Override
            public void onResponse(Call<ArrayList<Apply>> call, Response<ArrayList<Apply>> response) {
                Log.d("hjh", "rere2");
                if (response.isSuccessful()) {
                    items = response.body();
                    comListAdapter = new ComListAdapter(items);
                    lv_com_list.setAdapter(comListAdapter);
                    Log.d("hjh", "rere3");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Apply>> call, Throwable t) {

            }
        });
    }
}
