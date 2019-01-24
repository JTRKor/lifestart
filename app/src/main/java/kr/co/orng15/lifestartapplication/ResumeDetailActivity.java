package kr.co.orng15.lifestartapplication;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.orng15.lifestartapplication.data.Member;
import kr.co.orng15.lifestartapplication.data.Member_Result;
import kr.co.orng15.lifestartapplication.data.Personal_Detail;
import kr.co.orng15.lifestartapplication.data.Personal_Detail_Resume_Use;
import kr.co.orng15.lifestartapplication.data.Personal_Member;
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

public class ResumeDetailActivity extends AppCompatActivity {
    LoginService loginService = LoginService.getInstance();
    @BindView(R.id.ed_r_detail_title) EditText ed_r_detail_title;
    @BindView(R.id.txt_r_visible_title) TextView txt_r_visible_title;
    @BindView(R.id.btn_photo_find) Button btn_photo_find;
    @BindView(R.id.img_photo) ImageView img_photo;
    @BindView(R.id.txt_r_visible_photo) TextView txt_r_visible_photo;
    @BindView(R.id.ed_r_detail_name) EditText ed_r_detail_name;
    @BindView(R.id.txt_r_visible_name) TextView txt_r_visible_name;
    @BindView(R.id.ed_r_detail_y) TextView ed_r_detail_y;
    @BindView(R.id.ed_r_detail_m) TextView ed_r_detail_m;
    @BindView(R.id.ed_r_detail_d) TextView ed_r_detail_d;
    @BindView(R.id.txt_r_visible_ymd) TextView txt_r_visible_ymd;
    @BindView(R.id.ed_r_detail_p1) Spinner ed_r_detail_p1;
    @BindView(R.id.ed_r_detail_p2) EditText ed_r_detail_p2;
    @BindView(R.id.ed_r_detail_p3) EditText ed_r_detail_p3;
    @BindView(R.id.txt_r_visible_phone) TextView txt_r_visible_phone;
    @BindView(R.id.btn_r_detail_find_address) Button btn_r_detail_find_address;
    @BindView(R.id.txt_r_detail_address0) TextView txt_r_detail_address0;
    @BindView(R.id.txt_r_detail_address1) TextView txt_r_detail_address1;
    @BindView(R.id.ed_r_detail_address2) EditText ed_r_detail_address2;
    @BindView(R.id.txt_r_visible_address) TextView txt_r_visible_address;
    @BindView(R.id.ed_r_detail_school1) EditText ed_r_detail_school1;
    @BindView(R.id.ed_r_detail_school2) EditText ed_r_detail_school2;
    @BindView(R.id.txt_r_visible_school) TextView txt_r_visible_school;
    @BindView(R.id.ed_r_detail_career_name1) EditText ed_r_detail_career_name1;
    @BindView(R.id.ed_r_detail_career_kind1) EditText ed_r_detail_career_kind1;
    @BindView(R.id.ed_r_detail_career_month1) EditText ed_r_detail_career_month1;
    @BindView(R.id.ed_r_detail_career_story1) EditText ed_r_detail_career_story1;
    @BindView(R.id.txt_r_visible_career1) TextView txt_r_visible_career1;
    @BindView(R.id.ed_r_detail_career_name2)  EditText ed_r_detail_career_name2;
    @BindView(R.id.ed_r_detail_career_kind2) EditText ed_r_detail_career_kind2;
    @BindView(R.id.ed_r_detail_career_month2) EditText ed_r_detail_career_month2;
    @BindView(R.id.ed_r_detail_career_story2) EditText ed_r_detail_career_story2;
    @BindView(R.id.txt_r_visible_career2) TextView txt_r_visible_career2;
    @BindView(R.id.ed_r_detail_career_name3) EditText ed_r_detail_career_name3;
    @BindView(R.id.ed_r_detail_career_kind3) EditText ed_r_detail_career_kind3;
    @BindView(R.id.ed_r_detail_career_month3) EditText ed_r_detail_career_month3;
    @BindView(R.id.ed_r_detail_career_story3) EditText ed_r_detail_career_story3;
    @BindView(R.id.txt_r_visible_career3) TextView txt_r_visible_career3;
    @BindView(R.id.ed_detail_license_name1) EditText ed_detail_license_name1;
    @BindView(R.id.ed_detail_license_name2) EditText ed_detail_license_name2;
    @BindView(R.id.ed_detail_license_name3) EditText ed_detail_license_name3;
    @BindView(R.id.ed_detail_my_resume) EditText ed_detail_my_resume;
    @BindView(R.id.txt_r_visible_resume) TextView txt_r_visible_resume;
    @BindView(R.id.btn_detail_resume_ensure) Button btn_detail_resume_ensure;
    @BindView(R.id.btn_birthday) Button btn_birthday;
    Integer year;
    Integer month;
    Integer day;
    Intent intent;
    Long id = 0l;
    Integer ccount = 0;
    Integer lcount = 0;
    ArrayList<Long> ccountarr = new ArrayList<>();
    ArrayList<Long> lcountarr = new ArrayList<>();

