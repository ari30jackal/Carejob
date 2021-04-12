package com.carefast.model;

import com.google.gson.annotations.SerializedName;

public class DetailinterviewItem{

	@SerializedName("start_time")
	private String startTime;

	@SerializedName("created_at_interview")
	private String createdAtInterview;

	@SerializedName("end_time")
	private String endTime;

	@SerializedName("qrcode_interview")
	private String qrcodeInterview;

	@SerializedName("location")
	private String location;

	@SerializedName("confirmation_date")
	private String confirmationDate;

	@SerializedName("date_schedule")
	private String dateSchedule;

	@SerializedName("updated_at_interview")
	private String updatedAtInterview;

	@SerializedName("id_interview")
	private String idInterview;

	@SerializedName("id_advertisement_code")
	private String idAdvertisementCode;

	public String getStartTime(){
		return startTime;
	}

	public String getCreatedAtInterview(){
		return createdAtInterview;
	}

	public String getEndTime(){
		return endTime;
	}

	public String getQrcodeInterview(){
		return qrcodeInterview;
	}

	public String getLocation(){
		return location;
	}

	public String getConfirmationDate(){
		return confirmationDate;
	}

	public String getDateSchedule(){
		return dateSchedule;
	}

	public String getUpdatedAtInterview(){
		return updatedAtInterview;
	}

	public String getIdInterview(){
		return idInterview;
	}

	public String getIdAdvertisementCode(){
		return idAdvertisementCode;
	}
}