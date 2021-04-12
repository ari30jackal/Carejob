package com.carefast.model;

import com.google.gson.annotations.SerializedName;

public class DistrictsItem{

	@SerializedName("dis_name")
	private String disName;

	@SerializedName("dis_id")
	private String disId;

	@SerializedName("city_id")
	private String cityId;

	public String getDisName(){
		return disName;
	}

	public String getDisId(){
		return disId;
	}

	public String getCityId(){
		return cityId;
	}
}