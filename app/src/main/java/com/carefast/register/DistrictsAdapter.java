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
import com.carefast.model.DistrictsItem;
import com.carefast.model.ProvinceItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DistrictsAdapter extends RecyclerView.Adapter<DistrictsAdapter.AssessHolder> {
    List<DistrictsItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private DistrictsAdapter.OnItemClicked onClick;
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
    String id;

    public interface OnItemClicked {
        void onItemClick(int position);
    }


    public DistrictsAdapter(Context context, List<DistrictsItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public DistrictsAdapter.AssessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_province, parent, false);

        return new DistrictsAdapter.AssessHolder(itemView, mListener);
    }


    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(final DistrictsAdapter.AssessHolder holder, @SuppressLint("RecyclerView") final int position) {

        final DistrictsItem item = dataItemList.get(position);

        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);
        try {
            id=pref.getString(PrefContract.districts_id_choose,"");
        } catch (AppException e) {
            e.printStackTrace();
        }


        if (id.equals(item.getDisId())){

            holder.cek.setVisibility(View.VISIBLE);
        }
        else{
            holder.cek.setVisibility(View.INVISIBLE);
        }
        holder.tvTitle.setText(item.getDisName());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    pref.put(PrefContract.districts_id_choose, item.getDisId());
                    pref.put(PrefContract.districts_name_choose, item.getDisName());


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
            tvTitle = itemView.findViewById(R.id.tv_prov);
            card = itemView.findViewById(R.id.card1);
cek = itemView.findViewById(R.id.cek);
        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<DistrictsItem> getItems() {
        return dataItemList;
    }


    public DistrictsItem getItem(int position) {
        return dataItemList.get(position);
    }
}