    Uri imgUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_detail);
        ButterKnife.bind(this);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        refresh();

        ArrayAdapter phoneAdapter = ArrayAdapter.createFromResource(this, R.array.phone_first, android.R.layout.simple_spinner_item);
        phoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ed_r_detail_p1.setAdapter(phoneAdapter);

        //서버로 이미지를 전송할 때
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        intent = getIntent();
        id = intent.getLongExtra("id", 0);
        if (id == 0) {
            Call<Personal_Detail> observ = RetrofitService.getInstance().getRetrofitRequest().m_personal_detail_get(loginService.getLoginMember().getId());
            observ.enqueue(new Callback<Personal_Detail>() {
                @Override
                public void onResponse(Call<Personal_Detail> call, Response<Personal_Detail> response) {
                    if (response.isSuccessful()) {
                        Personal_Detail result = response.body();

                        ed_r_detail_y.setText(result.getDetail_birth_year());
                        ed_r_detail_m.setText(result.getDetail_birth_month());
                        ed_r_detail_d.setText(result.getDetail_birth_day());
                        String tmpaddress[] = result.getDetail_address().split("\\$");
                        txt_r_detail_address0.setText(tmpaddress[0]);
                        txt_r_detail_address1.setText(tmpaddress[1]);
                        ed_r_detail_address2.setText(tmpaddress[2]);
                        ed_r_detail_school1.setText(result.getDetail_learn());
                        ed_r_detail_school2.setText(result.getDetail_learn_finish());
                    }
                }

                @Override
                public void onFailure(Call<Personal_Detail> call, Throwable t) {

                }
            });

            Call<Personal_Member> observ2 = RetrofitService.getInstance().getRetrofitRequest().m_p_Info(loginService.getLoginMember().getId());
            observ2.enqueue(new Callback<Personal_Member>() {
                @Override
                public void onResponse(Call<Personal_Member> call, Response<Personal_Member> response) {
                    if (response.isSuccessful()) {
                        Personal_Member result = response.body();

                        ed_r_detail_name.setText(result.getMember_name());
                        String phoneArr[] = result.getMember_phone().split("-");
                        for (int i = 0; i < ed_r_detail_p1.getAdapter().getCount(); i++) {
                            if (ed_r_detail_p1.getAdapter().getItem(i).equals("010")) {
                                ed_r_detail_p1.setSelection(i);
                            } else if (ed_r_detail_p1.getAdapter().getItem(i).equals("011")) {
                                ed_r_detail_p1.setSelection(i);
                            } else if (ed_r_detail_p1.getAdapter().getItem(i).equals("016")) {
                                ed_r_detail_p1.setSelection(i);
                            } else if (ed_r_detail_p1.getAdapter().getItem(i).equals("017")) {
                                ed_r_detail_p1.setSelection(i);
                            }
                        }
                        ed_r_detail_p2.setText(phoneArr[1]);
                        ed_r_detail_p3.setText(phoneArr[2]);
                    }
                }

                @Override
                public void onFailure(Call<Personal_Member> call, Throwable t) {

                }
            });
        } else {
            Call<Personal_Detail_Resume_Use> observ = RetrofitService.getInstance().getRetrofitRequest().m_personal_detail_resume_use_get(id, loginService.getLoginMember().getId());
            observ.enqueue(new Callback<Personal_Detail_Resume_Use>() {
                @Override
                public void onResponse(Call<Personal_Detail_Resume_Use> call, Response<Personal_Detail_Resume_Use> response) {
                    if (response.isSuccessful()) {
                        Personal_Detail_Resume_Use result = response.body();
                        ed_r_detail_title.setText(result.getPdetailResume().getDetail_title().toString());

                        GlideApp.with(ResumeDetailActivity.this).load(ServerInfo.url + result.getPdetailFile().getSavename())
                                .centerCrop()
                                .into(img_photo);

                        ed_r_detail_y.setText(result.getPdetail().getDetail_birth_year());
                        ed_r_detail_m.setText(result.getPdetail().getDetail_birth_month());
                        ed_r_detail_d.setText(result.getPdetail().getDetail_birth_day());

                        String tmpaddress[] = result.getPdetail().getDetail_address().split("\\$");
                        txt_r_detail_address0.setText(tmpaddress[0]);
                        txt_r_detail_address1.setText(tmpaddress[1]);
                        ed_r_detail_address2.setText(tmpaddress[2]);
                        ed_r_detail_school1.setText(result.getPdetail().getDetail_learn());
                        ed_r_detail_school2.setText(result.getPdetail().getDetail_learn_finish());
                        ed_r_detail_name.setText(result.getPMember().getMember_name());

                        String phoneArr[] = result.getPMember().getMember_phone().split("-");
                        for (int i = 0; i < ed_r_detail_p1.getAdapter().getCount(); i++) {
                            if (ed_r_detail_p1.getAdapter().getItem(i).equals("010")) {
                                ed_r_detail_p1.setSelection(i);
                            } else if (ed_r_detail_p1.getAdapter().getItem(i).equals("011")) {
                                ed_r_detail_p1.setSelection(i);
                            } else if (ed_r_detail_p1.getAdapter().getItem(i).equals("017")) {
                                ed_r_detail_p1.setSelection(i);
                            } else if (ed_r_detail_p1.getAdapter().getItem(i).equals("018")) {
                                ed_r_detail_p1.setSelection(i);
                            }
                        }
                        ed_r_detail_p2.setText(phoneArr[1]);
                        ed_r_detail_p3.setText(phoneArr[2]);

                        ccount = result.getPdetailCareer_arr().size();
                        if (ccount > 0) {
                            ed_r_detail_career_name1.setText(result.getPdetailCareer_arr().get(0).getCareer_name());
                            ed_r_detail_career_kind1.setText(result.getPdetailCareer_arr().get(0).getCareer_kind());
                            ed_r_detail_career_month1.setText(result.getPdetailCareer_arr().get(0).getCareer_use_month());
                            ed_r_detail_career_story1.setText(result.getPdetailCareer_arr().get(0).getCareer_story());
                            ccountarr.add(result.getPdetailCareer_arr().get(0).getId());
                            if (ccount > 1) {
                                ed_r_detail_career_name2.setText(result.getPdetailCareer_arr().get(1).getCareer_name());
                                ed_r_detail_career_kind2.setText(result.getPdetailCareer_arr().get(1).getCareer_kind());
                                ed_r_detail_career_month2.setText(result.getPdetailCareer_arr().get(1).getCareer_use_month());
                                ed_r_detail_career_story2.setText(result.getPdetailCareer_arr().get(1).getCareer_story());
                                ccountarr.add(result.getPdetailCareer_arr().get(1).getId());
                                if (ccount > 2) {
                                    ed_r_detail_career_name3.setText(result.getPdetailCareer_arr().get(2).getCareer_name());
                                    ed_r_detail_career_kind3.setText(result.getPdetailCareer_arr().get(2).getCareer_kind());
                                    ed_r_detail_career_month3.setText(result.getPdetailCareer_arr().get(2).getCareer_use_month());
                                    ed_r_detail_career_story3.setText(result.getPdetailCareer_arr().get(2).getCareer_story());
                                    ccountarr.add(result.getPdetailCareer_arr().get(2).getId());
                                }
                            }
                        }
                        lcount = result.getPdetailLicense_arr().size();
                        if (lcount > 0) {
                            ed_detail_license_name1.setText(result.getPdetailLicense_arr().get(0).getDetail_license_name());
                            lcountarr.add(result.getPdetailLicense_arr().get(0).getId());
                            if (lcount > 1) {
                                ed_detail_license_name2.setText(result.getPdetailLicense_arr().get(1).getDetail_license_name());
                                lcountarr.add(result.getPdetailLicense_arr().get(1).getId());
                                if (lcount > 2) {
                                    ed_detail_license_name3.setText(result.getPdetailLicense_arr().get(2).getDetail_license_name());
                                    lcountarr.add(result.getPdetailLicense_arr().get(2).getId());
                                }
                            }
                        }
                        ed_detail_my_resume.setText(result.getPdetailResume().getDetail_myresume());
                    }
                }

                @Override
                public void onFailure(Call<Personal_Detail_Resume_Use> call, Throwable t) {

                }
            });
        }
    }

    @OnClick(R.id.btn_birthday)
    void findBirth(View view) {
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

        DatePickerDialog dialog = new DatePickerDialog(ResumeDetailActivity.this,dateSetListener,cyear,cmonth,cday);
        dialog.show();
    }

    @OnClick(R.id.btn_r_detail_find_address)
    void findAddress(View view) {
        Intent intent = new Intent(ResumeDetailActivity.this, AddressPopupActivity.class);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.btn_photo_find)
    void onPhoto(View view) {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Select picture"), 0);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };

        TedPermission.with(ResumeDetailActivity.this)
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
                GlideApp.with(ResumeDetailActivity.this).load(imgUri).centerCrop().into(img_photo);
            } else  if(requestCode == 1) {
                String tmpAddress = data.getStringExtra("addr");
                if(tmpAddress != null) {
                    String addressArr[] = tmpAddress.split("\\(");
                    String tmpAddress0 = addressArr[0];
                    String tmpAddress1 = addressArr[1];
                    String address1Arr[] = tmpAddress1.split("\\) ");
                    String address0 = address1Arr[0];
                    String address1 = address1Arr[1];

                    txt_r_detail_address0.setText(address0);
                    txt_r_detail_address1.setText(address1);
                }
            }
        }
    }

    @OnClick(R.id.btn_detail_resume_ensure)
    void onResumeEnsure(View view) {
        //Personal_Detail_Resume
        String formupdate[] = new String[28];
        formupdate[0] = ed_r_detail_title.getText().toString();
        /*formupdate[1] = img_path.toString();*/
        formupdate[1] = "";
        formupdate[2] = ed_detail_my_resume.getText().toString();

        formupdate[3] = ed_r_detail_name.getText().toString();
        formupdate[4] = ed_r_detail_y.getText().toString();
        formupdate[5] = ed_r_detail_m.getText().toString();
        formupdate[6] = ed_r_detail_d.getText().toString();
        String detail_phone1 = ed_r_detail_p1.getSelectedItem().toString();
        String detail_phone2 = ed_r_detail_p2.getText().toString();
        String detail_phone3 = ed_r_detail_p3.getText().toString();
        formupdate[7] = detail_phone1 + "-" + detail_phone2 + "-" + detail_phone3;
        String address0 = txt_r_detail_address0.getText().toString();
        String address1 = txt_r_detail_address1.getText().toString();
        String address2 = ed_r_detail_address2.getText().toString();
        formupdate[8] = address0 + "$" + address1 + "$" + address2;
        formupdate[9] = ed_r_detail_school1.getText().toString();
        formupdate[10] = ed_r_detail_school2.getText().toString();

        formupdate[11] = ed_r_detail_career_name1.getText().toString();
        formupdate[12] = ed_r_detail_career_kind1.getText().toString();
        formupdate[13] = ed_r_detail_career_month1.getText().toString();
        formupdate[14] = ed_r_detail_career_story1.getText().toString();
        formupdate[15] = ed_r_detail_career_name2.getText().toString();
        formupdate[16] = ed_r_detail_career_kind2.getText().toString();
        formupdate[17] = ed_r_detail_career_month2.getText().toString();
        formupdate[18] = ed_r_detail_career_story2.getText().toString();
        formupdate[19] = ed_r_detail_career_name3.getText().toString();
        formupdate[20] = ed_r_detail_career_kind3.getText().toString();
        formupdate[21] = ed_r_detail_career_month3.getText().toString();
        formupdate[22] = ed_r_detail_career_story3.getText().toString();

        formupdate[23] = ed_detail_license_name1.getText().toString();
        formupdate[24] = ed_detail_license_name2.getText().toString();
        formupdate[25] = ed_detail_license_name3.getText().toString();

        formupdate[26] = id.toString();
        formupdate[27] = loginService.getLoginMember().getId().toString();

        String detail_title = ed_r_detail_title.getText().toString();

        String detail_myresume = ed_detail_my_resume.getText().toString();

        //이력서
        String detail_name = ed_r_detail_name.getText().toString();
        String detail_birth_year = ed_r_detail_y.getText().toString();
        String detail_birth_month = ed_r_detail_m.getText().toString();
        String detail_birth_day = ed_r_detail_d.getText().toString();
        String detail_learn = ed_r_detail_school1.getText().toString();
        String detail_learn_finish = ed_r_detail_school2.getText().toString();

        //Personal_Detail_career 1,2,3
        String career_name1 = ed_r_detail_career_name1.getText().toString();
        String career_kind1 = ed_r_detail_career_kind1.getText().toString();
        String career_use_month1 = ed_r_detail_career_month1.getText().toString();
        String career_story1 = ed_r_detail_career_story1.getText().toString();
        String career_name2 = ed_r_detail_career_name2.getText().toString();
        String career_kind2 = ed_r_detail_career_kind2.getText().toString();
        String career_use_month2 = ed_r_detail_career_month2.getText().toString();
        String career_story2 = ed_r_detail_career_story2.getText().toString();
        String career_name3 = ed_r_detail_career_name3.getText().toString();
        String career_kind3 = ed_r_detail_career_kind3.getText().toString();
        String career_use_month3 = ed_r_detail_career_month3.getText().toString();
        String career_story3 = ed_r_detail_career_story3.getText().toString();

        //Personal_Detail_license 1,2,3
        String detail_license_name1 = ed_detail_license_name1.getText().toString();
        String detail_license_name2 = ed_detail_license_name2.getText().toString();
        String detail_license_name3 = ed_detail_license_name3.getText().toString();

        File file = null;
        String origname = "";
        MultipartBody.Part filePart = null;
        if (imgUri != null) {
            file = new File(
                    RealPathUtil.getRealPath(ResumeDetailActivity.this,
                            imgUri));
            if (file.exists()) {
            }
            filePart =
                    MultipartBody.Part.createFormData("file",
                            file.getName(),
                            RequestBody.create(MediaType.parse("image/*"), file));

            origname = filePart.toString();
        }
        RequestBody formcontent[] = new RequestBody[formupdate.length];
        for (int i = 0; i < formupdate.length; i++) {
            formcontent[i] = RequestBody.create(MediaType.parse("text/plain"),
                    formupdate[i]);
        }

        if (detail_title.equals("")) {
            txt_r_visible_title.setVisibility(View.VISIBLE);
        } else {
            txt_r_visible_title.setVisibility(View.GONE);
        }

        if (imgUri != null) {

        }
        if (id != 0) {

        }
        else {
            if (origname.equals("")) {
                txt_r_visible_photo.setVisibility(View.VISIBLE);
            } else {
                txt_r_visible_photo.setVisibility(View.GONE);
            }
        }


        if (detail_name.equals("")) {
            txt_r_visible_name.setVisibility(View.VISIBLE);
        } else {
            txt_r_visible_name.setVisibility(View.GONE);
        }

        if (detail_birth_year.equals("") || detail_birth_month.equals("") || detail_birth_day.equals("")) {
            txt_r_visible_ymd.setVisibility(View.VISIBLE);
        } else {
            txt_r_visible_ymd.setVisibility(View.GONE);
        }

        if (detail_phone1.equals("") || detail_phone2.equals("") || detail_phone3.equals("")) {
            txt_r_visible_phone.setVisibility(View.VISIBLE);
        } else {
            txt_r_visible_phone.setVisibility(View.GONE);
        }

        if (address0.equals("") || address1.equals("") || address2.equals("")) {
            txt_r_visible_address.setVisibility(View.VISIBLE);
        } else {
            txt_r_visible_address.setVisibility(View.GONE);
        }

        if (detail_learn.equals("") || detail_learn_finish.equals("")) {
            txt_r_visible_school.setVisibility(View.VISIBLE);
        } else {
            txt_r_visible_school.setVisibility(View.GONE);
        }

        if (detail_myresume.equals("")) {
            txt_r_visible_resume.setVisibility(View.VISIBLE);
        } else {
            txt_r_visible_resume.setVisibility(View.GONE);
        }

        ArrayList<String> career1 = new ArrayList<>();
        if (!career_name1.equals("")) {
            career1.add("1");
        }
        if (!career_kind1.equals("")) {
            career1.add("1");
        }
        if (!career_use_month1.equals("")) {
            career1.add("1");
        }
        if (!career_story1.equals("")) {
            career1.add("1");
        }
        if (career1.size() == 0 || career1.size() == 4) {
            txt_r_visible_career1.setVisibility(View.GONE);
        } else {
            txt_r_visible_career1.setVisibility(View.VISIBLE);
        }

        ArrayList<String> career2 = new ArrayList<>();
        if (!career_name2.equals("")) {
            career2.add("1");
        }
        if (!career_kind2.equals("")) {
            career2.add("1");
        }
        if (!career_use_month2.equals("")) {
            career2.add("1");
        }
        if (!career_story2.equals("")) {
            career2.add("1");
        }
        if (career2.size() == 0 || career2.size() == 4) {
            txt_r_visible_career2.setVisibility(View.GONE);
        } else {
            txt_r_visible_career2.setVisibility(View.VISIBLE);
        }

        ArrayList<String> career3 = new ArrayList<>();
        if (!career_name3.equals("")) {
            career3.add("1");
        }
        if (!career_kind3.equals("")) {
            career3.add("1");
        }
        if (!career_use_month3.equals("")) {
            career3.add("1");
        }
        if (!career_story3.equals("")) {
            career3.add("1");
        }
        if (career3.size() == 0 || career3.size() == 4) {
            txt_r_visible_career3.setVisibility(View.GONE);
        } else {
            txt_r_visible_career3.setVisibility(View.VISIBLE);
        }

        if (txt_r_visible_title.getVisibility() == View.VISIBLE || txt_r_visible_photo.getVisibility() == View.VISIBLE || txt_r_visible_name.getVisibility() == View.VISIBLE ||
                txt_r_visible_ymd.getVisibility() == View.VISIBLE || txt_r_visible_phone.getVisibility() == View.VISIBLE || txt_r_visible_address.getVisibility() == View.VISIBLE
                || txt_r_visible_school.getVisibility() == View.VISIBLE || txt_r_visible_resume.getVisibility() == View.VISIBLE || txt_r_visible_career1.getVisibility() == View.VISIBLE
                || txt_r_visible_career2.getVisibility() == View.VISIBLE || txt_r_visible_career3.getVisibility() == View.VISIBLE) {
            return;
        } else {
            Log.d("hjh", "id=" + id);
            if (id == 0) {
                /*
                formcontent[26], formcontent[27], formcontent[0], formcontent[2], formcontent[3], formcontent[4], formcontent[5], formcontent[6], formcontent[7], formcontent[8], formcontent[9], formcontent[10], formcontent[11], formcontent[12], formcontent[13], formcontent[14], formcontent[15], formcontent[16], formcontent[17], formcontent[18], formcontent[19], formcontent[20], formcontent[21], formcontent[22], formcontent[23], formcontent[24], formcontent[25]
                 */

                Call<Member_Result> observ = RetrofitService.getInstance().getRetrofitRequest().m_personal_detail_resume_ok(formcontent[26], formcontent[27], formcontent[0], formcontent[2],
                        formcontent[3], formcontent[4], formcontent[5], formcontent[6], formcontent[7], formcontent[8], formcontent[9], formcontent[10], formcontent[11], formcontent[12],
                        formcontent[13], formcontent[14], formcontent[15], formcontent[16], formcontent[17], formcontent[18], formcontent[19], formcontent[20], formcontent[21],
                        formcontent[22], formcontent[23], formcontent[24], formcontent[25], filePart);
                observ.enqueue(new Callback<Member_Result>() {
                    @Override
                    public void onResponse(Call<Member_Result> call, Response<Member_Result> response) {
                        if (response.isSuccessful()) {
                            Member_Result result = response.body();
                            if (result.getResult() == 0) {
                                Member login_member = new Member(result.getId(), result.getMember_id(), result.getMember_pw(), result.getMember_kind(), result.getMember_history(), result.getMember_history_date(), result.getMember_status(), result.getMember_status_date());
                                loginService.setLoginId(result.getMember_id());
                                loginService.setLoginPw(result.getMember_pw());
                                loginService.setLoginMember(login_member);
                            }
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Member_Result> call, Throwable t) {

                    }
                });
            } else {
                Integer ccountsize = ccountarr.size();
                Integer lcountsize = lcountarr.size();
                ccountarr.add(0l);
                ccountarr.add(0l);
                ccountarr.add(0l);
                lcountarr.add(0l);
                lcountarr.add(0l);
                lcountarr.add(0l);
                RequestBody formcontent2[] = new RequestBody[8];
                formcontent2[0] = RequestBody.create(MediaType.parse("text/plain"), ccountsize.toString());
                formcontent2[1] = RequestBody.create(MediaType.parse("text/plain"), lcountsize.toString());
                formcontent2[2] = RequestBody.create(MediaType.parse("text/plain"), ccountarr.get(0).toString());
                formcontent2[3] = RequestBody.create(MediaType.parse("text/plain"), ccountarr.get(1).toString());
                formcontent2[4] = RequestBody.create(MediaType.parse("text/plain"), ccountarr.get(2).toString());
                formcontent2[5] = RequestBody.create(MediaType.parse("text/plain"), lcountarr.get(0).toString());
                formcontent2[6] = RequestBody.create(MediaType.parse("text/plain"), lcountarr.get(1).toString());
                formcontent2[7] = RequestBody.create(MediaType.parse("text/plain"), lcountarr.get(2).toString());

                if (imgUri != null) {
                    Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().m_personal_detail_resume_update_ok(formcontent[26], formcontent[27], formcontent[0], formcontent[2],
                            formcontent[3], formcontent[4], formcontent[5], formcontent[6], formcontent[7], formcontent[8], formcontent[9], formcontent[10], formcontent[11], formcontent[12],
                            formcontent[13], formcontent[14], formcontent[15], formcontent[16], formcontent[17], formcontent[18], formcontent[19], formcontent[20], formcontent[21],
                            formcontent[22], formcontent[23], formcontent[24], formcontent[25], filePart,
                            formcontent2[0], formcontent2[1], formcontent2[2], formcontent2[3],  formcontent2[4],
                            formcontent2[5], formcontent2[6], formcontent2[7]);
                    observ.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
                else {
                    Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().m_personal_detail_resume_update_ok2(formcontent[26], formcontent[27], formcontent[0], formcontent[2],
                            formcontent[3], formcontent[4], formcontent[5], formcontent[6], formcontent[7], formcontent[8], formcontent[9], formcontent[10], formcontent[11], formcontent[12],
                            formcontent[13], formcontent[14], formcontent[15], formcontent[16], formcontent[17], formcontent[18], formcontent[19], formcontent[20], formcontent[21],
                            formcontent[22], formcontent[23], formcontent[24], formcontent[25],
                            formcontent2[0], formcontent2[1], formcontent2[2], formcontent2[3],  formcontent2[4],
                            formcontent2[5], formcontent2[6], formcontent2[7]);
                    observ.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
            }
        }
    }

    public void refresh() {
        ed_r_detail_y.setText(year.toString());
        ed_r_detail_m.setText("" + (month+1));
        ed_r_detail_d.setText(day.toString());
    }
}
