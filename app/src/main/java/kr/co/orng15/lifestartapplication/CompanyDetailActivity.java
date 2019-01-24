package kr.co.orng15.lifestartapplication;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.data.Company_Detail;
import kr.co.orng15.lifestartapplication.data.Company_Detail_Use;
import kr.co.orng15.lifestartapplication.data.Member;
import kr.co.orng15.lifestartapplication.data.Member_Result;
import kr.co.orng15.lifestartapplication.data.Mresult;
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

public class CompanyDetailActivity extends AppCompatActivity {
    LoginService loginService = LoginService.getInstance();

    @BindView(R.id.btn_com_photo_find) Button btn_com_photo_find;
    @BindView(R.id.img_com_photo) ImageView img_com_photo;
    @BindView(R.id.txt_c_visible_photo) TextView txt_c_visible_photo;
    @BindView(R.id.ed_com_kind) EditText ed_com_kind;
    @BindView(R.id.txt_visible_com_kind) TextView txt_visible_com_kind;
    @BindView(R.id.ed_com_main) EditText ed_com_main;
    @BindView(R.id.txt_visible_com_main) TextView txt_visible_com_main;
    @BindView(R.id.ed_com_ins) EditText ed_com_ins;
    @BindView(R.id.txt_visible_com_ins) TextView txt_visible_com_ins;
    @BindView(R.id.btn_find_address) Button btn_find_address;
    @BindView(R.id.ed_com_address0) TextView ed_com_address0;
    @BindView(R.id.ed_com_address1) TextView ed_com_address1;
    @BindView(R.id.ed_com_address2) EditText ed_com_address2;
    @BindView(R.id.txt_visible_com_address) TextView txt_visible_com_address;
    @BindView(R.id.ed_com_money) EditText ed_com_money;
    @BindView(R.id.txt_visible_com_money) TextView txt_visible_com_money;
    @BindView(R.id.ed_com_member) EditText ed_com_member;
    @BindView(R.id.txt_visible_com_member) TextView txt_visible_com_member;
    @BindView(R.id.ed_com_year) TextView ed_com_year;
    @BindView(R.id.ed_com_month) TextView ed_com_month;
    @BindView(R.id.ed_com_day) TextView ed_com_day;
    @BindView(R.id.txt_visible_com_ymd) TextView txt_visible_com_ymd;
    @BindView(R.id.ed_com_hrd_email) TextView ed_com_hrd_email;
    @BindView(R.id.txt_visible_com_email) TextView txt_visible_com_email;
    @BindView(R.id.ed_com_hrd_name) EditText ed_com_hrd_name;
    @BindView(R.id.txt_visible_com_name) TextView txt_visible_com_name;
    @BindView(R.id.ed_com_hrd_phone1) EditText ed_com_hrd_phone1;
    @BindView(R.id.ed_com_hrd_phone2) EditText ed_com_hrd_phone2;
    @BindView(R.id.ed_com_hrd_phone3) EditText ed_com_hrd_phone3;
    @BindView(R.id.txt_visible_hrd_phone) TextView txt_visible_hrd_phone;
    @BindView(R.id.btn_com_detail_store) Button btn_com_detail_store;
    @BindView(R.id.btn_birth_com) Button btn_birth_com;
    @BindView(R.id.btn_c_email) Button btn_c_email;
    Integer year;
    Integer month;
    Integer day;
    Long id = 0l;
    Uri imgUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        ButterKnife.bind(this);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        refresh();

