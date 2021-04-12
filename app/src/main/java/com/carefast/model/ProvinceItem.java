package com.carefast.model;

import com.google.gson.annotations.SerializedName;

public class ProvinceItem{

	@SerializedName("prov_id")
	private String provId;

	@SerializedName("prov_name")
	private String provName;

	@SerializedName("locationid")
	private String locationid;

	@SerializedName("status")
	private String status;

	public String getProvId(){
		return provId;
	}

	public String getProvName(){
		return provName;
	}

	public String getLocationid(){
		return locationid;
	}

	public String getStatus(){
		return status;
	}
}