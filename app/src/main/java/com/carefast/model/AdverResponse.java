package com.carefast.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AdverResponse{

	@SerializedName("Advertisement")
	private List<AdvertisementItemItemItem> advertisement;

	public List<AdvertisementItemItemItem> getAdvertisement(){
		return advertisement;
	}
}