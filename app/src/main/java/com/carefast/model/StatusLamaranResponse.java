package com.carefast.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class StatusLamaranResponse{

	@SerializedName("ListLamaran")
	private List<ListLamaranItem> listLamaran;

	public List<ListLamaranItem> getListLamaran(){
		return listLamaran;
	}
}