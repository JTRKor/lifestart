package kr.co.orng15.lifestartapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.adapter.AdminBoardAdapter;
import kr.co.orng15.lifestartapplication.data.Board;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBoardActivity extends AppCompatActivity {

    AdminBoardAdapter adminBoardAdapter;
    ArrayList<Board> items;

    @BindView(R.id.ed_search_board) EditText ed_search_board;
    @BindView(R.id.btn_board_search) Button btn_board_search;
    @BindView(R.id.btn_close2) Button btn_close2;
    @BindView(R.id.txt_total_board) TextView txt_total_board;
    @BindView(R.id.lv_admin_board) ListView lv_admin_board;
    String search = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_board);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_board_search)
    void onSearch(View view) {
        search = ed_search_board.getText().toString();

        refresh();
    }

    @OnClick(R.id.btn_close2)
    void onClose(View view) {
        Intent intent = new Intent(AdminBoardActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void refresh() {
        Call<ArrayList<Board>> observ = RetrofitService.getInstance().getRetrofitRequest().m_admin_board_get(search);
        observ.enqueue(new Callback<ArrayList<Board>>() {
            @Override
            public void onResponse(Call<ArrayList<Board>> call, Response<ArrayList<Board>> response) {
                if (response.isSuccessful()) {
                    items = response.body();
                    adminBoardAdapter = new AdminBoardAdapter(items);
                    lv_admin_board.setAdapter(adminBoardAdapter);

                    Integer tmp = items.size();
                    txt_total_board.setText(tmp.toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Board>> call, Throwable t) {

            }
        });
    }
}
