package com.carefast.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class JobPositionResponse{

	@SerializedName("JobPosition")
	private List<JobPositionItem> jobPosition;

	public List<JobPositionItem> getJobPosition(){
		return jobPosition;
	}
}