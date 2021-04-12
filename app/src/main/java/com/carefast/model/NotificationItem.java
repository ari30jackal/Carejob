package com.carefast.model;

import com.google.gson.annotations.SerializedName;

public class NotificationItem{

	@SerializedName("advertisement_code")
	private String advertisementCode;
	@SerializedName("id_notification")
	private String idNotification;
	@SerializedName("status_review")
	private String statusReview;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("id_advertisement")
	private String idAdvertisement;

	@SerializedName("icon_image")
	private String iconImage;

	@SerializedName("nama_position")
	private String namaPosition;

	public String getAdvertisementCode(){
		return advertisementCode;
	}

	public String getStatusReview(){
		return statusReview;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getIdAdvertisement(){
		return idAdvertisement;
	}

	public String getIdNotification() {
		return idNotification;
	}

	public String getIconImage(){
		return iconImage;
	}

	public String getNamaPosition(){
		return namaPosition;
	}
}