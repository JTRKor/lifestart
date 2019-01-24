package kr.co.orng15.lifestartapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MargerActivity extends AppCompatActivity {

    @BindView(R.id.btn_member_search) Button btn_member_search;
    @BindView(R.id.btn_board_search) Button btn_board_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marger);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_member_search)
    void onMember(View view) {
        Intent intent1 = new Intent(MargerActivity.this, AdminActivity.class);
        startActivity(intent1);
    }

    @OnClick(R.id.btn_board_search)
    void onBoard(View view) {
        Intent intent2 = new Intent(MargerActivity.this, AdminBoardActivity.class);
        startActivity(intent2);
    }
}
