package kr.co.orng15.lifestartapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.orng15.lifestartapplication.data.Apply;
import kr.co.orng15.lifestartapplication.data.Board;
import kr.co.orng15.lifestartapplication.data.ServerInfo;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyConfirmActivity extends AppCompatActivity {

    @BindView(R.id.txt_confirm_title) TextView txt_confirm_title;
    @BindView(R.id.img_confirm) ImageView img_confirm;
    @BindView(R.id.txt_confirm_name) TextView txt_confirm_name;
    @BindView(R.id.txt_confirm_year) TextView txt_confirm_year;
    @BindView(R.id.txt_confirm_month) TextView txt_confirm_month;
    @BindView(R.id.txt_confirm_day) TextView txt_confirm_day;
    @BindView(R.id.txt_confirm_phone1) TextView txt_confirm_phone1;
    @BindView(R.id.txt_confirm_phone2) TextView txt_confirm_phone2;
    @BindView(R.id.txt_confirm_phone3) TextView txt_confirm_phone3;
    @BindView(R.id.txt_confirm_address1) TextView txt_confirm_address1;
    @BindView(R.id.txt_confirm_address2) TextView txt_confirm_address2;
    @BindView(R.id.txt_confirm_address3) TextView txt_confirm_address3;
    @BindView(R.id.txt_confirm_school) TextView txt_confirm_school;
    @BindView(R.id.txt_confirm_learn_finish) TextView txt_confirm_learn_finish;
    @BindView(R.id.txt_confirm_company_school) TextView txt_confirm_company_school;
    @BindView(R.id.txt_confirm_career_name1) TextView txt_confirm_career_name1;
    @BindView(R.id.txt_confirm_career_kind1) TextView txt_confirm_career_kind1;
    @BindView(R.id.txt_confirm_career_month1) TextView txt_confirm_career_month1;
    @BindView(R.id.txt_confirm_career_main1) TextView txt_confirm_career_main1;
    @BindView(R.id.txt_confirm_career_name2) TextView txt_confirm_career_name2;
    @BindView(R.id.txt_confirm_career_kind2) TextView txt_confirm_career_kind2;
    @BindView(R.id.txt_confirm_career_month2) TextView txt_confirm_career_month2;
    @BindView(R.id.txt_confirm_career_main2) TextView txt_confirm_career_main2;
    @BindView(R.id.txt_confirm_career_name3) TextView txt_confirm_career_name3;
    @BindView(R.id.txt_confirm_career_kind3) TextView txt_confirm_career_kind3;
    @BindView(R.id.txt_confirm_career_month3) TextView txt_confirm_career_month3;
    @BindView(R.id.txt_confirm_career_main3) TextView txt_confirm_career_main3;
    @BindView(R.id.txt_confirm_company_career) TextView txt_confirm_company_career;
    @BindView(R.id.txt_confirm_license1) TextView txt_confirm_license1;
    @BindView(R.id.txt_confirm_license2) TextView txt_confirm_license2;
    @BindView(R.id.txt_confirm_license3) TextView txt_confirm_license3;
    @BindView(R.id.txt_confirm_company_license) TextView txt_confirm_company_license;
    @BindView(R.id.txt_confirm_company_prior) TextView txt_confirm_company_prior;
    @BindView(R.id.txt_confirm_resume) TextView txt_confirm_resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_confirm);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Long id = intent.getLongExtra("id", 0);

        Call<Apply> observ = RetrofitService.getInstance().getRetrofitRequest().m_apply_resume(id);
        observ.enqueue(new Callback<Apply>() {
            @Override
            public void onResponse(Call<Apply> call, Response<Apply> response) {
                if (response.isSuccessful()) {
                    final Apply result = response.body();

                    txt_confirm_title.setText(result.getApply_title());

                    GlideApp.with(ApplyConfirmActivity.this).load(ServerInfo.url + result.getApply_image())
                            .centerCrop()
                            .into(img_confirm);

                    txt_confirm_name.setText(result.getApply_name());
                    txt_confirm_year.setText(result.getApply_birth_year());
                    txt_confirm_month.setText(result.getApply_birth_month());
                    txt_confirm_day.setText(result.getApply_birth_day());
                    String phoneArr[] = result.getApply_phone().split("-");
                    txt_confirm_phone1.setText(phoneArr[0]);
                    txt_confirm_phone2.setText(phoneArr[1]);
                    txt_confirm_phone3.setText(phoneArr[2]);
                    String addressArr[] = result.getApply_address().split("\\$");
                    txt_confirm_address1.setText(addressArr[0]);
                    txt_confirm_address2.setText(addressArr[1]);
                    txt_confirm_address3.setText(addressArr[2]);
                    txt_confirm_school.setText(result.getApply_learn());
                    txt_confirm_learn_finish.setText(result.getApply_learn_finish());
                    String careerArr[] = result.getApply_career().split("\\$");
                    if (!careerArr[0].equals("없음")) {
                        for (int i = 0; i < careerArr.length; i++) {
                            String tmpCareer[] = careerArr[i].split("#");
                            for (int j = 0; j < tmpCareer.length; j++) {
                                if (i == 0) {
                                    txt_confirm_career_name1.setText(tmpCareer[0]);
                                    txt_confirm_career_kind1.setText(tmpCareer[1]);
                                    txt_confirm_career_month1.setText(tmpCareer[2]);
                                    txt_confirm_career_main1.setText(tmpCareer[3]);
                                }
                                else if (i == 1) {
                                    txt_confirm_career_name2.setText(tmpCareer[0]);
                                    txt_confirm_career_kind2.setText(tmpCareer[1]);
                                    txt_confirm_career_month2.setText(tmpCareer[2]);
                                    txt_confirm_career_main2.setText(tmpCareer[3]);
                                }
                                else if (i == 2) {
                                    txt_confirm_career_name3.setText(tmpCareer[0]);
                                    txt_confirm_career_kind3.setText(tmpCareer[1]);
                                    txt_confirm_career_month3.setText(tmpCareer[2]);
                                    txt_confirm_career_main3.setText(tmpCareer[3]);
                                }
                            }
                        }
                    }

                    String licenseArr[] = result.getApply_license().split("\\$");
                    if(!licenseArr.equals("없음")) {
                        for(int i=0; i<licenseArr.length; i++) {
                            if(i == 0) {
                                txt_confirm_license1.setText(licenseArr[0]);
                            } else if(i == 1) {
                                txt_confirm_license2.setText(licenseArr[1]);
                            } else if(i == 2) {
                                txt_confirm_license3.setText(licenseArr[2]);
                            }
                        }
                    }

                    txt_confirm_resume.setText(result.getApply_myresume());
                    Call<Board> observ = RetrofitService.getInstance().getRetrofitRequest().m_apply_board(result.getBoard_id());
                    observ.enqueue(new Callback<Board>() {
                        @Override
                        public void onResponse(Call<Board> call, Response<Board> response) {
                            if (response.isSuccessful()) {
                                Board result2 = response.body();

                                txt_confirm_company_school.setText(result2.getBoard_learn());
                                txt_confirm_company_career.setText(result2.getBoard_career());
                                txt_confirm_company_license.setText(result2.getBoard_essential());
                                txt_confirm_company_prior.setText(result2.getBoard_bonus());
                            }
                        }

                        @Override
                        public void onFailure(Call<Board> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Apply> call, Throwable t) {

            }
        });
    }
}
