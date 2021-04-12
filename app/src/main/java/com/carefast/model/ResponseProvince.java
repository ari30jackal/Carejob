package com.carefast.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseProvince{

	@SerializedName("Province")
	private List<ProvinceItem> province;

	public List<ProvinceItem> getProvince(){
		return province;
	}
}