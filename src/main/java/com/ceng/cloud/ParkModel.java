package com.ceng.cloud;

import com.google.gson.JsonObject;

public class ParkModel {
	
	private long parkID;
	private String parkName;
	private double latitude;
	private double longitude;
	private int capacity;
	private int availableSpace;
	private String parkType;
	private String district;
	private double distance;
	private int freeParkingMin;
	
	public ParkModel(JsonObject jsonObject) {
		this.parkID = jsonObject.get("ParkID").getAsLong();
		this.parkName = jsonObject.get("ParkAdi").getAsString();
		this.latitude = Double.parseDouble(jsonObject.get("Latitude").getAsString());
		this.longitude = Double.parseDouble(jsonObject.get("Longitude").getAsString());
		this.capacity = jsonObject.get("Kapasitesi").getAsInt();
		this.availableSpace = jsonObject.get("BosKapasite").getAsInt();
		this.parkType = jsonObject.get("ParkTipi").getAsString();
		this.district = jsonObject.get("Ilce").getAsString();
		this.distance = jsonObject.get("Distance").getAsDouble();
		this.freeParkingMin = jsonObject.get("UcretsizParklanmaDk").getAsInt();
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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getAvailableSpace() {
		return availableSpace;
	}

	public void setAvailableSpace(int availableSpace) {
		this.availableSpace = availableSpace;
	}

	public String getParkType() {
		return parkType;
	}

	public void setParkType(String parkType) {
		this.parkType = parkType;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getFreeParkingMin() {
		return freeParkingMin;
	}

	public void setFreeParkingMin(int freeParkingMin) {
		this.freeParkingMin = freeParkingMin;
	}
	
}
