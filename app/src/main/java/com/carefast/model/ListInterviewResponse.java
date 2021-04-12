package com.carefast.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ListInterviewResponse{

	@SerializedName("interview")
	private List<InterviewItem> interview;

	public List<InterviewItem> getInterview(){
		return interview;
	}
}