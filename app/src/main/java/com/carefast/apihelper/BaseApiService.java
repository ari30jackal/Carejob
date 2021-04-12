package com.carefast.apihelper;


import com.carefast.model.AdvertisementResponse;
import com.carefast.model.CityResponse;
import com.carefast.model.DistrictsResponse;
import com.carefast.model.JobPositionResponse;
import com.carefast.model.ListInterviewResponse;
import com.carefast.model.NotificationResponse;
import com.carefast.model.PostalcodeResponse;
import com.carefast.model.ProvResponse;
import com.carefast.model.ResponseCity;
import com.carefast.model.ResponseProvince;
import com.carefast.model.StatusLamaranResponse;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by Teke on 01/11/2017.
 */
public interface BaseApiService {

    @GET("apiari/province")
    Call<ProvResponse> province_get();
    @GET("apiari/advertisement")
    Call<AdvertisementResponse>advertisement_get();
    @GET("apiari/jobposition")
    Call<JobPositionResponse>position_get();
    @FormUrlEncoded
    @POST("apiari/city")
    Call<CityResponse> city_get(@Field("prov_id") String province_id);
    @FormUrlEncoded
    @POST("apiari/districts")
    Call<DistrictsResponse>districts_get(@Field("city_id") String city_id);
    @FormUrlEncoded
    @POST("apiari/hasilscan")
    Call<ResponseBody>scanqr(@Field("id_interview") String id_interview);
    @FormUrlEncoded
    @POST("apiari/hadir")
    Call<ResponseBody>hadir(@Field("id_schedule_interview") String id_schedule_interview,
                            @Field("id_user_applicant") String id_user_applicant);
    @FormUrlEncoded
    @POST("apiari/updateemail")
    Call<ResponseBody>updateemail(
            @Field("id_user") String id_user,
            @Field("email_applicant") String email_applicant

    );

    @FormUrlEncoded
    @POST("api/Resetpass/forgot_password")
    Call<ResponseBody> forgetPass( @Field("email") String email);
    @FormUrlEncoded
    @POST("apiari/updatephone")
    Call<ResponseBody>updatephone(
            @Field("id_user") String id_user,
            @Field("phone_applicant") String phone_applicant

    );

    @FormUrlEncoded
    @POST("apiari/updatedatadiri")
    Call<ResponseBody>updatedatadiri(
            @Field("id_user") String id_user,
            @Field("weight_applicant") String weight_applicant,
            @Field("province_applicant") String province_applicant,
            @Field("city_applicant") String city_applicant,
            @Field("districts_applicant") String districts_applicant,
            @Field("zip_code_applicant") String zip_code_applicant,
            @Field("address_details_applicant") String address_details_applicant
    );

    @FormUrlEncoded
    @POST("apiari/datadiri")
    Call<ResponseBody>getdatadiri(@Field("id_user") String id_user);



    @FormUrlEncoded
    @POST("apiari/getpengalaman")
    Call<ResponseBody>getpengalaman(@Field("id_user_applicant") String id_user_applicant);


    @FormUrlEncoded
    @POST("apiari/updatenama")
    Call<ResponseBody>updatenama(
            @Field("id_user") String id_user,
            @Field("first_name_applicant") String first_name_applicant,
            @Field("last_name_applicant") String last_name_applicant

    );
    @FormUrlEncoded
    @POST("api/regisFirstGet")
    Call<ResponseBody>getId(
            @Field("email_applicant") String email
    );
    @Multipart
    @POST("api/pengalamanedu")
    Call<ResponseBody> updateedu(
            @Part("id_applicant") RequestBody uid,
            @Part MultipartBody.Part images,
            @Part("work_experience_applicant") RequestBody exp,
            @Part("last_job_applicant") RequestBody job,
            @Part("last_education_applicant") RequestBody edu);

    @Multipart
    @POST("api/updatepengalaman")
    Call<ResponseBody> updatepengalaman(
            @Part("id_applicant") RequestBody uid,
            @Part("work_experience_applicant") RequestBody exp,
            @Part("last_job_applicant") RequestBody job,
            @Part("last_education_applicant") RequestBody edu);


