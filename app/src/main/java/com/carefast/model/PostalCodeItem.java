package com.carefast.model;

import com.google.gson.annotations.SerializedName;

public class PostalCodeItem{

	@SerializedName("subdis_id")
	private String subdisId;

	@SerializedName("postal_id")
	private String postalId;

	@SerializedName("prov_id")
	private String provId;

	@SerializedName("postal_code")
	private String postalCode;

	@SerializedName("dis_id")
	private String disId;

	@SerializedName("city_id")
	private String cityId;

	public String getSubdisId(){
		return subdisId;
	}

	public String getPostalId(){
		return postalId;
	}

	public String getProvId(){
		return provId;
	}

	public String getPostalCode(){
		return postalCode;
	}

	public String getDisId(){
		return disId;
	}

	public String getCityId(){
		return cityId;
	}
}