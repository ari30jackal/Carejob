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
import com.carefast.detailjob.DetailJobjadwalActivity;
import com.carefast.model.InterviewItem;
import com.carefast.register.AdapterOnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class ListInterviewAdapter extends RecyclerView.Adapter<ListInterviewAdapter.AssessHolder> {
    List<InterviewItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private ListInterviewAdapter.OnItemClicked onClick;
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


    public ListInterviewAdapter(Context context, List<InterviewItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public ListInterviewAdapter.AssessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listinterviewcard, parent, false);

        return new ListInterviewAdapter.AssessHolder(itemView, mListener);
    }


    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(final ListInterviewAdapter.AssessHolder holder, @SuppressLint("RecyclerView") final int position) {

        final InterviewItem item = dataItemList.get(position);

        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);
holder.tvjabatan.setText(item.getNamaPosition());
holder.tvinterviewer.setText(item.getAdminMasterName());
holder.tvplacement.setText(item.getLocation());
holder.tvkodelowongan.setText(item.getAdvertisementCode());
holder.tvdate.setText(item.getDateSchedule());
holder.tvtime.setText(item.getStartTime() + " - "+item.getEndTime());
        Picasso.get()
                .load(mContext.getResources().getString(R.string.base_url_asset_interview) +item.getIconImage())
                .into(holder.ivicon);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailJobjadwalActivity.class);
                mContext.startActivity(intent);

//                Toast.makeText(mContext, "sch  =  "+item.getIdInterviewSchedule()+"adv   =  "+item.getIdAdvertisement(), Toast.LENGTH_SHORT).show();
                try {
pref.put(PrefContract.sch,item.getIdInterviewSchedule());
pref.put(PrefContract.adv,item.getIdAdvertisement());
                    pref.put(PrefContract.id_interview,item.getIdInterviewSchedule());
                    pref.put(PrefContract.id_schedule,item.getIdInterviewSchedule());
                    pref.put(PrefContract.id_advertisement,item.getIdAdvertisement());
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


        TextView tvjabatan,tvplacement,tvdate,tvkodelowongan,tvinterviewer,tvtime;
        CardView card;
        ImageView ivicon;

        private AssessHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            mApiService = UtilsApi.getAPIService();
            mListener = listener;
            context = itemView.getContext();
         tvjabatan = itemView.findViewById(R.id.tvjabataninterview);
         tvplacement = itemView.findViewById(R.id.tvlocationinterview);
         tvdate=itemView.findViewById(R.id.tv_date);
         tvkodelowongan = itemView.findViewById(R.id.tvkodelowinterview);
         tvinterviewer = itemView.findViewById(R.id.tvinterviewer);
         tvtime = itemView.findViewById(R.id.tv_hour);
card = itemView.findViewById(R.id.card);
            ivicon = itemView.findViewById(R.id.iv_icon);
        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<InterviewItem> getItems() {
        return dataItemList;
    }


    public InterviewItem getItem(int position) {
        return dataItemList.get(position);
    }
}