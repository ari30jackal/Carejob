package com.carefast.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RegencyResponse{

	@SerializedName("Districts")
	private List<DistrictsItem> districts;

	public List<DistrictsItem> getDistricts(){
		return districts;
	}
}