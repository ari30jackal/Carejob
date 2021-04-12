package com.carefast.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
import com.carefast.model.AdvertisementItem;
import com.carefast.model.NotificationItem;
import com.carefast.register.AdapterOnItemClickListener;
import com.carefast.register.TahapEmpatActivity;
import com.carefast.register.TahapLimaActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.AssessHolder> {
    List<NotificationItem> dataItemList;
    Context mContext;
    RecyclerView mRecyclerView;
    AdapterView.OnItemClickListener listener;
    private NotifAdapter.OnItemClicked onClick;
    private AdapterOnItemClickListener mListener;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context context;
    SecuredPreference pref;
    String passCek, role;
    private List<?> data = new ArrayList<>();
    Date strdate;
    Date waktudetik;
    Date waktuuntil;
    String status;
    String  kd,jabatan;
    Date sdf3;
    Date waktuhariini;
    String idnotif;
    FragmentManager fragmentManager;
    FragmentTransaction ft;
    private ItemClickListener mClickListener;
    public interface OnItemClicked {
        void onItemClick(int position);
    }
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    public NotifAdapter(Context context, List<NotificationItem> dataItem, AdapterOnItemClickListener listener) {
        this.mContext = context;
        dataItemList = dataItem;
        this.mListener = listener;
        /*this.tListener = listener2;*/
    }

    @Override
    public NotifAdapter.AssessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifcard, parent, false);

        return new NotifAdapter.AssessHolder(itemView, mListener);
    }


    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(final NotifAdapter.AssessHolder holder, @SuppressLint("RecyclerView") final int position) {

        final NotificationItem item = dataItemList.get(position);
        mApiService = UtilsApi.getAPIService();
        pref = new SecuredPreference(mContext, PrefContract.PREF_EL);
        idnotif = item.getIdNotification();
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:

                                mApiService.deletenotif(dataItemList.get(position).getIdNotification())
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
                                                            Toast.makeText(context, "Berhasil Menghapus data", Toast.LENGTH_SHORT).show();
                                                            Log.d("create uid job", "onResponse: " );
                                                            removeItem(position);

                                                        } else {
                                                            Toast.makeText(context, "Gagal Menghapus data", Toast.LENGTH_SHORT).show();
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





                                return true;

                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

            }
        });


//        myTextView.setText(Html.fromHtml("<b>" + myText + "</b>");
        kd=item.getAdvertisementCode();
        jabatan = item.getNamaPosition();
        status = item.getStatusReview();
        if (status.equals("Approved")){
            holder.tvnotif.setText(Html.fromHtml("Selamat! Berkas lamaran Anda lolos untuk posisi <b>"+jabatan+" "+kd+"</b> .Untuk detailnya, silakan cek pada menu Jobs."));
        }
        else if (status.equals("Not Approved")){
            holder.tvnotif.setText(Html.fromHtml("Mohon maaf, berkas lamaran Anda tidak lolos untuk posisi <b>"+jabatan+" "+kd+"</b> .Untuk detailnya, silakan cek pada menu Jobs."));
        }
        else if (status.equals("Confirmed")){
            holder.tvnotif.setText(Html.fromHtml("Anda telah melakukan konfirmasi jadwal interview untuk posisi <b>"+jabatan+" "+kd+"</b> .Untuk detailnya, silakan cek pada menu Jobs."));
        }
        else if (status.equals("Canceled")){
            holder.tvnotif.setText(Html.fromHtml("Anda telah melakukan pembatalan lamaran pekerjaan untuk <b>"+jabatan+" "+kd+"</b> .Untuk detailnya, silakan cek pada menu Jobs."));
        }
        else if (status.equals("On Process")){
            holder.tvnotif.setText(Html.fromHtml("Lamaran Pekerjaan Anda untuk posisi <b>"+jabatan+" "+kd+"</b>, berhasil terkirim. Mohon menunggu proses scanning lamaran oleh admin.Untuk detailnya, silakan cek pada menu Jobs."));
        }
        else if (status.equals("Passed")){
            holder.tvnotif.setText(Html.fromHtml("Selamat! Anda lolos tahap Interview untuk posisi <b>"+jabatan+" "+kd+"</b> .Untuk detailnya, silakan cek pada menu Jobs."));
        }
        else if (status.equals("Failed")){
            holder.tvnotif.setText(Html.fromHtml("Mohon Maaf. Anda tidak lolos tahap Interview untuk posisi <b>"+jabatan+" "+kd+"</b>. Untuk detailnya, silakan cek pada menu Jobs."));
        }


        holder.tvwaktu.setText(item.getUpdatedAt());
        Picasso.get()
                .load(mContext.getResources().getString(R.string.base_url_asset_interview) +item.getIconImage())
                .into(holder.ivicon);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
    public void updateData(List data) {
        this.data = data;
        notifyDataSetChanged();
    }
    public void removeItem(int position) {
        dataItemList.remove(position);
        notifyDataSetChanged();
    }
    private void getprogres() {
    }


    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class AssessHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView buttonViewOption,tvnotif,tvwaktu;
        CardView card;
        ImageView ivicon;

        private AssessHolder(View itemView, AdapterOnItemClickListener listener) {

            super(itemView);
            itemView.setOnClickListener(this);
            mApiService = UtilsApi.getAPIService();
            mListener = listener;
            context = itemView.getContext();

            tvnotif = itemView.findViewById(R.id.tv_notif);
            buttonViewOption = itemView.findViewById(R.id.textViewOptions);
            tvwaktu = itemView.findViewById(R.id.tv_waktu);
            card = itemView.findViewById(R.id.card);


            ivicon = itemView.findViewById(R.id.iv_icon);
        }

        @Override
        public void onClick(View itemView) {
            mListener.onClick(itemView, getAdapterPosition());
        }
    }

    public List<NotificationItem> getItems() {
        return dataItemList;
    }


    public NotificationItem getItem(int position) {
        return dataItemList.get(position);
    }
}