        if(loginService.getLoginMember().getMember_history() >= 2) {
            Call<Company_Detail_Use> observ = RetrofitService.getInstance().getRetrofitRequest().m_company_detail_get(loginService.getLoginMember().getId());
            observ.enqueue(new Callback<Company_Detail_Use>() {
                @Override
                public void onResponse(Call<Company_Detail_Use> call, Response<Company_Detail_Use> response) {
                    if (response.isSuccessful()) {
                        Company_Detail_Use result = response.body();
                        ed_com_kind.setText(result.getCdetail().getCompany_kind());
                        ed_com_main.setText(result.getCdetail().getCompany_mainbusiness());
                        ed_com_ins.setText(result.getCdetail().getCompany_insurance());
                        String addressArr[] = result.getCdetail().getCompany_address().split("\\$");
                        ed_com_address0.setText(addressArr[0]);
                        ed_com_address1.setText(addressArr[1]);
                        ed_com_address2.setText(addressArr[2]);
                        ed_com_money.setText(result.getCdetail().getCompany_moneysize());
                        ed_com_member.setText(result.getCdetail().getCompany_membersize());
                        ed_com_year.setText(result.getCdetail().getCompany_year());
                        ed_com_month.setText(result.getCdetail().getCompany_month());
                        ed_com_day.setText(result.getCdetail().getCompany_day());
                        ed_com_hrd_email.setText(result.getCdetail().getCompany_hrd_email());
                        ed_com_hrd_name.setText(result.getCdetail().getCompany_hrd_name());
                        String phoneArr[] = result.getCdetail().getCompany_hrd_phone().split("-");
                        ed_com_hrd_phone1.setText(phoneArr[0]);
                        ed_com_hrd_phone2.setText(phoneArr[1]);
                        ed_com_hrd_phone3.setText(phoneArr[2]);

                        GlideApp.with(CompanyDetailActivity.this).load(ServerInfo.url + result.getCdetail_File().getSavename())
                                .centerCrop()
                                .into(img_com_photo);
                    }
                }

                @Override
                public void onFailure(Call<Company_Detail_Use> call, Throwable t) {

                }
            });
        }
    }

    @OnClick(R.id.btn_c_email)
    void callEmail(View view) {
        Intent intent = new Intent(CompanyDetailActivity.this, EmailPopupActivity.class);
        startActivityForResult(intent, 2);
    }

    @OnClick(R.id.btn_birth_com)
    void findCal(View view) {
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

        DatePickerDialog dialog = new DatePickerDialog(CompanyDetailActivity.this,dateSetListener,cyear,cmonth,cday);
        dialog.show();
    }

    @OnClick(R.id.btn_find_address)
    void findAddress(View view) {
        Intent intent = new Intent(CompanyDetailActivity.this, AddressPopupActivity.class);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.btn_com_photo_find)
    void callImg(View view) {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), 0);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };

        TedPermission.with(CompanyDetailActivity.this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                imgUri = data.getData();
                GlideApp.with(CompanyDetailActivity.this).load(imgUri).centerCrop().into(img_com_photo);
            } else if(requestCode == 1) {
                String tmpAddress = data.getStringExtra("addr");
                if(tmpAddress != null) {
                    String addressArr[] = tmpAddress.split("\\(");
                    String tmpAddress0 = addressArr[0];
                    String tmpAddress1 = addressArr[1];
                    String address1Arr[] = tmpAddress1.split("\\) ");
                    String address0 = address1Arr[0];
                    String address1 = address1Arr[1];

                    ed_com_address0.setText(address0);
                    ed_com_address1.setText(address1);
                }
            } else if(requestCode == 2) {
                String tmpEmail = data.getStringExtra("email");
                ed_com_hrd_email.setText(tmpEmail);
            }
        }
    }

    @OnClick(R.id.btn_com_detail_store)
    void storeDetailCom(View view) {
        String c_formupdate[] = new String[13];

        c_formupdate[0] = ed_com_kind.getText().toString();
        c_formupdate[1]  = ed_com_main.getText().toString();
        c_formupdate[2] = ed_com_ins.getText().toString();
        String address0 = ed_com_address0.getText().toString();
        String address1 = ed_com_address1.getText().toString();
        String address2 = ed_com_address2.getText().toString();
        c_formupdate[3] = address0 + "$" + address1 + "$" + address2;
        c_formupdate[4] = ed_com_money.getText().toString();
        c_formupdate[5] = ed_com_member.getText().toString();
        c_formupdate[6] = ed_com_year.getText().toString();
        c_formupdate[7] = ed_com_month.getText().toString();
        c_formupdate[8] = ed_com_day.getText().toString();
        c_formupdate[9] = ed_com_hrd_email.getText().toString();
        c_formupdate[10] = ed_com_hrd_name.getText().toString();
        String phone1 = ed_com_hrd_phone1.getText().toString();
        String phone2 = ed_com_hrd_phone2.getText().toString();
        String phone3 = ed_com_hrd_phone3.getText().toString();
        c_formupdate[11] = phone1 +"-" + phone2 + "-" + phone3;
        c_formupdate[12] = loginService.getLoginMember().getId().toString();

        String company_kind = ed_com_kind.getText().toString();
        String company_mainbusiness  = ed_com_main.getText().toString();
        String company_insurance = ed_com_ins.getText().toString();
        String company_moneysize = ed_com_money.getText().toString();
        String company_membersize = ed_com_member.getText().toString();
        String company_year = ed_com_year.getText().toString();
        String company_month = ed_com_month.getText().toString();
        String company_day = ed_com_day.getText().toString();
        String company_hrd_email = ed_com_hrd_email.getText().toString();
        String company_hrd_name = ed_com_hrd_name.getText().toString();

        File file = null;
        String origname = "";
        MultipartBody.Part filePart = null;
        if (imgUri != null) {
            file = new File(
                    RealPathUtil.getRealPath(CompanyDetailActivity.this,
                            imgUri));
            if (file.exists()) {
                Log.d("hjh", "exist");
            }
            filePart =
                    MultipartBody.Part.createFormData("file",
                            file.getName(),
                            RequestBody.create(MediaType.parse("image/*"), file));

            origname = filePart.toString();
        }
        RequestBody c_formcontent[] = new RequestBody[c_formupdate.length];
        for (int i = 0; i < c_formupdate.length; i++) {
            c_formcontent[i] = RequestBody.create(MediaType.parse("text/plain"),
                    c_formupdate[i]);
        }

        if (imgUri != null) {

        }
        if (id != 0) {

        }
        else {
            if (origname.equals("")) {
                txt_c_visible_photo.setVisibility(View.VISIBLE);
            } else {
                txt_c_visible_photo.setVisibility(View.GONE);
            }
        }

        if(company_kind.equals("")) {
            txt_visible_com_kind.setVisibility(View.VISIBLE);
        } else {
            txt_visible_com_kind.setVisibility(View.GONE);
        }

        if(company_mainbusiness.equals("")) {
            txt_visible_com_main.setVisibility(View.VISIBLE);
        } else {
            txt_visible_com_main.setVisibility(View.GONE);
        }

        if(company_insurance.equals("")) {
            txt_visible_com_ins.setVisibility(View.VISIBLE);
        } else {
            txt_visible_com_ins.setVisibility(View.GONE);
        }

        if(address0.equals("") || address1.equals("") || address2.equals("")) {
            txt_visible_com_address.setVisibility(View.VISIBLE);
        } else {
            txt_visible_com_address.setVisibility(View.GONE);
        }

        if(company_moneysize.equals("")) {
            txt_visible_com_money.setVisibility(View.VISIBLE);
        } else {
            txt_visible_com_money.setVisibility(View.GONE);
        }

        if(company_membersize.equals("")) {
            txt_visible_com_member.setVisibility(View.VISIBLE);
        } else {
            txt_visible_com_member.setVisibility(View.GONE);
        }

        if(company_year.equals("") || company_month.equals("") || company_day.equals("")) {
            txt_visible_com_ymd.setVisibility(View.VISIBLE);
        } else {
            txt_visible_com_ymd.setVisibility(View.GONE);
        }

        if(company_hrd_email.equals("")) {
            txt_visible_com_email.setVisibility(View.VISIBLE);
        } else {
            txt_visible_com_email.setVisibility(View.GONE);
        }

        if(company_hrd_name.equals("")) {
            txt_visible_com_name.setVisibility(View.VISIBLE);
        } else {
            txt_visible_com_name.setVisibility(View.GONE);
        }

        if(phone1.equals("") || phone2.equals("") || phone3.equals("")) {
            txt_visible_hrd_phone.setVisibility(View.VISIBLE);
        } else {
            txt_visible_hrd_phone.setVisibility(View.GONE);
        }

        if (txt_visible_com_kind.getVisibility() == View.VISIBLE || txt_visible_com_main.getVisibility() == View.VISIBLE || txt_visible_com_ins.getVisibility() == View.VISIBLE
                || txt_visible_com_address.getVisibility() == View.VISIBLE || txt_visible_com_money.getVisibility() == View.VISIBLE || txt_visible_com_member.getVisibility() == View.VISIBLE
                || txt_visible_com_ymd.getVisibility() == View.VISIBLE || txt_visible_com_email.getVisibility() == View.VISIBLE || txt_visible_com_name.getVisibility() == View.VISIBLE
                || txt_visible_hrd_phone.getVisibility() == View.VISIBLE || txt_c_visible_photo.getVisibility() == View.VISIBLE) {
            return ;
        }

        Call<Member_Result> observ = RetrofitService.getInstance().getRetrofitRequest().m_company_detail_ok(c_formcontent[12], c_formcontent[0], c_formcontent[1], c_formcontent[2], c_formcontent[3]
                , c_formcontent[4], c_formcontent[5], c_formcontent[6], c_formcontent[7], c_formcontent[8], c_formcontent[9], c_formcontent[10], c_formcontent[11], filePart);
        observ.enqueue(new Callback<Member_Result>() {
            @Override
            public void onResponse(Call<Member_Result> call, Response<Member_Result> response) {
                if (response.isSuccessful()) {
                    Member_Result result = response.body();
                    if(result.getResult() == 0) {
                        Member login_member = new Member(result.getId(), result.getMember_id(), result.getMember_pw(), result.getMember_kind(), result.getMember_history(), result.getMember_history_date(), result.getMember_status(), result.getMember_status_date());
                        loginService.setLoginId(result.getMember_id());
                        loginService.setLoginPw(result.getMember_pw());
                        loginService.setLoginMember(login_member);
                        AlertDialog.Builder ad=new AlertDialog.Builder(CompanyDetailActivity.this);
                        ad.setTitle("정보 저장 완료").setMessage("정보 저장이 완료되었습니다.").setNeutralButton("확인",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Member_Result> call, Throwable t) {

            }
        });
    }

    public void refresh() {
        ed_com_year.setText(year.toString());
        ed_com_month.setText("" + (month+1));
        ed_com_day.setText(day.toString());
    }
}
