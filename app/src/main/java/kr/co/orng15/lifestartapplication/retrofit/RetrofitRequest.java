package kr.co.orng15.lifestartapplication.retrofit;

import android.content.Intent;

import java.util.ArrayList;

import kr.co.orng15.lifestartapplication.data.Apply;
import kr.co.orng15.lifestartapplication.data.Board;
import kr.co.orng15.lifestartapplication.data.Board_Get_Use;
import kr.co.orng15.lifestartapplication.data.Board_Read;
import kr.co.orng15.lifestartapplication.data.Board_Write;
import kr.co.orng15.lifestartapplication.data.Company_Detail;
import kr.co.orng15.lifestartapplication.data.Company_Detail_Use;
import kr.co.orng15.lifestartapplication.data.Company_Member;
import kr.co.orng15.lifestartapplication.data.Company_Member_Result;
import kr.co.orng15.lifestartapplication.data.FirstCertified;
import kr.co.orng15.lifestartapplication.data.Idcheck;
import kr.co.orng15.lifestartapplication.data.JoinResult;
import kr.co.orng15.lifestartapplication.data.Member;
import kr.co.orng15.lifestartapplication.data.Member_Result;
import kr.co.orng15.lifestartapplication.data.Mresult;
import kr.co.orng15.lifestartapplication.data.Personal_Detail;
import kr.co.orng15.lifestartapplication.data.Personal_Detail_Resume;
import kr.co.orng15.lifestartapplication.data.Personal_Detail_Resume_Use;
import kr.co.orng15.lifestartapplication.data.Personal_Member;
import kr.co.orng15.lifestartapplication.data.Personal_Member_Result;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018-01-29.
 */

public interface RetrofitRequest {
    /*
    @GET("mq1getmemo.do")
    Call<ArrayList<Memo>> getMemoInfo();

    @FormUrlEncoded
    @POST("m_join_ok.do")
    Call<JoinResult> insertMember(@Field("member_company_number") String member_company_number, @Field("member_company_name") String member_company_name, @Field("member_company_ceo") String member_company_ceo, @Field("member_id") String member_id, @Field("member_pw")String member_pw, @Field("member_name")String member_name, @Field("member_number")String member_number, @Field("member_email")String member_email, @Field("member_value")Integer member_value);

    @FormUrlEncoded
    @POST("m_login_ok.do")
    Call<Personal_Member_Result> loginPMember(@Field("member_id") String member_id, @Field("member_pw")String member_pw, @Field("member_value")Integer member_value);

    @FormUrlEncoded
    @POST("m_c_pw_ok.do")
    Call<Member_Result> pw_CMember(@Field("id") Integer id, @Field("member_pw") String member_pw);

    @FormUrlEncoded
    @POST("m_p_pw_ok.do")
    Call<Member_Result> pw_PMember(@Field("id") Integer id, @Field("member_pw")String member_pw);*/

    //Commodify 액티비티
    @FormUrlEncoded
    @POST("m_change_pw_ok.do")
    Call<Void> pw_Member(@Field("id") Integer id, @Field("member_pw") String member_pw);

    //joininput 맥티비티에 사용
    @GET("m_idCheck.do")
    Call<Idcheck> m_idCheck(@Query("member_id") String member_id);

    //joinInput 액티비티에서 사용
    @FormUrlEncoded
    @POST("m_join_ok.do")
    Call<JoinResult> insertMember(@Field("member_id") String member_id, @Field("member_pw") String member_pw, @Field("member_kind") Integer member_kind);

    //login 액티비티에서 사용
    @FormUrlEncoded
    @POST("m_login_ok.do")
    Call<Member_Result> loginMember(@Field("member_id") String member_id, @Field("member_pw") String member_pw);

    //joinCompany 액티비티에서 사용
    @FormUrlEncoded
    @POST("m_c_info_ok.do")
    Call<Member_Result> info_CMember(@Field("id") Integer id, @Field("company_number") String company_number, @Field("company_name")String company_name, @Field("company_ceoname")String company_ceoname, @Field("company_phone")String company_phone, @Field("company_homepage")String company_homepage);

