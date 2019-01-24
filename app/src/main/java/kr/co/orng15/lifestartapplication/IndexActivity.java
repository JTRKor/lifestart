package kr.co.orng15.lifestartapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.adapter.ListAdapter;
import kr.co.orng15.lifestartapplication.data.Board;
import kr.co.orng15.lifestartapplication.data.Board_Get_Use;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndexActivity extends AppCompatActivity {
    LoginService loginService = LoginService.getInstance();
    ArrayList<Board> items;
    ListAdapter listAdapter;

    @BindView(R.id.ed_company_name) EditText ed_company_name;
    @BindView(R.id.btn_company_serach) Button btn_company_serach;
    @BindView(R.id.txt_login_name) TextView txt_login_name;
    @BindView(R.id.btn_info_com_write) Button btn_info_com_write;
    @BindView(R.id.btn_info_modify) Button btn_info_modify;
    @BindView(R.id.btn_logout) Button btn_logout;
    @BindView(R.id.btn_info_list) Button btn_info_list;
    @BindView(R.id.lv_index) ListView lv_index;
    String search ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ButterKnife.bind(this);

        refresh();

        if(loginService.getLoginMember().getMember_kind() == 1) {
            btn_info_com_write.setVisibility(View.VISIBLE);
            btn_info_list.setText("공고목록");

            btn_info_com_write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(loginService.getLoginMember().getMember_history() <= 1) {
                        AlertDialog.Builder ad=new AlertDialog.Builder(IndexActivity.this);
                        ad.setTitle("알림창").setMessage("기업정보를 입력해야합니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                    }
                    else {
                        Intent intent = new Intent(IndexActivity.this, BoardActivity.class);
                        intent.putExtra("id", loginService.getLoginMember().getMember_id());
                        startActivityForResult(intent, 1);
                    }
                }
            });

        } else {
            btn_info_com_write.setVisibility(View.GONE);
            btn_info_list.setText("지원목록");
        }

        String login_name;
        if (loginService != null) {
            login_name = loginService.getLoginId().toString();
            txt_login_name.setText(login_name);
        }
        else {
            finish();
        }

        lv_index.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(IndexActivity.this, BoardDetailActivity.class);
                intent.putExtra("id", items.get(position).getId());
                startActivityForResult(intent, 0);
            }
        });
    }

    @OnClick(R.id.btn_info_list)
    void onList(View view) {
        if(loginService.getLoginMember().getMember_kind() == 1) {
            Intent intent = new Intent(IndexActivity.this, CompanyListActivity.class);
            intent.putExtra("kind", 1l);
            startActivity(intent);
        } else if(loginService.getLoginMember().getMember_kind() == 0) {
            Intent intent = new Intent(IndexActivity.this, ApplyListActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.btn_company_serach)
    void onSearh(View view) {
        search = ed_company_name.getText().toString();

        refresh();
    }

    @OnClick(R.id.btn_info_modify)
    void onInfoModify(View view) {
        Intent intent = new Intent(IndexActivity.this, ComModifyActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_logout)
    void onLogoutCall(View view) {
        loginService.setLoginMember(null);
        loginService.setLoginId(null);
        loginService.setLoginPw(null);
        finish();
    }

    public void refresh() {
        Call<ArrayList<Board>> observ = RetrofitService.getInstance().getRetrofitRequest().m_board_get(search);
        observ.enqueue(new Callback<ArrayList<Board>>() {
            @Override
            public void onResponse(Call<ArrayList<Board>> call, Response<ArrayList<Board>> response) {
                if (response.isSuccessful()) {
                    items = response.body();
                    listAdapter = new ListAdapter(items);
                    lv_index.setAdapter(listAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Board>> call, Throwable t) {

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                refresh();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }
}