    @FormUrlEncoded
    @POST("apiari/advertisementsearch")
    Call<AdvertisementResponse>advertisementsearch(
            @Field("keyword") String keyword
    );
    @FormUrlEncoded
    @POST("apiari/deletenotif")
    Call<ResponseBody>deletenotif(
            @Field("id_notification") String id_notification
    );
    @Multipart
    @POST("api/pengalamanjob")
    Call<ResponseBody>updatejob(
            @Part("id_applicant") RequestBody uid,

            @Part MultipartBody.Part image2,
            @Part("work_experience_applicant") RequestBody exp,
            @Part("last_job_applicant") RequestBody job,
            @Part("last_education_applicant") RequestBody edu);
    @FormUrlEncoded
    @POST("api/regisFirst")
    Call<ResponseBody>stepOne(
            @Field("email_applicant") String email,
            @Field("password_show") String pass,
            @Field("phone_applicant") String phone
    );

    @FormUrlEncoded
    @POST("api/regisFirstUpdate")
    Call<ResponseBody>stepOneUpdate(
            @Field("id_applicant") String uid,
            @Field("email_applicant") String email,
            @Field("password_show") String pass,
            @Field("phone_applicant") String phone
    );

    @Multipart
    @POST("api/regisSecond")
    Call<ResponseBody> stepTwo(
            @Part("id_applicant") RequestBody uid,
            @Part("gender_applicant") RequestBody gender,
            @Part("birth_at_applicant") RequestBody place,
            @Part("birth_date_applicant") RequestBody date,
            @Part("nik_number") RequestBody nik,
            @Part("first_name_applicant") RequestBody first,
            @Part("last_name_applicant") RequestBody last,
            @Part("height_applicant") RequestBody height,
            @Part("weight_applicant") RequestBody weight,
            @Part("province_applicant") RequestBody prov,
            @Part("city_applicant") RequestBody city,
            @Part("districts_applicant") RequestBody dis,
            @Part("zip_code_applicant") RequestBody code,
            @Part("address_details_applicant") RequestBody detail,
            @Part MultipartBody.Part image);

    @Multipart
    @POST("api/regisThird")
    Call<ResponseBody> stepThree(
            @Part("id_applicant") RequestBody uid,
            @Part MultipartBody.Part image,
            @Part MultipartBody.Part image2,
            @Part("work_experience_applicant") RequestBody exp,
            @Part("last_job_applicant") RequestBody job,
            @Part("last_education_applicant") RequestBody edu);