    //join 액티비티에서 사용
    @FormUrlEncoded
    @POST("m_p_info_ok.do")
    Call<Member_Result> info_PMember(@Field("id") Integer id, @Field("member_name")String member_name, @Field("member_phone")String member_phone, @Field("member_email")String member_email);

    //join 액티비티에서 사용
    @GET("m_p_info.do")
    Call<Personal_Member> m_p_Info(@Query("id") Integer id);

    //join_company 액티비티에서 사용
    @GET("m_c_info.do")
    Call<Company_Member> m_c_Info(@Query("id") Integer id);

    //Resume 액티비티에 사용
    @FormUrlEncoded
    @POST("m_personal_detail_ok.do")
    Call<Void> m_personal_detail_ok(@Field("member_id") Integer member_id, @Field("detail_birth_year") String detail_birth_year, @Field("detail_birth_month") String detail_birth_month, @Field("detail_birth_day") String detail_birth_day,
                                    @Field("detail_gender") String detail_gender, @Field("detail_address") String detail_address, @Field("detail_learn") String detail_learn, @Field("detail_learn_finish") String detail_learn_finish);

    //이력서, 기업정보, 개인정보작성란 추가 가능케
    //Resume 액티비티에 사용
    @FormUrlEncoded
    @POST("m_personal_detail_count.do")
    Call<Mresult> m_personal_detail_count(@Field("member_id") Integer member_id);

    @FormUrlEncoded
    @POST("m_personal_detail_get.do")
    Call<Personal_Detail> m_personal_detail_get(@Field("member_id") Integer member_id);

    //detail_resume 액티비티에 사용
    @Multipart
    @POST("m_personal_detail_resume_ok.do")
    Call<Member_Result> m_personal_detail_resume_ok(@Part("id") RequestBody id, @Part("member_id") RequestBody member_id, @Part("detail_title") RequestBody detail_title, @Part("detail_myresume") RequestBody detail_myresume, @Part("detail_name") RequestBody detail_name,
                                                    @Part("detail_birth_year") RequestBody detail_birth_year, @Part("detail_birth_month") RequestBody detail_birth_month, @Part("detail_birth_day") RequestBody detail_birth_day,
                                                    @Part("detail_phone") RequestBody detail_phone, @Part("detail_address") RequestBody detail_address, @Part("detail_learn") RequestBody detail_learn,
                                                    @Part("detail_learn_finish") RequestBody detail_learn_finish, @Part("career_name1") RequestBody career_name1, @Part("career_kind1") RequestBody career_kind1,
                                                    @Part("career_use_month1") RequestBody career_use_month1, @Part("career_story1") RequestBody career_story1, @Part("career_name2") RequestBody career_name2,
                                                    @Part("career_kind2") RequestBody career_kind2, @Part("career_use_month2") RequestBody career_use_month2, @Part("career_story2") RequestBody career_story2,
                                                    @Part("career_name3") RequestBody career_name3, @Part("career_kind3") RequestBody career_kind3, @Part("career_use_month3") RequestBody career_use_month3,
                                                    @Part("career_story3") RequestBody career_story3, @Part("detail_license_name1") RequestBody detail_license_name1, @Part("detail_license_name2") RequestBody detail_license_name2,
                                                    @Part("detail_license_name3") RequestBody detail_license_name3, @Part MultipartBody.Part photo);

