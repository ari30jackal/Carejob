package com.carefast.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CitiesResponse{

	@SerializedName("City")
	private List<CityItem> city;

	public List<CityItem> getCity(){
		return city;
	}
}