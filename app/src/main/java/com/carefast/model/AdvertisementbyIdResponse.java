package com.carefast.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AdvertisementbyIdResponse{

	@SerializedName("Advertisementbyid")
	private List<AdvertisementbyidItem> advertisementbyid;

	public List<AdvertisementbyidItem> getAdvertisementbyid(){
		return advertisementbyid;
	}
}