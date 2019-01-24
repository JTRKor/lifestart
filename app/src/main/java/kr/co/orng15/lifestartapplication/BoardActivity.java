package kr.co.orng15.lifestartapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.data.Board_Write;
import kr.co.orng15.lifestartapplication.data.Company_Detail;
import kr.co.orng15.lifestartapplication.data.Member;
import kr.co.orng15.lifestartapplication.data.ServerInfo;
import kr.co.orng15.lifestartapplication.login.LoginService;
import kr.co.orng15.lifestartapplication.retrofit.RetrofitService;
import kr.co.orng15.lifestartapplication.util.RealPathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardActivity extends AppCompatActivity {
    LoginService loginService = LoginService.getInstance();

    @BindView(R.id.ed_board_title) EditText ed_board_title;
    @BindView(R.id.txt_b_visible_title) TextView txt_b_visible_title;
    @BindView(R.id.img_board_main) ImageView img_board_main;
    @BindView(R.id.txt_r_visible_main_img) TextView txt_r_visible_main_img;
    @BindView(R.id.ed_board_jobkind) EditText ed_board_jobkind;
    @BindView(R.id.txt_b_visible_jobkind) TextView txt_b_visible_jobkind;
    @BindView(R.id.radio_group) RadioGroup radio_group;
    @BindView(R.id.rd_btn1) RadioButton rd_btn1;
    @BindView(R.id.rd_btn2) RadioButton rd_btn2;
    @BindView(R.id.txt_b_visible_new) TextView txt_b_visible_new;
    @BindView(R.id.ed_board_career) EditText ed_board_career;
    @BindView(R.id.txt_b_visible_career) TextView txt_b_visible_career;
    @BindView(R.id.ed_board_learn) EditText ed_board_learn;
    @BindView(R.id.txt_b_visible_learn) TextView txt_b_visible_learn;
    @BindView(R.id.ed_board_essential) EditText ed_board_essential;
    @BindView(R.id.txt_b_visible_essential) TextView txt_b_visible_essential;
    @BindView(R.id.ed_board_bonus) EditText ed_board_bonus;
    @BindView(R.id.txt_b_visible_bonus) TextView txt_b_visible_bonus;
    @BindView(R.id.ed_board_membermax) EditText ed_board_membermax;
    @BindView(R.id.txt_b_visible_membermax) TextView txt_b_visible_membermax;
    @BindView(R.id.ed_board_jobplace) EditText ed_board_jobplace;
    @BindView(R.id.txt_b_visible_jobplace) TextView txt_b_visible_jobplace;
    @BindView(R.id.ed_board_jobday) EditText ed_board_jobday;
    @BindView(R.id.txt_b_visible_jobday) TextView txt_b_visible_jobday;
    @BindView(R.id.ed_board_jobtime) EditText ed_board_jobtime;
    @BindView(R.id.txt_b_visible_jobtime) TextView txt_b_visible_jobtime;
    @BindView(R.id.ed_board_money) EditText ed_board_money;
    @BindView(R.id.txt_b_visible_money) TextView txt_b_visible_money;
    @BindView(R.id.ed_board_step) EditText ed_board_step;
    @BindView(R.id.txt_b_visible_step) TextView txt_b_visible_step;
    @BindView(R.id.ed_board_document) EditText ed_board_document;
    @BindView(R.id.txt_b_visible_document) TextView txt_b_visible_document;
    @BindView(R.id.btn_end_cal) Button btn_end_cal;
    @BindView(R.id.ed_board_end_year) TextView ed_board_end_year;
    @BindView(R.id.ed_board_end_month) TextView ed_board_end_month;
    @BindView(R.id.ed_board_end_day) TextView ed_board_end_day;
    @BindView(R.id.txt_board_end_ymd) TextView txt_board_end_ymd;
    @BindView(R.id.ed_board_content) EditText ed_board_content;
    @BindView(R.id.txt_b_visible_content) TextView txt_b_visible_content;
    @BindView(R.id.btn_img1) Button btn_img1;
    @BindView(R.id.img_file1) ImageView img_file1;
    @BindView(R.id.btn_img2) Button btn_img2;
    @BindView(R.id.img_file2) ImageView img_file2;
    @BindView(R.id.btn_img3) Button btn_img3;
    @BindView(R.id.img_file3) ImageView img_file3;
    @BindView(R.id.btn_img4) Button btn_img4;
    @BindView(R.id.img_file4) ImageView img_file4;
    @BindView(R.id.btn_img5) Button btn_img5;
    @BindView(R.id.img_file5) ImageView img_file5;
    @BindView(R.id.btn_img6) Button btn_img6;
    @BindView(R.id.img_file6) ImageView img_file6;
    @BindView(R.id.btn_img7) Button btn_img7;
    @BindView(R.id.img_file7) ImageView img_file7;
    @BindView(R.id.btn_img8) Button btn_img8;
    @BindView(R.id.img_file8) ImageView img_file8;
    @BindView(R.id.btn_img9) Button btn_img9;
    @BindView(R.id.img_file9) ImageView img_file9;
    @BindView(R.id.btn_img10) Button btn_img10;
    @BindView(R.id.img_file10) ImageView img_file10;
    @BindView(R.id.txt_b_visible_photo) TextView txt_b_visible_photo;
    @BindView(R.id.ed_board_name) TextView ed_board_name;
    @BindView(R.id.txt_b_visible_name) TextView txt_b_visible_name;
    @BindView(R.id.ed_board_kind) TextView ed_board_kind;
    @BindView(R.id.txt_b_visible_kind) TextView txt_b_visible_kind;
    @BindView(R.id.ed_board_mainbusiness) TextView ed_board_mainbusiness;
    @BindView(R.id.txt_b_visible_mainbusiness) TextView txt_b_visible_mainbusiness;
    @BindView(R.id.ed_board_insurance) TextView ed_board_insurance;
    @BindView(R.id.txt_b_visible_insurance) TextView txt_b_visible_insurance;
    @BindView(R.id.btn_board_find_address) Button btn_board_find_address;
    @BindView(R.id.txt_board_address0) TextView txt_board_address0;
    @BindView(R.id.txt_board_address1) TextView txt_board_address1;
    @BindView(R.id.ed_board_address2) TextView ed_board_address2;
    @BindView(R.id.txt_b_visible_address) TextView txt_b_visible_address;
    @BindView(R.id.ed_board_moneysize) TextView ed_board_moneysize;
    @BindView(R.id.txt_b_visible_moneysize) TextView txt_b_visible_moneysize;
    @BindView(R.id.ed_board_membersize) TextView ed_board_membersize;
    @BindView(R.id.txt_b_visible_membersize) TextView txt_b_visible_membersize;
    @BindView(R.id.ed_board_year) TextView ed_board_year;
    @BindView(R.id.ed_board_month) TextView ed_board_month;
    @BindView(R.id.ed_board_day) TextView ed_board_day;
    @BindView(R.id.btn_birth_company) Button btn_birth_company;
    @BindView(R.id.txt_b_visible_ymd) TextView txt_b_visible_ymd;
    @BindView(R.id.ed_board_hrd_email) TextView ed_board_hrd_email;
    @BindView(R.id.txt_b_visible_hrd_email) TextView txt_b_visible_hrd_email;
    @BindView(R.id.ed_board_hrd_name) TextView ed_board_hrd_name;
    @BindView(R.id.txt_b_visible_hrd_name) TextView txt_b_visible_hrd_name;
    @BindView(R.id.ed_board_hrd_phone1) EditText ed_board_hrd_phone1;
    @BindView(R.id.ed_board_hrd_phone2) EditText ed_board_hrd_phone2;
    @BindView(R.id.ed_board_hrd_phone3) EditText ed_board_hrd_phone3;
    @BindView(R.id.txt_board_hrd_phone) TextView txt_board_hrd_phone;
    @BindView(R.id.btn_board_store) Button btn_board_store;
    Integer year;
    Integer month;
    Integer day;
    Integer year1;
    Integer month1;
    Integer day1;
    Integer tmp_check = 0;
    Uri imgUri[] = new Uri[10];
    Long id = 0l;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        ButterKnife.bind(this);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        Calendar cal1 = Calendar.getInstance();
        year1 = cal1.get(Calendar.YEAR);
        month1 = cal1.get(Calendar.MONTH);
        day1 = cal1.get(Calendar.DAY_OF_MONTH);

        refresh();

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rd_btn1:
                        tmp_check = 0;
                        break;
                    case R.id.rd_btn2:
                        tmp_check = 1;
                        break;
                }
                Log.d("hjh", "checkedId=" + checkedId);
                Log.d("hjh", "tmp_check=" + tmp_check);
            }
        });


        Call<Board_Write> observ = RetrofitService.getInstance().getRetrofitRequest().m_board_write_get(loginService.getLoginMember().getId());
        observ.enqueue(new Callback<Board_Write>() {
            @Override
            public void onResponse(Call<Board_Write> call, Response<Board_Write> response) {
                if (response.isSuccessful()) {
                    Board_Write result = response.body();

                    ed_board_name.setText(result.getCmember().getCompany_name());
                    ed_board_kind.setText(result.getCdetail().getCompany_kind());
                    ed_board_mainbusiness.setText(result.getCdetail().getCompany_mainbusiness());
                    ed_board_insurance.setText(result.getCdetail().getCompany_insurance());
                    String addressArr[] = result.getCdetail().getCompany_address().split("\\$");
                    Log.d("hjh", "address1=" + addressArr[0]);
                    Log.d("hjh", "address2=" + addressArr[1]);
                    Log.d("hjh", "address3=" + addressArr[2]);
                    txt_board_address0.setText(addressArr[0].toString());
                    txt_board_address1.setText(addressArr[1].toString());
                    ed_board_address2.setText(addressArr[2].toString());
                    ed_board_moneysize.setText(result.getCdetail().getCompany_moneysize());
                    ed_board_membersize.setText(result.getCdetail().getCompany_membersize());
                    ed_board_year.setText(result.getCdetail().getCompany_year());
                    ed_board_month.setText(result.getCdetail().getCompany_month());
                    ed_board_day.setText(result.getCdetail().getCompany_day());
                    ed_board_hrd_email.setText(result.getCdetail().getCompany_hrd_email());
                    ed_board_hrd_name.setText(result.getCdetail().getCompany_hrd_name());
                    String phoneArr[] = result.getCdetail().getCompany_hrd_phone().split("-");

                    ed_board_hrd_phone1.setText(phoneArr[0]);
                    ed_board_hrd_phone2.setText(phoneArr[1]);
                    ed_board_hrd_phone3.setText(phoneArr[2]);

                    GlideApp.with(BoardActivity.this).load(ServerInfo.url + result.getCdetail_File().getSavename())
                            .centerCrop()
                            .into(img_board_main);
                }
            }

            @Override
            public void onFailure(Call<Board_Write> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.btn_birth_company)
    void findCal(View view) {
        /*Calendar c = Calendar.getInstance();
        int cyear = year1;
        int cmonth = month1;
        int cday = day1;

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int tmp_year, int tmp_month, int tmp_day) {
                year1 = tmp_year;
                month1 = tmp_month;
                day1 = tmp_day;

                refresh();
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(BoardActivity.this,dateSetListener,cyear,cmonth,cday);
        //상단의 cyear,cmonth,cday가 달력이 열릴 때 처음 지칭해주는 날짜로 현재의 코드 상태로는
        //현재날짜만 되며 날짜를 이동후에 달력을 열어도 오늘의 날이 선택된 채로 나오게 되며,
        //필요 시 해당 변수의 값이 오늘 날로 오게 코딩해주게되면 선택되어있는 날이 선택된 채로 열리게 할 수 있다.
        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();*/
    }

    @OnClick(R.id.btn_board_find_address)
    void findAddress(View view) {
        Intent intent = new Intent(BoardActivity.this, AddressPopupActivity.class);
        startActivityForResult(intent, 10);
    }

    @OnClick(R.id.btn_end_cal)
    void btn_calendar(View view) {
        Calendar c = Calendar.getInstance();
        int cyear = year;
        int cmonth = month;
        int cday = day;

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int tmp_year, int tmp_month, int tmp_day) {
                year = tmp_year;
                month = tmp_month;
                day = tmp_day;

                refresh();
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(BoardActivity.this,dateSetListener,cyear,cmonth,cday);
        //상단의 cyear,cmonth,cday가 달력이 열릴 때 처음 지칭해주는 날짜로 현재의 코드 상태로는
        //현재날짜만 되며 날짜를 이동후에 달력을 열어도 오늘의 날이 선택된 채로 나오게 되며,
        //필요 시 해당 변수의 값이 오늘 날로 오게 코딩해주게되면 선택되어있는 날이 선택된 채로 열리게 할 수 있다.
        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();
    }

    @OnClick(R.id.btn_img1)
    void onImg1(View view) {
        permissioncheck(0);
    }

    @OnClick(R.id.btn_img2)
    void onImg2(View view) {
        permissioncheck(1);
    }

    @OnClick(R.id.btn_img3)
    void onImg3(View view) {
        permissioncheck(2);
    }

    @OnClick(R.id.btn_img4)
    void onImg4(View view) {
        permissioncheck(3);
    }

    @OnClick(R.id.btn_img5)
    void onImg5(View view) {
        permissioncheck(4);
    }

    @OnClick(R.id.btn_img6)
    void onImg6(View view) {
        permissioncheck(5);
    }

    @OnClick(R.id.btn_img7)
    void onImg7(View view) {
        permissioncheck(6);
    }

    @OnClick(R.id.btn_img8)
    void onImg8(View view) {
        permissioncheck(7);
    }

    @OnClick(R.id.btn_img9)
    void onImg9(View view) {
        permissioncheck(8);
    }

    @OnClick(R.id.btn_img10)
    void onImg10(View view) {
        permissioncheck(9);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                imgUri[0] = data.getData();
                GlideApp.with(BoardActivity.this).load(imgUri[0]).centerCrop().into(img_file1);
            } else if(requestCode == 1) {
                imgUri[1] = data.getData();
                GlideApp.with(BoardActivity.this).load(imgUri[1]).centerCrop().into(img_file2);
            } else if(requestCode == 2) {
                imgUri[2] = data.getData();
                GlideApp.with(BoardActivity.this).load(imgUri[2]).centerCrop().into(img_file3);
            } else if(requestCode == 3) {
                imgUri[3] = data.getData();
                GlideApp.with(BoardActivity.this).load(imgUri[3]).centerCrop().into(img_file4);
            } else if(requestCode == 4) {
                imgUri[4] = data.getData();
                GlideApp.with(BoardActivity.this).load(imgUri[4]).centerCrop().into(img_file5);
            } else if(requestCode == 5) {
                imgUri[5] = data.getData();
                GlideApp.with(BoardActivity.this).load(imgUri[5]).centerCrop().into(img_file6);
            } else if(requestCode == 6) {
                imgUri[6] = data.getData();
                GlideApp.with(BoardActivity.this).load(imgUri[6]).centerCrop().into(img_file7);
            } else if(requestCode == 7) {
                imgUri[7] = data.getData();
                GlideApp.with(BoardActivity.this).load(imgUri[7]).centerCrop().into(img_file8);
            } else if(requestCode == 8) {
                imgUri[8] = data.getData();
                GlideApp.with(BoardActivity.this).load(imgUri[8]).centerCrop().into(img_file9);
            } else if(requestCode == 9) {
                imgUri[9] = data.getData();
                GlideApp.with(BoardActivity.this).load(imgUri[9]).centerCrop().into(img_file10);
            } else if(requestCode == 10) {
                String tmpAddress = data.getStringExtra("addr");
                if(tmpAddress != null) {
                    String addressArr[] = tmpAddress.split("\\(");
                    String tmpAddress0 = addressArr[0];
                    String tmpAddress1 = addressArr[1];
                    String address1Arr[] = tmpAddress1.split("\\) ");
                    String address0 = address1Arr[0];
                    String address1 = address1Arr[1];

                    txt_board_address0.setText(address0);
                    txt_board_address1.setText(address1);
                }
            }
        }
    }

    @OnClick(R.id.btn_board_store)
    void onBoardStore(View view) {
        String c_formupdate[] = new String[32];

        c_formupdate[0] = ed_board_title.getText().toString();
        c_formupdate[1] = ed_board_jobkind.getText().toString();
        c_formupdate[2] = ed_board_career.getText().toString();
        c_formupdate[3] = ed_board_learn.getText().toString();
        c_formupdate[4] = ed_board_essential.getText().toString();
        c_formupdate[5] = ed_board_bonus.getText().toString();
        c_formupdate[6]  = ed_board_membermax.getText().toString();
        c_formupdate[7]  = ed_board_jobplace.getText().toString();
        c_formupdate[8]  = ed_board_jobday.getText().toString();
        c_formupdate[9]  = ed_board_jobtime.getText().toString();
        c_formupdate[10]  = ed_board_money.getText().toString();
        c_formupdate[11]  = ed_board_step.getText().toString();
        c_formupdate[12]  = ed_board_document.getText().toString();
        c_formupdate[13]  = ed_board_end_year.getText().toString();
        c_formupdate[14]  = ed_board_end_month.getText().toString();
        c_formupdate[15]  = ed_board_end_day.getText().toString();
        c_formupdate[16]  = ed_board_content.getText().toString();
        c_formupdate[17]  = ed_board_name.getText().toString();
        c_formupdate[18]  = ed_board_kind.getText().toString();
        c_formupdate[19]  = ed_board_mainbusiness.getText().toString();
        c_formupdate[20]  = ed_board_insurance.getText().toString();
        String board_address0  = txt_board_address0.getText().toString();
        String board_address1  = txt_board_address1.getText().toString();
        String board_address2  = ed_board_address2.getText().toString();
        c_formupdate[21] = board_address0 + "$" + board_address1 + "$" + board_address2;
        c_formupdate[22]  = ed_board_moneysize.getText().toString();
        c_formupdate[23]  = ed_board_membersize.getText().toString();
        c_formupdate[24]  = ed_board_year.getText().toString();
        c_formupdate[25]  = ed_board_month.getText().toString();
        c_formupdate[26]  = ed_board_day.getText().toString();
        c_formupdate[27]  = ed_board_hrd_email.getText().toString();
        c_formupdate[28]  = ed_board_hrd_name.getText().toString();
        String board_hrd_phone1  = ed_board_hrd_phone1.getText().toString();
        String board_hrd_phone2  = ed_board_hrd_phone2.getText().toString();
        String board_hrd_phone3  = ed_board_hrd_phone3.getText().toString();
        c_formupdate[29] = board_hrd_phone1 + "-" + board_hrd_phone2 + "-" + board_hrd_phone3;
        c_formupdate[30] = loginService.getLoginMember().getId().toString();
        if (tmp_check == 0) {
            c_formupdate[31] = rd_btn1.getText().toString();
        }
        else if (tmp_check == 1) {
            c_formupdate[31] = rd_btn2.getText().toString();
        }

        String board_title = ed_board_title.getText().toString();
        String board_jobkind = ed_board_jobkind.getText().toString();
        String board_career = ed_board_career.getText().toString();
        String board_learn = ed_board_learn.getText().toString();
        String board_membermax  = ed_board_membermax.getText().toString();
        String board_jobplace  = ed_board_jobplace.getText().toString();
        String board_jobday  = ed_board_jobday.getText().toString();
        String board_jobtime  = ed_board_jobtime.getText().toString();
        String board_money  = ed_board_money.getText().toString();
        String board_step  = ed_board_step.getText().toString();
        String board_end_year  = ed_board_end_year.getText().toString();
        String board_end_month  = ed_board_end_month.getText().toString();
        String board_end_day  = ed_board_end_day.getText().toString();
        String board_content  = ed_board_content.getText().toString();
        String board_name  = ed_board_name.getText().toString();
        String board_kind  = ed_board_kind.getText().toString();
        String board_mainbusiness  = ed_board_mainbusiness.getText().toString();
        String board_insurance  = ed_board_insurance.getText().toString();
        String board_moneysize  = ed_board_moneysize.getText().toString();
        String board_membersize  = ed_board_membersize.getText().toString();
        String board_year  = ed_board_year.getText().toString();
        String board_month  = ed_board_month.getText().toString();
        String board_day  = ed_board_day.getText().toString();
        String board_hrd_email  = ed_board_hrd_email.getText().toString();
        String board_hrd_name  = ed_board_hrd_name.getText().toString();


        ArrayList<File> file = new ArrayList<File>();
        String origname = "";
        boolean isimgUri = false;
        ArrayList<Integer> filecount = new ArrayList<>();

        for (int i = 0; i < imgUri.length; i++) {
            Log.d("hjh", "i=" + i);
            if (imgUri[i] != null) {
                Log.d("hjh", "imgUri[i]=" + imgUri[i]);
                File tmp = new File(
                        RealPathUtil.getRealPath(BoardActivity.this,
                                imgUri[i]));
                file.add(tmp);
                if (file.get(i).exists()) {
                    Log.d("hjh", "exist");
                }
            }
        }

        ArrayList<MultipartBody.Part> filePart = new ArrayList<>();
        for(int i=0; i < file.size(); i++) {
            filePart.add(MultipartBody.Part.createFormData("file_"+i,
                    file.get(i).getName(),
                    RequestBody.create(MediaType.parse("image/*"), file.get(i))));
        }


        RequestBody c_formcontent[] = new RequestBody[c_formupdate.length];
        for (int i = 0; i < c_formupdate.length; i++) {
            c_formcontent[i] = RequestBody.create(MediaType.parse("text/plain"),
                    c_formupdate[i]);
        }

        if(board_title.equals("")) {
            txt_b_visible_title.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_title.setVisibility(View.GONE);
        }

        if(board_jobkind.equals("")) {
            txt_b_visible_jobkind.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_jobkind.setVisibility(View.GONE);
        }

        if(!rd_btn1.isChecked() && !rd_btn2.isChecked()) {
            txt_b_visible_new.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_new.setVisibility(View.GONE);
        }

        if(board_career.equals("")) {
            txt_b_visible_career.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_career.setVisibility(View.GONE);
        }

        if(board_learn.equals("")) {
            txt_b_visible_learn.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_learn.setVisibility(View.GONE);
        }

        if(board_membermax.equals("")) {
            txt_b_visible_membermax.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_membermax.setVisibility(View.GONE);
        }

        if(board_jobplace.equals("")) {
            txt_b_visible_jobplace.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_jobplace.setVisibility(View.GONE);
        }

        if(board_jobday.equals("")) {
            txt_b_visible_jobday.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_jobday.setVisibility(View.GONE);
        }

        if(board_jobtime.equals("")) {
            txt_b_visible_jobtime.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_jobtime.setVisibility(View.GONE);
        }

        if(board_money.equals("")) {
            txt_b_visible_money.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_money.setVisibility(View.GONE);
        }

        if(board_step.equals("")) {
            txt_b_visible_step.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_step.setVisibility(View.GONE);
        }

        if(board_end_year.equals("") || board_end_month.equals("") || board_end_day.equals("")) {
            txt_board_end_ymd.setVisibility(View.VISIBLE);
        } else {
            txt_board_end_ymd.setVisibility(View.GONE);
        }

        if(board_content.equals("")) {
            txt_b_visible_content.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_content.setVisibility(View.GONE);
        }

        if(board_name.equals("")) {
            txt_b_visible_name.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_name.setVisibility(View.GONE);
        }

        if(board_kind.equals("")) {
            txt_b_visible_kind.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_kind.setVisibility(View.GONE);
        }

        if(board_mainbusiness.equals("")) {
            txt_b_visible_mainbusiness.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_mainbusiness.setVisibility(View.GONE);
        }

        if(board_insurance.equals("")) {
            txt_b_visible_insurance.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_insurance.setVisibility(View.GONE);
        }

        if(board_address0.equals("") || board_address1.equals("") || board_address2.equals("")) {
            txt_b_visible_address.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_address.setVisibility(View.GONE);
        }

        if(board_moneysize.equals("")) {
            txt_b_visible_moneysize.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_moneysize.setVisibility(View.GONE);
        }

        if(board_membersize.equals("")) {
            txt_b_visible_membersize.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_membersize.setVisibility(View.GONE);
        }

        if(board_year.equals("") || board_month.equals("") || board_day.equals("")) {
            txt_b_visible_ymd.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_ymd.setVisibility(View.GONE);
        }

        if(board_hrd_email.equals("")) {
            txt_b_visible_hrd_email.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_hrd_email.setVisibility(View.GONE);
        }

        if(board_hrd_name.equals("")) {
            txt_b_visible_hrd_name.setVisibility(View.VISIBLE);
        } else {
            txt_b_visible_hrd_name.setVisibility(View.GONE);
        }

        if(board_hrd_phone1.equals("") || board_hrd_phone2.equals("") || board_hrd_phone3.equals("")) {
            txt_board_hrd_phone.setVisibility(View.VISIBLE);
        } else {
            txt_board_hrd_phone.setVisibility(View.GONE);
        }


        if(txt_b_visible_title.getVisibility() == View.VISIBLE ||  txt_b_visible_jobkind.getVisibility() == View.VISIBLE || txt_b_visible_career.getVisibility() == View.VISIBLE ||
                txt_b_visible_learn.getVisibility() == View.VISIBLE || txt_b_visible_membermax.getVisibility() == View.VISIBLE ||  txt_b_visible_jobplace.getVisibility() == View.VISIBLE ||  txt_b_visible_jobday.getVisibility() == View.VISIBLE ||
                txt_b_visible_jobtime.getVisibility() == View.VISIBLE || txt_b_visible_money.getVisibility() == View.VISIBLE || txt_b_visible_step.getVisibility() == View.VISIBLE ||
                txt_board_end_ymd.getVisibility() == View.VISIBLE || txt_b_visible_name.getVisibility() == View.VISIBLE ||
                txt_b_visible_kind.getVisibility() == View.VISIBLE || txt_b_visible_mainbusiness.getVisibility() == View.VISIBLE || txt_b_visible_insurance.getVisibility() == View.VISIBLE ||
                txt_b_visible_address.getVisibility() == View.VISIBLE || txt_b_visible_moneysize.getVisibility() == View.VISIBLE || txt_b_visible_membersize.getVisibility() == View.VISIBLE ||
                txt_b_visible_ymd.getVisibility() == View.VISIBLE || txt_b_visible_hrd_email.getVisibility() == View.VISIBLE ||txt_b_visible_hrd_name.getVisibility() == View.VISIBLE ||
                txt_board_hrd_phone.getVisibility() == View.VISIBLE || txt_b_visible_content.getVisibility() == View.VISIBLE) {
            return;
        }
        if (ed_board_essential.getText().equals("")) {
            ed_board_essential.setText("없음");
        }
        if (ed_board_bonus.getText().equals("")) {
            ed_board_bonus.setText("없음");
        }
        if (ed_board_document.getText().equals("")) {
            ed_board_document.setText("없음");
        }

        Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().m_board_write_ok(c_formcontent[30], c_formcontent[0], c_formcontent[1], c_formcontent[31], c_formcontent[2],
                c_formcontent[3], c_formcontent[4], c_formcontent[5], c_formcontent[6], c_formcontent[7], c_formcontent[8], c_formcontent[9], c_formcontent[10],
                c_formcontent[11], c_formcontent[12], c_formcontent[13], c_formcontent[14], c_formcontent[15], c_formcontent[16], c_formcontent[17],
                c_formcontent[18], c_formcontent[19], c_formcontent[20], c_formcontent[21], c_formcontent[22], c_formcontent[23], c_formcontent[24],
                c_formcontent[25], c_formcontent[26], c_formcontent[27], c_formcontent[28], c_formcontent[29], filePart);
        observ.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    AlertDialog.Builder ad=new AlertDialog.Builder(BoardActivity.this);
                    ad.setTitle("공고문 작성 완료").setMessage("공고문 작성이 완료되었습니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).create().show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    public void refresh() {
        ed_board_end_year.setText(year.toString());
        ed_board_end_month.setText("" + (month+1));
        ed_board_end_day.setText(day.toString());

        ed_board_year.setText(year1.toString());
        ed_board_month.setText("" + (month1+1));
        ed_board_day.setText(day1.toString());
    }

    public void permissioncheck(final int position) {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), position);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };

        TedPermission.with(BoardActivity.this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }
}
