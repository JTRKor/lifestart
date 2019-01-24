package kr.co.orng15.lifestartapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.adapter.AdminAdapter;
import kr.co.orng15.lifestartapplication.data.Member;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {

    ArrayList<Member> items;
    AdminAdapter adminAdapter;
    @BindView(R.id.spinner_main_search) Spinner spinner_main_search;
    @BindView(R.id.linear_id) LinearLayout linear_id;
    @BindView(R.id.ed_id_search) EditText ed_id_search;
    @BindView(R.id.linear_kind) LinearLayout linear_kind;
    @BindView(R.id.spinner_kind) Spinner spinner_kind;
    @BindView(R.id.linear_status) LinearLayout linear_status;
    @BindView(R.id.spinner_status) Spinner spinner_status;
    @BindView(R.id.txt_total) TextView txt_total;
    @BindView(R.id.btn_admin_search) Button btn_admin_search;
    @BindView(R.id.btn_close1) Button btn_close1;
    @BindView(R.id.lv_admin) ListView lv_admin;
    String search = "";
    String kind = "0";
    String status = "0";
    String main_position = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);

        ArrayAdapter mainAdapter = ArrayAdapter.createFromResource(this, R.array.main_search, android.R.layout.simple_spinner_item);
        mainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_main_search.setAdapter(mainAdapter);

        ArrayAdapter kindAdapter = ArrayAdapter.createFromResource(this, R.array.member_kind, android.R.layout.simple_spinner_item);
        kindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_kind.setAdapter(kindAdapter);

        ArrayAdapter statusAdapter = ArrayAdapter.createFromResource(this, R.array.member_status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_status.setAdapter(statusAdapter);

        spinner_main_search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    main_position = "0";
                    linear_id.setVisibility(View.VISIBLE);
                    linear_kind.setVisibility(View.GONE);
                    linear_status.setVisibility(View.GONE);
                }
                else if (position == 1) {
                    main_position = "1";
                    linear_id.setVisibility(View.GONE);
                    linear_kind.setVisibility(View.VISIBLE);
                    linear_status.setVisibility(View.GONE);

                    spinner_kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position == 0) {
                                kind = "0";
                            } else if(position == 1) {
                                kind = "1";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                else if (position == 2) {
                    main_position = "2";
                    linear_id.setVisibility(View.GONE);
                    linear_kind.setVisibility(View.GONE);
                    linear_status.setVisibility(View.VISIBLE);

                    spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position == 0) {
                                status = "0";
                            } else if(position == 1) {
                                status = "1";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_admin_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = ed_id_search.getText().toString();

                Log.d("hjh", "main=" + main_position);
                Log.d("hjh", "search=" + search);
                Log.d("hjh", "kind=" + kind);
                Log.d("hjh", "status=" + status);

                refresh();
            }
        });
    }

    @OnClick(R.id.btn_close1)
    void onClose(View view) {
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void refresh() {
        Call<ArrayList<Member>> observ = RetrofitService.getInstance().getRetrofitRequest().m_admin_member_get(main_position, search, kind, status);
        observ.enqueue(new Callback<ArrayList<Member>>() {
            @Override
            public void onResponse(Call<ArrayList<Member>> call, Response<ArrayList<Member>> response) {
                if (response.isSuccessful()) {
                    items = response.body();
                    adminAdapter = new AdminAdapter(items);
                    lv_admin.setAdapter(adminAdapter);
                    Integer tmp = items.size();
                    txt_total.setText(tmp.toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Member>> call, Throwable t) {

            }
        });
    }
}
