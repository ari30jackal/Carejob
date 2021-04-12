package com.carefast.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PostalcodeResponse{

	@SerializedName("PostalCode")
	private List<PostalCodeItem> postalCode;

	public List<PostalCodeItem> getPostalCode(){
		return postalCode;
	}
}