    @FormUrlEncoded
    @POST("api/regisFourth")
    Call<ResponseBody>stepFour(
            @Field("user_id") String uid
    );
    @FormUrlEncoded
    @POST("apiari/jabatantemp")
    Call<ResponseBody>deletetemp(
            @Field("user_id") String uid
    );
    @FormUrlEncoded
    @POST("apiari/notificationcall")
    Call<NotificationResponse>getnotif(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("api/regisfifthvalidation")
    Call<ResponseBody>getValidationStepFifth(
            @Field("id_applicant") String uid
    );


    @FormUrlEncoded
    @POST("api/regisFifth")
    Call<ResponseBody>stepFifth(
            @Field("id_applicant") String uid,
            @Field("have_tatoo_applicant") String tattoo,
            @Field("ear_piercing_applicant") String piercing,
            @Field("marital_status_applicant") String married
    );


    @FormUrlEncoded
    @POST("apiari/isactive")
    Call<ResponseBody>isactive(
            @Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("api/api_verif/verifyemail")
    Call<ResponseBody>verifyMail(
            @Field("email_applicant") String email

    );


    @Multipart
    @POST("api/regisFifth_skck")
    Call<ResponseBody> stepFiveSkck(
            @Part("id_applicant") RequestBody uid,
            @Part MultipartBody.Part image);


    @Multipart
    @POST("api/regisFifth_certi")
    Call<ResponseBody> stepFiveCerti(
            @Part("id_applicant") RequestBody uid,
            @Part MultipartBody.Part image);

    @Multipart
    @POST("api/regisFifth_sim")
    Call<ResponseBody> stepFiveSim(
            @Part("id_applicant") RequestBody uid,
            @Part MultipartBody.Part image);

    @Multipart
    @POST("api/regisFifth_stnk")
    Call<ResponseBody> stepFiveBpkb(
            @Part("id_applicant") RequestBody uid,
            @Part MultipartBody.Part image);



    @Multipart
    @POST("api/regisFifth_sio")
    Call<ResponseBody> stepFiveSio(
            @Part("id_applicant") RequestBody uid,
            @Part MultipartBody.Part image);


    @FormUrlEncoded
    @POST("api/updateJobData")
    Call<ResponseBody>updateJobData(
            @Field("user_id") String uid,
            @Field("job_name") String jname,
            @Field("id_job") String idjob,
            @Field("job_status") String jstatus
    );


    @FormUrlEncoded
    @POST("api/changePass")
    Call<ResponseBody>changePass(
            @Field("id_applicant") String uid,
            @Field("password_show") String pass
    );

    @FormUrlEncoded
    @POST("api/checkOldPass")
    Call<ResponseBody>checkOldPass(
            @Field("id_applicant") String uid,
            @Field("password_show") String pass
    );

    @FormUrlEncoded
    @POST("api/getDataKelengkapan")
    Call<ResponseBody>getDataKelengkapan(
            @Field("id_applicant") String uid
    );



//
//    @FormUrlEncoded
//    @POST("apiari/detailinterview")
//    Call<ResponseBody>detailinterview(@Field("id_interview") String id_interview,@Field("id_lamaran") String id_lamaran);

    @FormUrlEncoded
    @POST("apiari/detailinterview")
    Call<ResponseBody>detailinterview(@Field("id_schedule_interview") String id_interview,
                                         @Field("id_user") String id_user,
                                         @Field("id_advertisement") String id_advertisement);

    @FormUrlEncoded
    @POST("apiari/profile")
    Call<ResponseBody>getprofile(@Field("id_user_applicant") String id_user_applicant);







    @FormUrlEncoded
    @POST("apiari/postal")
    Call<PostalcodeResponse>postal_get(@Field("prov_id") String prov_id,
                                       @Field("city_id") String city_id,
                                       @Field("dis_id") String dis_id);
    @FormUrlEncoded
    @POST("user/auth/login_email")
    Call<ResponseBody>login(@Field("email_applicant") String email_applicant,
                                       @Field("password_hash") String password_hash);
    @FormUrlEncoded
    @POST("apiari/proseslamar")
    Call<ResponseBody>lamarkerja(@Field("id_advertisement") String id_advertisement,
                            @Field("id_user_applicant") String id_user_applicant);
    @FormUrlEncoded
    @POST("apiari/advertisementstatus")
    Call<ResponseBody>advertisementstatus(@Field("id_advertisement") String id_advertisement,
                                 @Field("id_user_applicant") String id_user_applicant,
                                          @Field("id_interview") String id_interview);
    @FormUrlEncoded
    @POST("apiari/advertisementbyid")
    Call<ResponseBody>advertisementbyid(@Field("id_advertisement") String id_advertisement);
    @FormUrlEncoded
    @POST("apiari/listlamaran")
    Call<StatusLamaranResponse>statuslamaran(@Field("id_user_applicant") String id_user_applicant);
    @FormUrlEncoded
    @PUT("apiari/updatestatus")
    Call<ResponseBody> changestatus(@Field("id_user_advertisement") String id_user_advertisement,
                                   @Field("status_review") String status_review);


    @FormUrlEncoded
    @POST("apiari/interview")
    Call<ListInterviewResponse>listinterview(@Field("id_applicant_approve") String id_applicant_approve);
    @FormUrlEncoded
    @POST("apiari/interviewdua")
    Call<ResponseBody>getadvertfromschedule(@Field("id_user_applicant") String id_user_applicant,
                                                     @Field("id_advertisement") String id_advertisement);

}
