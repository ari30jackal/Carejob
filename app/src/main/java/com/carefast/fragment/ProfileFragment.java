package com.carefast.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carefast.Login.LoginActivity;
import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.detailjob.DetailJobjadwalActivity;
import com.carefast.menuprofile.AkunSayaActivity;
import com.carefast.menuprofile.DataDiriActivity;
import com.carefast.menuprofile.KeamananAkunActivity;
import com.carefast.menuprofile.KelengkapanActivity;
import com.carefast.menuprofile.PengalamanActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
LinearLayout logout,lldatadiri,llpengalaman,llkelengkapan,llkeamanan;
SecuredPreference pref;
BaseApiService mApiService;
ImageView ivprofile;
String userid;
TextView tvnama,tvphone,tvemail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logout = view.findViewById(R.id.ll_logout);
        mApiService = UtilsApi.getAPIService();
        tvemail = view.findViewById(R.id.tv_gmail);
        tvnama = view.findViewById(R.id.tv_nama);
        tvphone = view.findViewById(R.id.tv_phone);
        pref = new SecuredPreference(getActivity(), PrefContract.PREF_EL);
        lldatadiri = view.findViewById(R.id.ll_datadiri);
       ivprofile = view.findViewById(R.id.iv_changeprofile);
       ivprofile.setOnClickListener(v->{
           Intent intent = new Intent(getActivity(), AkunSayaActivity.class);
           getActivity().startActivity(intent);
//           getActivity().finish();

       });
        try {
            userid=pref.getString(PrefContract.user_id,"");
        } catch (AppException e) {
            e.printStackTrace();
        }
        getprofile();
        llkeamanan =view.findViewById(R.id.ll_keamanan);
        llpengalaman = view.findViewById(R.id.ll_pengalaman);
        llkelengkapan = view.findViewById(R.id.ll_kelengkapan);




        llkelengkapan.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), KelengkapanActivity.class);
            getActivity().startActivity(intent);
            try {
                pref.put(PrefContract.user_id, userid);
            } catch (AppException e) {
                e.printStackTrace();
            }

//    getActivity().finish();

        });

llkeamanan.setOnClickListener(v ->{
    Intent intent = new Intent(getActivity(), KeamananAkunActivity.class);
    getActivity().startActivity(intent);
    try {
        pref.put(PrefContract.user_id, userid);
    } catch (AppException e) {
        e.printStackTrace();
    }

//    getActivity().finish();

});

llpengalaman.setOnClickListener(v->{

    Intent intent = new Intent(getActivity(), PengalamanActivity.class);
    getActivity().startActivity(intent);
//    getActivity().finish();
});
        lldatadiri.setOnClickListener(v->{

            Intent intent = new Intent(getActivity(), DataDiriActivity.class);
            getActivity().startActivity(intent);
//            getActivity().finish();


        });
        logout.setOnClickListener(v->{

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
            pref.clear();
            try {
                pref.put(PrefContract.user_login, "false");
            } catch (AppException e) {
                e.printStackTrace();
            }


        });




    }
    private void getprofile() {
//        Toast.makeText(getActivity(),id_sch, Toast.LENGTH_SHORT).show();

        mApiService.getprofile(userid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                        //String pesan = jsonRESULTS.getString("success");
                        JSONArray ds = jsonRESULTS.getJSONArray("Profile");
                        JSONObject dd = ds.getJSONObject(0);
                        String email_applicant = dd.getString("email_applicant");
                        String phone_applicant = dd.getString("phone_applicant");
                        String last_name_applicant = dd.getString("last_name_applicant");
                        String first_name_applicant = dd.getString("first_name_applicant");
                   tvnama.setText(first_name_applicant+" "+last_name_applicant);
                   tvemail.setText(email_applicant);
                   tvphone.setText(phone_applicant);


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

    @Override
    public void onResume() {
        super.onResume();
        getprofile();
    }
}