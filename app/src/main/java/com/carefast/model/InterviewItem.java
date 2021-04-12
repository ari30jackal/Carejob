package com.carefast.model;

import com.google.gson.annotations.SerializedName;

public class InterviewItem{
@SerializedName("id_interview_schedule")
private String idInterviewSchedule;
	@SerializedName("advertisement_code")
	private String advertisementCode;
	@SerializedName("location")
	private String location;
	@SerializedName("start_time")
	private String startTime;

	@SerializedName("admin_master_name")
	private String adminMasterName;

	@SerializedName("id_advertisement")
	private String idAdvertisement;

	@SerializedName("end_time")
	private String endTime;

	@SerializedName("nama_position")
	private String namaPosition;

	@SerializedName("date_schedule")
	private String dateSchedule;
	@SerializedName("icon_image")
	private String iconImage;
	public String getIconImage() {
		return iconImage;
	}
	public String getAdvertisementCode(){
		return advertisementCode;
	}

	public String getStartTime(){
		return startTime;
	}

	public String getAdminMasterName(){
		return adminMasterName;
	}

	public String getIdAdvertisement(){
		return idAdvertisement;
	}

	public String getEndTime(){
		return endTime;
	}

	public String getIdInterviewSchedule() {
		return idInterviewSchedule;
	}

	public void setIdInterviewSchedule(String idInterviewSchedule) {
		this.idInterviewSchedule = idInterviewSchedule;
	}

	public void setAdvertisementCode(String advertisementCode) {
		this.advertisementCode = advertisementCode;
	}

	public String getLocation() {
		return location;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setAdminMasterName(String adminMasterName) {
		this.adminMasterName = adminMasterName;
	}

	public void setIdAdvertisement(String idAdvertisement) {
		this.idAdvertisement = idAdvertisement;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setNamaPosition(String namaPosition) {
		this.namaPosition = namaPosition;
	}

	public void setDateSchedule(String dateSchedule) {
		this.dateSchedule = dateSchedule;
	}

	public String getNamaPosition(){
		return namaPosition;
	}

	public String getDateSchedule(){
		return dateSchedule;
	}
}