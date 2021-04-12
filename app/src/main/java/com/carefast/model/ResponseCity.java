package com.carefast.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCity{

    @SerializedName("City")
    private List<CityItem> City;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public void setCity(List<CityItem> City){
        this.City = City;
    }

    public List<CityItem> getCity(){
        return City;
    }

    public void setError(boolean error){
        this.error = error;
    }

    public boolean isError(){
        return error;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString(){
        return
                "ResponseCity{" +
                        "City = '" + City + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}