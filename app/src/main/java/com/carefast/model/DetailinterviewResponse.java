package com.carefast.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailinterviewResponse{

	@SerializedName("Detailinterview")
	private List<DetailinterviewItem> detailinterview;

	public List<DetailinterviewItem> getDetailinterview(){
		return detailinterview;
	}
}