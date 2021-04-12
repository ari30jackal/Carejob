package com.carefast.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.home.HomeeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatusDetailjadwalFragment extends Fragment {
    TextView tvstatuslamar, teksjuduljadwal, tvplacementkerja, tvjabatan, tvkodelowongan, tvperiode, tvulasan, tvhariinterview, tvjaminterview, tvjabataninterview, tvplacementinterview, tvinterviewer, tvkodelowonganinterview, tvkonfirmasisebelum;
    Button Btnkonfirmasi, Btntolak, Btnperbaikidata, Btnbatallamar;
    SecuredPreference pref;
    BaseApiService mApiService;
    String id_interviewschedule;
    String id_advertisement, id_interview, id_user, id_lamaran, id_sch;
    LinearLayout ll_konfirmasi;
    String adv, sch;
    String getjabatan, getimage, getkode, getplace, getstatus, getreason, gethariint, getjamint, getinterviewer, getplacemenint, getconfirmationdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        ll_konfirmasi = view.findViewById(R.id.ll_konfirmasi);
        tvstatuslamar = view.findViewById(R.id.tv_statuslamaran);
        tvjabatan = view.findViewById(R.id.tv_jabatan);
        tvplacementkerja = view.findViewById(R.id.tv_placementkerja);
        tvkodelowongan = view.findViewById(R.id.tv_kodelowongan);
        tvperiode = view.findViewById(R.id.tv_periode);
        teksjuduljadwal = view.findViewById(R.id.teksjuduljadwal);
        tvulasan = view.findViewById(R.id.tv_ulasan);
        tvhariinterview = view.findViewById(R.id.tv_date);
        Btnbatallamar = view.findViewById(R.id.btn_batallamar);
        Btnperbaikidata = view.findViewById(R.id.btn_perbaikidata);
        tvjaminterview = view.findViewById(R.id.tv_hour);
        tvjabataninterview = view.findViewById(R.id.tvjabataninterview);
        tvplacementinterview = view.findViewById(R.id.tvlocationinterview);
        tvinterviewer = view.findViewById(R.id.tvinterviewer);
        tvkodelowonganinterview = view.findViewById(R.id.tvkodelowinterview);
        tvkonfirmasisebelum = view.findViewById(R.id.tv_bataskonfirmasi);
        Btnkonfirmasi = view.findViewById(R.id.btn_konfirmasikehadiran);
        Btntolak = view.findViewById(R.id.btn_tolakkehadiran);

        try {
            id_advertisement = pref.getString(PrefContract.adv, "");
            id_user = pref.getString(PrefContract.user_id, "");
            id_interviewschedule = pref.getString(PrefContract.sch, "");

            getjabatan = pref.getString(PrefContract.putjabatan,"");
            getkode = pref.getString(PrefContract.putkodejabatan,"");
            getimage = pref.getString(PrefContract.putimgjabatan,"");
            getplace = pref.getString(PrefContract.putplace,"");
            getstatus = pref.getString(PrefContract.putstatus,"");
            getreason =pref.getString(PrefContract.putreason,"");
            gethariint = pref.getString(PrefContract.hariinterviewod,"");
            getjamint = pref.getString(PrefContract.jaminterviewod,"");
            getinterviewer = pref.getString(PrefContract.interviewerod,"");
            getplacemenint = pref.getString(PrefContract.placementinterviewod,"");
            getconfirmationdate = pref.getString(PrefContract.confirmationdate,"");
        } catch (AppException e) {
            e.printStackTrace();
        }
        getdetailinterview();
        hideandshow();



    }

    private void hideandshow() {

        if (getstatus.equals("Not Approved")) {
            ll_konfirmasi.setVisibility(View.GONE);
            teksjuduljadwal.setVisibility(View.GONE);
            Btnkonfirmasi.setVisibility(View.GONE);
            Btntolak.setVisibility(View.GONE);
            tvulasan.setText(getreason);
//            Btnperbaikidata.setVisibility(View.VISIBLE);
            tvstatuslamar.setTextColor(Color.parseColor("#E76A6B"));
            tvstatuslamar.setBackgroundColor(Color.parseColor("#FFECED"));
        } else if (getstatus.equals("On Process")) {
            tvstatuslamar.setTextColor(Color.parseColor("#0030DF"));
            tvstatuslamar.setBackgroundColor(Color.parseColor("#D7E0FF"));

        } else if (getstatus.equals("Approved")) {
            tvulasan.setText("Selamat, Anda dinyatakan lulus pada tahap seleksi lamaran pekerjaan. Tahap selanjutnya adalah Interview. Jadwal terlampir.");
            tvstatuslamar.setTextColor(Color.parseColor("#16AA9D"));
            tvstatuslamar.setBackgroundColor(Color.parseColor("#C0EFEA"));
tvplacementkerja.setText(getplace);
tvjabatan.setText(getjabatan);
            tvkodelowonganinterview.setText(getkode);
            tvjabataninterview.setText(getjabatan);
            ll_konfirmasi.setVisibility(View.VISIBLE);
            teksjuduljadwal.setVisibility(View.VISIBLE);
            Btnkonfirmasi.setOnClickListener(v -> {
                updatestatus("Confirmed");


                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Berhasil!")
                        .setContentText("Berhasil Konfirmasi")
                        .setConfirmText("Siap!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Intent intent  = new Intent(getActivity(), HomeeActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }) .show();
            });


            Btntolak.setOnClickListener(v -> {
                updatestatus("Canceled");
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("DIBATALKAN")
                        .setContentText("Lamaran sudah Dibatalkan")
                        .setConfirmText("Siap!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Intent intent  = new Intent(getActivity(), HomeeActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }) .show();
            });


//    advertisementstatus();
        } else if (getstatus.equals("Confirmed")) {
            tvstatuslamar.setTextColor(Color.parseColor("#FFAE00"));
            tvstatuslamar.setBackgroundColor(Color.parseColor("#FFF4E8"));
            ll_konfirmasi.setVisibility(View.VISIBLE);
            teksjuduljadwal.setVisibility(View.VISIBLE);
            Btntolak.setVisibility(View.GONE);
            Btnkonfirmasi.setVisibility(View.GONE);
            tvkonfirmasisebelum.setVisibility(View.GONE);
            tvkodelowonganinterview.setText(getkode);
            tvulasan.setText("Selamat, Anda dinyatakan lulus pada tahap seleksi lamaran pekerjaan. Tahap selanjutnya adalah Interview. Jadwal terlampir.");
            tvjabataninterview.setText(getjabatan);
        } else if (getstatus.equals("Canceled")) {
            tvstatuslamar.setTextColor(Color.parseColor("#707070"));
            tvstatuslamar.setBackgroundColor(Color.parseColor("#E1E1E1"));
            Btnkonfirmasi.setVisibility(View.GONE);
            Btntolak.setVisibility(View.GONE);
            teksjuduljadwal.setVisibility(View.GONE);
            ll_konfirmasi.setVisibility(View.GONE);
            tvulasan.setText("Jadwal Interview Anda sudah Dibatalkan");
        }


    }

    private void getdetailinterview() {



        mApiService.detailinterview(id_interviewschedule,id_user,id_advertisement).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {

                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                        //String pesan = jsonRESULTS.getString("success");
                        JSONArray ds = jsonRESULTS.getJSONArray("Detailinterview");
                        JSONObject dd = ds.getJSONObject(0);
                        String id_user_advertisement = dd.getString("id_user_advertisement");
                        String status_review = dd.getString("status_review");
                        String reason_review = dd.getString("reason_review");
                        String date_schedule = dd.getString("date_schedule");
                        String end_time = dd.getString("end_time");
                        String start_time= dd.getString("start_time");
                        String location= dd.getString("location");
                        String confirmation_date = dd.getString("confirmation_date");
                        String icon_image = dd.getString("icon_image");
                        String advertisement_code = dd.getString("advertisement_code");
                        String placement = dd.getString("placement");
                        String job_description = dd.getString("job_description");
                        String nama_position= dd.getString("nama_position");
                        String admin_master_name = dd.getString("admin_master_name");
                        tvinterviewer.setText(admin_master_name);
                        tvplacementinterview.setText(location);
                        tvjaminterview.setText(start_time + " - " + end_time);
                        tvhariinterview.setText(date_schedule);
                        tvstatuslamar.setText(status_review);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(getActivity(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void updatestatus(String status) {
        try {
            id_lamaran = pref.getString(PrefContract.id_lamaran, "");
        } catch (AppException e) {
            e.printStackTrace();
        }
        mApiService.changestatus(id_lamaran, status.toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                            JSONObject jsonRESULTS = null;
                            try {
                                jsonRESULTS = new JSONObject(response.body().string());

                                String asd = jsonRESULTS.getString("message");
                                if (asd.equals("berhasil")) {


                                } else {



                                }


                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
                            Log.d("logggOut", "gagal");
                            Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}