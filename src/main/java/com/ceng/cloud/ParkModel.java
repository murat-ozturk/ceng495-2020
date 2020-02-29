package com.ceng.cloud;

import com.google.gson.JsonObject;

public class ParkModel {
	
	private long parkID;
	private String parkName;
	private String district;
	
	public ParkModel(JsonObject jsonObject) {
		this.parkID = jsonObject.get("ParkID").getAsLong();
		this.parkName = jsonObject.get("ParkAdi").getAsString();
		this.district = jsonObject.get("Ilce").getAsString();
	}

	public long getParkID() {
		return parkID;
	}

	public void setParkID(long parkID) {
		this.parkID = parkID;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
}
