package com.carefast.register;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.carefast.model.CityItem;
import com.carefast.model.ProvinceItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.AssessHolder> {
    List<CityItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private CityAdapter.OnItemClicked onClick;
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
    String id;
    FragmentManager fragmentManager;
    FragmentTransaction ft;

    public interface OnItemClicked {
        void onItemClick(int position);
    }


    public CityAdapter(Context context, List<CityItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public CityAdapter.AssessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_province, parent, false);

        return new CityAdapter.AssessHolder(itemView, mListener);
    }


    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(final CityAdapter.AssessHolder holder, @SuppressLint("RecyclerView") final int position) {

        final CityItem item = dataItemList.get(position);

        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);

        try {
            id=pref.getString(PrefContract.city_id_choose,"");
        } catch (AppException e) {
            e.printStackTrace();
        }


        if (id.equals(item.getCityId())){

            holder.cek.setVisibility(View.VISIBLE);
        }
        else{
            holder.cek.setVisibility(View.INVISIBLE);
        }

        holder.tvTitle.setText(item.getCityName());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    pref.put(PrefContract.city_id_choose, item.getCityId());
                    pref.put(PrefContract.city_name_choose, item.getCityName());


                } catch (AppException e) {
                    e.printStackTrace();
                }

                ((Activity) mContext).finish();
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


        TextView tvTitle;
        LinearLayout card;
ImageView cek;

        private AssessHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mApiService = UtilsApi.getAPIService();
            mListener = listener;
            context = itemView.getContext();
//
            cek =itemView.findViewById(R.id.cek);
            tvTitle = itemView.findViewById(R.id.tv_prov);
            card = itemView.findViewById(R.id.card1);

        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<CityItem> getItems() {
        return dataItemList;
    }


    public CityItem getItem(int position) {
        return dataItemList.get(position);
    }
}