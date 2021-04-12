package com.carefast.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NotificationResponse{

	@SerializedName("notification")
	private List<NotificationItem> notification;

	public List<NotificationItem> getNotification(){
		return notification;
	}
}