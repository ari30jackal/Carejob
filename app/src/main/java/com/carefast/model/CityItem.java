package com.carefast.model;

import com.google.gson.annotations.SerializedName;

public class CityItem{

	@SerializedName("city_name")
	private String cityName;

	@SerializedName("prov_id")
	private String provId;

	@SerializedName("city_id")
	private String cityId;

	public String getCityName(){
		return cityName;
	}

	public String getProvId(){
		return provId;
	}

	public String getCityId(){
		return cityId;
	}
}