package com.carefast.model;

import com.google.gson.annotations.SerializedName;

public class AdvertisementbyidItem{

	@SerializedName("advertisement_code")
	private String advertisementCode;

	@SerializedName("end_date")
	private String endDate;

	@SerializedName("id_advertisement")
	private String idAdvertisement;

	@SerializedName("job_description")
	private String jobDescription;

	@SerializedName("qrcode")
	private String qrcode;

	@SerializedName("nama_position")
	private String namaPosition;

	@SerializedName("placement")
	private String placement;

	@SerializedName("id_branch_advert")
	private String idBranchAdvert;

	@SerializedName("advert_image")
	private String advertImage;

	@SerializedName("id_job_position_advert")
	private String idJobPositionAdvert;

	@SerializedName("start_date")
	private String startDate;

	public String getAdvertisementCode(){
		return advertisementCode;
	}

	public String getEndDate(){
		return endDate;
	}

	public String getIdAdvertisement(){
		return idAdvertisement;
	}

	public String getJobDescription(){
		return jobDescription;
	}

	public String getQrcode(){
		return qrcode;
	}

	public String getNamaPosition(){
		return namaPosition;
	}

	public String getPlacement(){
		return placement;
	}

	public String getIdBranchAdvert(){
		return idBranchAdvert;
	}

	public String getAdvertImage(){
		return advertImage;
	}

	public String getIdJobPositionAdvert(){
		return idJobPositionAdvert;
	}

	public String getStartDate(){
		return startDate;
	}
}