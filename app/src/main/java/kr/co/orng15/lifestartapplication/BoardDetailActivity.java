package kr.co.orng15.lifestartapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.data.Board;
import kr.co.orng15.lifestartapplication.data.Board_Read;
import kr.co.orng15.lifestartapplication.data.ServerInfo;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardDetailActivity extends AppCompatActivity {

    LoginService loginService = LoginService.getInstance();
    @BindView(R.id.txt_b_detail_title) TextView txt_b_detail_title;
    @BindView(R.id.txt_b_detail_end_ymd) TextView txt_b_detail_end_ymd;
    @BindView(R.id.img_company) ImageView img_company;
    @BindView(R.id.txt_b_detail_kind) TextView txt_b_detail_kind;
    @BindView(R.id.txt_b_detail_mainbusiness) TextView txt_b_detail_mainbusiness;
    @BindView(R.id.txt_b_detail_insurance) TextView txt_b_detail_insurance;
    @BindView(R.id.txt_b_detail_address0) TextView txt_b_detail_address0;
    @BindView(R.id.txt_b_detail_address1) TextView txt_b_detail_address1;
    @BindView(R.id.txt_b_detail_address2) TextView txt_b_detail_address2;
    @BindView(R.id.txt_b_detail_moneysize) TextView txt_b_detail_moneysize;
    @BindView(R.id.txt_b_detail_membersize) TextView txt_b_detail_membersize;
    @BindView(R.id.txt_b_detail_ymd) TextView txt_b_detail_ymd;
    @BindView(R.id.txt_b_detail_jobkind) TextView txt_b_detail_jobkind;
    @BindView(R.id.txt_b_detail_money) TextView txt_b_detail_money;
    @BindView(R.id.txt_b_detail_essential) TextView txt_b_detail_essential;
    @BindView(R.id.txt_b_detail_learn) TextView txt_b_detail_learn;
    @BindView(R.id.txt_b_detail_bonus) TextView txt_b_detail_bonus;
    @BindView(R.id.txt_b_detail_jobplace) TextView txt_b_detail_jobplace;
    @BindView(R.id.txt_b_detail_jobtime) TextView txt_b_detail_jobtime;
    @BindView(R.id.txt_b_detail_jobday) TextView txt_b_detail_jobday;
    LinearLayout[] linear_b_detail = new LinearLayout[10];
    ImageView[] img_b_detail = new ImageView[10];
    @BindView(R.id.txt_b_detail_content) TextView txt_b_detail_content;
    @BindView(R.id.txt_b_detail_step) TextView txt_b_detail_step;
    @BindView(R.id.txt_b_detail_document) TextView txt_b_detail_document;
    @BindView(R.id.txt_b_detail_hrd_name) TextView txt_b_detail_hrd_name;
    @BindView(R.id.txt_b_detail_hrd_phone) TextView txt_b_detail_hrd_phone;
    @BindView(R.id.txt_b_detail_hrd_email) TextView txt_b_detail_hrd_email;
    @BindView(R.id.txt_b_detail_membermax) TextView txt_b_detail_membermax;
    @BindView(R.id.txt_b_detail_member) TextView txt_b_detail_member;
    @BindView(R.id.btn_b_detail_apply) Button btn_b_detail_apply;
    Intent intent;
    Long id = 0l;
    Integer company_id = 0;
    Integer apply = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);
        ButterKnife.bind(this);

        linear_b_detail[0] = findViewById(R.id.linear_b_detail_1);
        linear_b_detail[1] = findViewById(R.id.linear_b_detail_2);
        linear_b_detail[2] = findViewById(R.id.linear_b_detail_3);
        linear_b_detail[3] = findViewById(R.id.linear_b_detail_4);
        linear_b_detail[4] = findViewById(R.id.linear_b_detail_5);
        linear_b_detail[5] = findViewById(R.id.linear_b_detail_6);
        linear_b_detail[6] = findViewById(R.id.linear_b_detail_7);
        linear_b_detail[7] = findViewById(R.id.linear_b_detail_8);
        linear_b_detail[8] = findViewById(R.id.linear_b_detail_9);
        linear_b_detail[9] = findViewById(R.id.linear_b_detail_10);

        img_b_detail[0] = findViewById(R.id.txt_b_detail_img1);
        img_b_detail[1] = findViewById(R.id.txt_b_detail_img2);
        img_b_detail[2] = findViewById(R.id.txt_b_detail_img3);
        img_b_detail[3] = findViewById(R.id.txt_b_detail_img4);
        img_b_detail[4] = findViewById(R.id.txt_b_detail_img5);
        img_b_detail[5] = findViewById(R.id.txt_b_detail_img6);
        img_b_detail[6] = findViewById(R.id.txt_b_detail_img7);
        img_b_detail[7] = findViewById(R.id.txt_b_detail_img8);
        img_b_detail[8] = findViewById(R.id.txt_b_detail_img9);
        img_b_detail[9] = findViewById(R.id.txt_b_detail_img10);

        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);
        Call<Board_Read> observ = RetrofitService.getInstance().getRetrofitRequest().m_board_detail(Long.parseLong(loginService.getLoginMember().getId().toString()), id);
        observ.enqueue(new Callback<Board_Read>() {
            @Override
            public void onResponse(Call<Board_Read> call, Response<Board_Read> response) {
                if (response.isSuccessful()) {
                    Board_Read result = response.body();
                    Integer count = result.getBoard_file().size();

                    String member_id_str = result.getBoard().getMember_id().toString();
                    company_id = Integer.parseInt(member_id_str);
                    txt_b_detail_title.setText(result.getBoard().getBoard_title());
                    String endYear = result.getBoard().getBoard_end_year();
                    String endMonth = result.getBoard().getBoard_end_month();
                    String endDay = result.getBoard().getBoard_end_day();
                    String board_detail_end_ymd = endYear + "-" + endMonth + "-" + endDay;
                    txt_b_detail_end_ymd.setText(board_detail_end_ymd);

                    GlideApp.with(BoardDetailActivity.this).load(ServerInfo.url + result.getBoard().getBoard_image())
                            .centerCrop()
                            .into(img_company);

                    txt_b_detail_kind.setText(result.getBoard().getBoard_kind());
                    txt_b_detail_mainbusiness.setText(result.getBoard().getBoard_mainbusiness());
                    txt_b_detail_insurance.setText(result.getBoard().getBoard_insurance());
                    String tmpAddress[] = result.getBoard().getBoard_address().split("\\$");
                    Log.d("hjh", "address1=" + tmpAddress[0]);
                    Log.d("hjh", "address2=" + tmpAddress[1]);
                    Log.d("hjh", "address3=" + tmpAddress[2]);
                    txt_b_detail_address0.setText(tmpAddress[0]);
                    txt_b_detail_address1.setText(tmpAddress[1]);
                    txt_b_detail_address2.setText(tmpAddress[2]);
                    txt_b_detail_moneysize.setText(result.getBoard().getBoard_moneysize());
                    txt_b_detail_membersize.setText(result.getBoard().getBoard_membersize());
                    String year = result.getBoard().getBoard_year();
                    String month = result.getBoard().getBoard_month();
                    String day = result.getBoard().getBoard_day();
                    String board_detail_ymd = year + "/" + month + "/" + day;
                    txt_b_detail_ymd.setText(board_detail_ymd);
                    txt_b_detail_jobkind.setText(result.getBoard().getBoard_jobkind());
                    txt_b_detail_money.setText(result.getBoard().getBoard_money());
                    txt_b_detail_essential.setText(result.getBoard().getBoard_essential());
                    txt_b_detail_learn.setText(result.getBoard().getBoard_learn());
                    txt_b_detail_bonus.setText(result.getBoard().getBoard_bonus());
                    txt_b_detail_jobplace.setText(result.getBoard().getBoard_jobplace());
                    txt_b_detail_jobtime.setText(result.getBoard().getBoard_jobtime());
                    txt_b_detail_jobday.setText(result.getBoard().getBoard_jobday());

                    for (int i = 0; i < count; i++) {
                        linear_b_detail[i].setVisibility(View.VISIBLE);
                        GlideApp.with(BoardDetailActivity.this).load(ServerInfo.url + result.getBoard_file().get(i).getSavename())
                                .centerInside()
                                .into(img_b_detail[i]);
                        Integer height = img_b_detail[i].getHeight();
                    }

                    txt_b_detail_content.setText(result.getBoard().getBoard_content());
                    txt_b_detail_step.setText(result.getBoard().getBoard_step());
                    txt_b_detail_document.setText(result.getBoard().getBoard_document());
                    txt_b_detail_hrd_name.setText(result.getBoard().getBoard_hrd_name());
                    txt_b_detail_hrd_phone.setText(result.getBoard().getBoard_hrd_phone());
                    txt_b_detail_hrd_email.setText(result.getBoard().getBoard_hrd_email());
                    txt_b_detail_membermax.setText(result.getBoard().getBoard_membermax());

                    // 지원자 수 카운트
                    txt_b_detail_member.setText(result.getTotalapply().toString());
                    apply = result.getApplycheck();

                    if (loginService.getLoginMember().getMember_kind() == 0) {
                        btn_b_detail_apply.setVisibility(View.VISIBLE);
                        btn_b_detail_apply.setText("지원");
                    } else if (loginService.getLoginMember().getMember_kind() == 1 && loginService.getLoginMember().getId().toString().equals(company_id.toString())) {
                        btn_b_detail_apply.setVisibility(View.VISIBLE);
                        btn_b_detail_apply.setText("확인");
                    } else if (loginService.getLoginMember().getMember_kind() == 1 && !loginService.getLoginMember().getId().toString().equals(company_id.toString())) {
                        btn_b_detail_apply.setVisibility(View.GONE);
                    }

                    if (btn_b_detail_apply.getVisibility() == View.VISIBLE) {
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Call<Board_Read> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.btn_b_detail_apply)
    void onApply(View view) {
        if(loginService.getLoginMember().getMember_kind() == 0) {
            if(apply == 0) {
                Intent intent = new Intent(BoardDetailActivity.this, BoardCheckActivity.class);
                intent.putExtra("board_id", id);
                startActivityForResult(intent, 0);
            } else if(apply == 1) {
                AlertDialog.Builder ad = new AlertDialog.Builder(BoardDetailActivity.this);
                ad.setTitle("알림창").setMessage("이미 지원하신 목록입니다.").setNeutralButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create().show();
            }
        } else if(loginService.getLoginMember().getMember_kind() == 1 && loginService.getLoginMember().getId().equals(company_id)) {
            Intent intent = new Intent(BoardDetailActivity.this, CompanyListActivity.class);
            intent.putExtra("board_id", id);
            intent.putExtra("kind", 2l);
            startActivityForResult(intent, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                finish();
            } else if(requestCode == 1) {
                finish();
            }
        }
    }
}
