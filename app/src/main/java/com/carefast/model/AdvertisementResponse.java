package com.carefast.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AdvertisementResponse{

	@SerializedName("Advertisement")
	private List<AdvertisementItem> advertisement;

	public List<AdvertisementItem> getAdvertisement(){
		return advertisement;
	}
}