    //@Part("content") RequestBody content,
    //detail_resume 액티비티에 사용
    @Multipart
    @POST("m_personal_detail_resume_update_ok.do")
    Call<Void> m_personal_detail_resume_update_ok(@Part("id") RequestBody id, @Part("member_id") RequestBody member_id, @Part("detail_title") RequestBody detail_title, @Part("detail_myresume") RequestBody detail_myresume, @Part("detail_name") RequestBody detail_name,
                                                  @Part("detail_birth_year") RequestBody detail_birth_year, @Part("detail_birth_month") RequestBody detail_birth_month, @Part("detail_birth_day") RequestBody detail_birth_day,
                                                  @Part("detail_phone") RequestBody detail_phone, @Part("detail_address") RequestBody detail_address, @Part("detail_learn") RequestBody detail_learn,
                                                  @Part("detail_learn_finish") RequestBody detail_learn_finish, @Part("career_name1") RequestBody career_name1, @Part("career_kind1") RequestBody career_kind1,
                                                  @Part("career_use_month1") RequestBody career_use_month1, @Part("career_story1") RequestBody career_story1, @Part("career_name2") RequestBody career_name2,
                                                  @Part("career_kind2") RequestBody career_kind2, @Part("career_use_month2") RequestBody career_use_month2, @Part("career_story2") RequestBody career_story2,
                                                  @Part("career_name3") RequestBody career_name3, @Part("career_kind3") RequestBody career_kind3, @Part("career_use_month3") RequestBody career_use_month3,
                                                  @Part("career_story3") RequestBody career_story3, @Part("detail_license_name1") RequestBody detail_license_name1, @Part("detail_license_name2") RequestBody detail_license_name2,
                                                  @Part("detail_license_name3") RequestBody detail_license_name3, @Part MultipartBody.Part photo,
                                                  @Part("ccsize") RequestBody ccsize, @Part("lcsize") RequestBody lcsize,
                                                  @Part("ccount0") RequestBody ccount0, @Part("ccount1") RequestBody ccount1, @Part("ccount2") RequestBody ccount2,
                                                  @Part("lcount0") RequestBody lcount0, @Part("lcount1") RequestBody lcount1, @Part("lcount2") RequestBody lcount2);

    @Multipart
    @POST("m_personal_detail_resume_update_ok2.do")
    Call<Void> m_personal_detail_resume_update_ok2(@Part("id") RequestBody id, @Part("member_id") RequestBody member_id, @Part("detail_title") RequestBody detail_title, @Part("detail_myresume") RequestBody detail_myresume, @Part("detail_name") RequestBody detail_name,
                                                   @Part("detail_birth_year") RequestBody detail_birth_year, @Part("detail_birth_month") RequestBody detail_birth_month, @Part("detail_birth_day") RequestBody detail_birth_day,
                                                   @Part("detail_phone") RequestBody detail_phone, @Part("detail_address") RequestBody detail_address, @Part("detail_learn") RequestBody detail_learn,
                                                   @Part("detail_learn_finish") RequestBody detail_learn_finish, @Part("career_name1") RequestBody career_name1, @Part("career_kind1") RequestBody career_kind1,
                                                   @Part("career_use_month1") RequestBody career_use_month1, @Part("career_story1") RequestBody career_story1, @Part("career_name2") RequestBody career_name2,
                                                   @Part("career_kind2") RequestBody career_kind2, @Part("career_use_month2") RequestBody career_use_month2, @Part("career_story2") RequestBody career_story2,
                                                   @Part("career_name3") RequestBody career_name3, @Part("career_kind3") RequestBody career_kind3, @Part("career_use_month3") RequestBody career_use_month3,
                                                   @Part("career_story3") RequestBody career_story3, @Part("detail_license_name1") RequestBody detail_license_name1, @Part("detail_license_name2") RequestBody detail_license_name2,
                                                   @Part("detail_license_name3") RequestBody detail_license_name3,
                                                   @Part("ccsize") RequestBody ccsize, @Part("lcsize") RequestBody lcsize,
                                                   @Part("ccount0") RequestBody ccount0, @Part("ccount1") RequestBody ccount1, @Part("ccount2") RequestBody ccount2,
                                                   @Part("lcount0") RequestBody lcount0, @Part("lcount1") RequestBody lcount1, @Part("lcount2") RequestBody lcount2);

    //resume_manager 액티비티에 사용
    @FormUrlEncoded
    @POST("m_personal_detail_resume_get.do")
    Call<ArrayList<Personal_Detail_Resume>> m_personal_detail_resume_get(@Field("member_id") Integer member_id);

