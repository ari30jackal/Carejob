package com.carefast.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.carefast.model.AdvertisementItem;
import com.carefast.register.AdapterOnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class AdvertisementAdapterRekomendasiLoker extends RecyclerView.Adapter<AdvertisementAdapterRekomendasiLoker.AssessHolder> {
    List<AdvertisementItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private AdvertisementAdapterRekomendasiLoker.OnItemClicked onClick;
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

    public interface OnItemClicked {
        void onItemClick(int position);
    }


    public AdvertisementAdapterRekomendasiLoker(Context context, List<AdvertisementItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public AdvertisementAdapterRekomendasiLoker.AssessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.advertiesementcardrekomendasiloker, parent, false);

        return new AdvertisementAdapterRekomendasiLoker.AssessHolder(itemView, mListener);
    }


    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(final AdvertisementAdapterRekomendasiLoker.AssessHolder holder, @SuppressLint("RecyclerView") final int position) {

        final AdvertisementItem item = dataItemList.get(position);

        if (position == 0){

holder.status.setVisibility(View.VISIBLE);
        }
        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);
holder.tvkodelowongan.setText(item.getAdvertisementcode());
holder.tvjabatan.setText(item.getJobposition());
holder.tvperiode.setText(item.getStartDate() + " - "+item.getEndDate());
holder.tvplacement.setText(item.getPlacement());
        Picasso.get()
                .load(mContext.getResources().getString(R.string.base_url_asset_interview) +item.getIconImage())
                .into(holder.ivicon);
holder.card.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mContext, DetailJobActivity.class);
        mContext.startActivity(intent);
        try {
            pref.put(PrefContract.desc_advertisement,item.getJobDescription());
            pref.put(PrefContract.id_advertisement,item.getIdAdvertisement());
            pref.put(PrefContract.position_advertisement,item.getJobposition());
            pref.put(PrefContract.placement_advertisement,item.getPlacement());
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


        TextView tvjabatan,tvplacement,tvperiode,tvkodelowongan,status;
        ImageView ivicon;
       CardView card;


        private AssessHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mApiService = UtilsApi.getAPIService();
            mListener = listener;
            context = itemView.getContext();
tvjabatan = itemView.findViewById(R.id.tv_jabatan);
card = itemView.findViewById(R.id.card);
tvplacement = itemView.findViewById(R.id.tv_placement);
tvperiode = itemView.findViewById(R.id.tv_periode);
tvkodelowongan=itemView.findViewById(R.id.tv_kodelowongan);
status = itemView.findViewById(R.id.tv_status);
ivicon = itemView.findViewById(R.id.iv_icon);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<AdvertisementItem> getItems() {
        return dataItemList;
    }


    public AdvertisementItem getItem(int position) {
        return dataItemList.get(position);
    }
}