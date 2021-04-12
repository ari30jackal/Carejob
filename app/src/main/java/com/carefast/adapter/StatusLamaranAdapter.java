package com.carefast.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.carefast.Login.R;
import com.carefast.apihelper.BaseApiService;
import com.carefast.apihelper.UtilsApi;
import com.carefast.contract.AppException;
import com.carefast.contract.PrefContract;
import com.carefast.contract.SecuredPreference;
import com.carefast.detailjob.DetailJobActivity;
import com.carefast.detailjob.DetailJobAfterActivity;
import com.carefast.detailjob.DetailJobjadwalActivity;
import com.carefast.model.AdvertisementItem;
import com.carefast.model.CityItem;
import com.carefast.model.ListLamaranItem;
import com.carefast.model.ProvinceItem;
import com.carefast.register.AdapterOnItemClickListener;
import com.carefast.register.CityAdapter;
import com.carefast.register.TahapDuaActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatusLamaranAdapter extends RecyclerView.Adapter<StatusLamaranAdapter.AssessHolder> {
    List<ListLamaranItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private StatusLamaranAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context context;
    SecuredPreference pref;
    String stat;
    String passCek, role;
    Date strdate;
    Date waktudetik;
    Date waktuuntil;
    Date sdf3;
    Date waktuhariini;
    FragmentManager fragmentManager;
    FragmentTransaction ft;

    public interface OnItemClicked {
        void onItemClick(int position);
    }


    public StatusLamaranAdapter(Context context, List<ListLamaranItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public StatusLamaranAdapter.AssessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.statuslamarancard, parent, false);

        return new StatusLamaranAdapter.AssessHolder(itemView, mListener);
    }


    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(final StatusLamaranAdapter.AssessHolder holder, @SuppressLint("RecyclerView") final int position) {

        final ListLamaranItem item = dataItemList.get(position);

        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);
        holder.tvkodelowongan.setText(item.getAdvertisementCode());
        holder.tvperiode.setText(" " + item.getStartDate() + " - " + item.getEndDate());

        holder.tvplacement.setText(item.getPlacement());
        holder.tvjabatan.setText(item.getNamaPosition());

        stat = item.getStatusReview();
        if (stat.equals("Approved")) {
            holder.tvstatus.setTextColor(Color.parseColor("#16AA9D"));
            holder.tvstatus.setBackgroundColor(Color.parseColor("#C0EFEA"));

        } else if (stat.equals("Confirmed")) {
            holder.tvstatus.setTextColor(Color.parseColor("#FFAE00"));
            holder.tvstatus.setBackgroundColor(Color.parseColor("#FFF4E8"));
        } else if (stat.equals("Not Approved")) {

            holder.tvstatus.setTextColor(Color.parseColor("#E76A6B"));
            holder.tvstatus.setBackgroundColor(Color.parseColor("#FFECED"));
        } else if (stat.equals("Canceled")) {
            holder.tvstatus.setTextColor(Color.parseColor("#707070"));
            holder.tvstatus.setBackgroundColor(Color.parseColor("#E1E1E1"));

        } else if (stat.equals("On Process")) {
            holder.tvstatus.setTextColor(Color.parseColor("#0030DF"));
            holder.tvstatus.setBackgroundColor(Color.parseColor("#D7E0FF"));

        } else if (stat.equals("Failed")) {
        } else if (stat.equals("Passed")) {
        }


        holder.tvstatus.setText(item.getStatusReview());
//        'Approve','Not Approve','Confirm','Cancel','On Process','Failed','Passed'
        Picasso.get()
                .load(mContext.getResources().getString(R.string.base_url_asset_interview) +item.getIconImage())
                .into(holder.ivicon);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (stat.equals("On Process") || stat.equals("Not Approved")){
                    Intent intent = new Intent(mContext, DetailJobAfterActivity.class);
                    mContext.startActivity(intent);



                }
                else{
                    Intent intent = new Intent(mContext, DetailJobjadwalActivity.class);
                    mContext.startActivity(intent);

                }



                try {
                    pref.put(PrefContract.id_lamaran, item.getIdUserAdvertisement());
                    pref.put(PrefContract.id_advertisement, item.getIdAdvertisement());
                    pref.put(PrefContract.sch,item.getIdScheduleInterview());
                    pref.put(PrefContract.id_schedule,item.getIdScheduleInterview());
                    pref.put(PrefContract.adv,item.getIdAdvertisement());
                    pref.put(PrefContract.putreason, item.getReasonReview());
                    pref.put(PrefContract.putstatus, item.getStatusReview());
                    pref.put(PrefContract.putplace, item.getPlacement());
                    pref.put(PrefContract.putjabatan, item.getNamaPosition());
                    pref.put(PrefContract.id_interview, item.getIdScheduleInterview());
                    pref.put(PrefContract.putimgjabatan, item.getIconImage());
                } catch (AppException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getprogres() {
    }


    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class AssessHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tvjabatan, tvplacement, tvstatus, tvperiode, tvkodelowongan;
        ImageView ivicon;
        CardView card;


        private AssessHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mApiService = UtilsApi.getAPIService();
            mListener = listener;
            context = itemView.getContext();

            card = itemView.findViewById(R.id.card);
            tvjabatan = itemView.findViewById(R.id.tvjabatanstatus);
            tvplacement = itemView.findViewById(R.id.tvlocationstatus);
            ivicon = itemView.findViewById(R.id.iv_iconstatuslamaran);
            tvstatus = itemView.findViewById(R.id.tv_statuslamaran);
            tvperiode = itemView.findViewById(R.id.tvperiodestatus);
            tvkodelowongan = itemView.findViewById(R.id.tvkodelowstatus);


        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<ListLamaranItem> getItems() {
        return dataItemList;
    }


    public ListLamaranItem getItem(int position) {
        return dataItemList.get(position);
    }
}