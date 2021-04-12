package com.carefast.model;

import com.google.gson.annotations.SerializedName;

public class ListLamaranItem{
	@SerializedName("id_advertisement")
	private String idAdvertisement;
	@SerializedName("advertisement_code")
	private String advertisementCode;
@SerializedName("id_user_advertisement")
private String idUserAdvertisement;
	@SerializedName("id_schedule_interview")
	private String idScheduleInterview;
	@SerializedName("end_date")
	private String endDate;
	@SerializedName("reason_review")
	private String reasonReview;
	@SerializedName("status_review")
	private String statusReview;

	@SerializedName("icon_image")
	private String iconImage;

	@SerializedName("nama_position")
	private String namaPosition;

	@SerializedName("placement")
	private String placement;

	@SerializedName("start_date")
	private String startDate;


	public String getAdvertisementCode(){
		return advertisementCode;
	}

	public String getEndDate(){
		return endDate;
	}

	public String getStatusReview(){
		return statusReview;
	}

	public String getIconImage(){
		return iconImage;
	}

	public String getReasonReview() {
		return reasonReview;
	}

	public String getIdScheduleInterview() {
		return idScheduleInterview;
	}

	public String getNamaPosition(){
		return namaPosition;
	}

	public String getIdUserAdvertisement() {
		return idUserAdvertisement;
	}

	public String getPlacement(){
		return placement;
	}

	public String getStartDate(){
		return startDate;
	}

	public String getIdAdvertisement() {
		return idAdvertisement;
	}
}