    //resumelistadapter 에서 사용
    @FormUrlEncoded
    @POST("m_personal_detail_resume_del.do")
    Call<Void> m_personal_detail_resume_del(@Field("id") Long id);

    //detail_resume 액티비티에서 사용
    @FormUrlEncoded
    @POST("m_personal_detail_resume_use_get.do")
    Call<Personal_Detail_Resume_Use> m_personal_detail_resume_use_get(@Field("id") Long id, @Field("member_id") Integer member_id);

    /*기존
    @FormUrlEncoded
    @POST("m_company_detail_ok.do")
    Call<Void> m_company_detail_ok(@Field("member_id") Integer member_id, @Field("company_kind") String company_kind, @Field("company_mainbusiness") String company_mainbusiness
            , @Field("company_insurance") String company_insurance, @Field("company_address") String company_address, @Field("company_moneysize") String company_moneysize, @Field("company_membersize") String company_membersize
            , @Field("company_year") String company_year, @Field("company_month") String company_month, @Field("company_day") String company_day, @Field("company_hrd_email") String company_hrd_email
            , @Field("company_hrd_name") String company_hrd_name, @Field("company_hrd_phone") String company_hrd_phone);*/

    //기업 정보 신규작성 companyDetail
    @Multipart
    @POST("m_company_detail_ok.do")
    Call<Member_Result> m_company_detail_ok(@Part("member_id") RequestBody id, @Part("company_kind") RequestBody company_kind, @Part("company_mainbusiness") RequestBody company_mainbusiness, @Part("company_insurance") RequestBody company_insurance
            , @Part("company_address") RequestBody company_address, @Part("company_moneysize") RequestBody company_moneysize, @Part("company_membersize") RequestBody company_membersize
            , @Part("company_year") RequestBody company_year, @Part("company_month") RequestBody company_month, @Part("company_day") RequestBody company_day, @Part("company_hrd_email") RequestBody company_hrd_email
            , @Part("company_hrd_name") RequestBody company_hrd_name, @Part("company_hrd_phone") RequestBody company_hrd_phone, @Part MultipartBody.Part photo);

    //(수정하러 들어왔을 시)기업정보 작성된것 박아 놓기
    //1.카운트 companyDetail
    @FormUrlEncoded
    @POST("m_company_detail_count.do")
    Call<Mresult> m_company_detail_count(@Field("member_id") Integer member_id);

    //2.ed에 박아놓기 companyDetail
    @FormUrlEncoded
    @POST("m_company_detail_get.do")
    Call<Company_Detail_Use> m_company_detail_get(@Field("member_id") Integer member_id);

    //board 액티비티
    @FormUrlEncoded
    @POST("m_board_write_get.do")
    Call<Board_Write> m_board_write_get(@Field("member_id") Integer member_id);

    //board 액티비티
    @Multipart
    @POST("m_board_write_ok.do")
    Call<Void> m_board_write_ok(@Part("member_id") RequestBody id, @Part("board_title") RequestBody board_title, @Part("board_jobkind") RequestBody board_jobkind, @Part("board_new") RequestBody board_new, @Part("board_career") RequestBody board_career
            , @Part("board_learn") RequestBody board_learn, @Part("board_essential") RequestBody board_essential, @Part("board_bonus") RequestBody board_bonus
            , @Part("board_membermax") RequestBody board_membermax, @Part("board_jobplace") RequestBody board_jobplace, @Part("board_jobday") RequestBody board_jobday, @Part("board_jobtime") RequestBody board_jobtime
            , @Part("board_money") RequestBody board_money, @Part("board_step") RequestBody board_step, @Part("board_document") RequestBody board_document, @Part("board_end_year") RequestBody board_end_year
            , @Part("board_end_month") RequestBody board_end_month, @Part("board_end_day") RequestBody board_end_day, @Part("board_content") RequestBody board_content, @Part("board_name") RequestBody board_name
            , @Part("board_kind") RequestBody board_kind, @Part("board_mainbusiness") RequestBody board_mainbusiness, @Part("board_insurance") RequestBody board_insurance, @Part("board_address") RequestBody board_address, @Part("board_moneysize") RequestBody board_moneysize
            , @Part("board_membersize") RequestBody board_membersize, @Part("board_year") RequestBody board_year, @Part("board_month") RequestBody board_month, @Part("board_day") RequestBody board_day, @Part("board_hrd_email") RequestBody board_hrd_email
            , @Part("board_hrd_name") RequestBody board_hrd_name, @Part("board_hrd_phone") RequestBody board_hrd_phone, @Part ArrayList<MultipartBody.Part> photo);

