package com.carefast.model;

import com.google.gson.annotations.SerializedName;

public class AdvertisementItem{
@SerializedName("nama_position")
private String jobposition;


	@SerializedName("end_date")
	private String endDate;
@SerializedName("advertisement_code")
private String advertisementcode;
	@SerializedName("id_advertisement")
	private String idAdvertisement;

	@SerializedName("job_description")
	private String jobDescription;

	@SerializedName("qrcode")
	private String qrcode;

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
	@SerializedName("icon_image")
	private String iconImage;

	public String getIconImage() {
		return iconImage;
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

	public String getPlacement(){
		return placement;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAdvertisementcode() {
		return advertisementcode;
	}

	public void setAdvertisementcode(String advertisementcode) {
		this.advertisementcode = advertisementcode;
	}

	public void setIdAdvertisement(String idAdvertisement) {
		this.idAdvertisement = idAdvertisement;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getJobposition() {
		return jobposition;
	}

	public void setJobposition(String jobposition) {
		this.jobposition = jobposition;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public void setPlacement(String placement) {
		this.placement = placement;
	}

	public void setIdBranchAdvert(String idBranchAdvert) {
		this.idBranchAdvert = idBranchAdvert;
	}

	public void setAdvertImage(String advertImage) {
		this.advertImage = advertImage;
	}

	public void setIdJobPositionAdvert(String idJobPositionAdvert) {
		this.idJobPositionAdvert = idJobPositionAdvert;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
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