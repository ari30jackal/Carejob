package com.carefast.register;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.model.CityItem;
import com.carefast.model.DistrictsItem;
import com.carefast.model.JobPositionItem;
import com.carefast.model.ProvinceItem;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.AssessHolder> {
    List<JobPositionItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private PositionAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context context;
    SecuredPreference pref;
    String passCek, role;
    Date strdate;
    Date waktudetik;
    Date waktuuntil;
    Date sdf3;
    Date waktuhariini;
    FragmentManager fragmentManager;
    FragmentTransaction ft;
    String passing_uid;

    public interface OnItemClicked {
        void onItemClick(int position);
    }


    public PositionAdapter(Context context, List<JobPositionItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public PositionAdapter.AssessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.positionitem, parent, false);

        return new PositionAdapter.AssessHolder(itemView, mListener);
    }


    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(final PositionAdapter.AssessHolder holder, @SuppressLint("RecyclerView") final int position) {

        mApiService = UtilsApi.getAPIService();
        final JobPositionItem item = dataItemList.get(position);
        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);
        try {
            passing_uid = pref.getString(PrefContract.user_id, "");
        } catch (AppException e) {
            e.printStackTrace();
        }
        Log.d("uidnya adapter tahap 4", "onBindViewHolder: " + passing_uid);


        holder.tvTitle.setText(item.getNamaPosition());
        Picasso.get().load(mContext.getResources().getString(R.string.base_url_asset) + item.getPositionImage()).into(holder.iv);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.ceklis.getVisibility() == view.VISIBLE) {
                    holder.ceklis.setVisibility(View.GONE);

                    //when uncheck update status 'N'
                    unupdateJobData(item.getNamaPosition(), item.getIdJobPosition());
                } else {
                    holder.ceklis.setVisibility(View.VISIBLE);

                    //when check update status 'Y'
                    Log.d("namaposisi", "onClick: " + item.getNamaPosition());
                    updateJobData(item.getNamaPosition(), item.getIdJobPosition());
                }
            }
        });
    }

    private void unupdateJobData(String jobname, String idjob) {
        mApiService.updateJobData(passing_uid, jobname, idjob ,"N")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                String pesanError = jsonRESULTS.getString("code");

                                if (pesanError.equals("200")) {
                                    Log.d("create uid job", "onResponseUNCHECK: " );
                                } else if (pesanError.equals("201")) {
                                    Log.d("create job", "onResponseUNCHECK: " );
                                } else if (pesanError.equals("202")) {
                                    Log.d("update", "onResponseUNCHECK: " );
                                }else {
                                    Log.d("error", "onResponseUNCHECK: ");
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }

                        } else {
//                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //Log.e("debug", "onFailure: ERROR > " + t.toString());
//                        loading.dismiss();
                    }
                });


    }

    private void updateJobData(String jobname, String idjob) {
        mApiService.updateJobData(passing_uid, jobname, idjob,"Y")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                            loading.dismiss();

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                //Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();

                                String pesanError = jsonRESULTS.getString("code");

                                if (pesanError.equals("200")) {
                                    Log.d("create uid job", "onResponse: " );
                                } else if (pesanError.equals("201")) {
                                    Log.d("create job", "onResponse: " );
                                } else if (pesanError.equals("202")) {
                                    Log.d("update", "onResponse: " );
                                }else {
                                    Log.d("error", "onResponse: ");
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }

                        } else {
//                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //Log.e("debug", "onFailure: ERROR > " + t.toString());
//                        loading.dismiss();
                    }
                });


    }


    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class AssessHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tvTitle;
        LinearLayout card;
        ImageView ceklis, iv;

        private AssessHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            iv = itemView.findViewById(R.id.iv_post);
            ceklis = itemView.findViewById(R.id.checklist);
            tvTitle = itemView.findViewById(R.id.tv_post);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<JobPositionItem> getItems() {
        return dataItemList;
    }


    public JobPositionItem getItem(int position) {
        return dataItemList.get(position);
    }
}