    //index 액티비티에 사용
    @FormUrlEncoded
    @POST("m_board_get.do")
    Call<ArrayList<Board>> m_board_get(@Field("search") String  search);

    //Board Detail 액티비티에 사용
    @FormUrlEncoded
    @POST("m_board_detail.do")
    Call<Board_Read> m_board_detail(@Field("member_id") Long member_id, @Field("board_id") Long board_id);

    //Board_Check
    @FormUrlEncoded
    @POST("m_board_apply.do")
    Call<Mresult> m_board_apply(@Field("member_id") Long member_id, @Field("board_id") Long board_id, @Field("resume_id") Long resume_id, @Field("resume_title") String resume_title);

    //CompanyListActivity 액티비티에 사용
    @FormUrlEncoded
    @POST("m_company_list.do")
    Call<ArrayList<Apply>> m_company_list(@Field("member_id") Integer member_id);

    //ApplyListActivity 액티비티에 사용
    @FormUrlEncoded
    @POST("m_personal_apply.do")
    Call<ArrayList<Apply>> m_personal_apply(@Field("member_id") Integer member_id);

    //ApplyConfirmActivity 액티비티에 사용
    @FormUrlEncoded
    @POST("m_apply_resume.do")
    Call<Apply> m_apply_resume(@Field("apply_id") Long apply_id);

    //ApplyConfirmActivity 액티비티에 사용
    @FormUrlEncoded
    @POST("m_apply_board.do")
    Call<Board> m_apply_board(@Field("board_id") Long board_id);

    //CompanyListActivity 액티비티에 사용
    @FormUrlEncoded
    @POST("m_company_select_list.do")
    Call<ArrayList<Apply>> m_company_select_list(@Field("board_id") Long board_id, @Field("company_id") Long company_id);

    //EmailPopupActivity 액티비티에 사용
    //서버에 보내는 작성된 이메일
    @FormUrlEncoded
    @POST("m_mailsend.do")
    Call<FirstCertified> m_mailsend(@Field("member_id") Integer member_id, @Field("receiver") String email);

    //EmailPopupActivity 액티비티에 사용
    @FormUrlEncoded
    @POST("m_certified_ok.do")
    Call<FirstCertified> m_certified_ok(@Field("certiid") Long cetifiedid, @Field("member_id") Integer member_id, @Field("code") String code);


    //AdminActivity에 사용
    @FormUrlEncoded
    @POST("m_admin_member_get.do")
    Call<ArrayList<Member>> m_admin_member_get(@Field("main_position") String main_position, @Field("search") String search, @Field("kind") String kind, @Field("status") String status);

    // AdminAdapter
    @FormUrlEncoded
    @POST("m_admin_change.do")
    Call<Mresult> m_admin_change(@Field("id") String id, @Field("member_pw") String member_pw, @Field("member_kind") String member_kind, @Field("member_status") String member_status);

    //AdminBoard 액티비티에 사용
    @FormUrlEncoded
    @POST("m_admin_board_get.do")
    Call<ArrayList<Board>> m_admin_board_get(@Field("search") String  search);

    //AdminBoardAdapter m_admin_board_del
    @FormUrlEncoded
    @POST("m_admin_delete.do")
    Call<Mresult> m_admin_delete(@Field("id") Long id);
}

