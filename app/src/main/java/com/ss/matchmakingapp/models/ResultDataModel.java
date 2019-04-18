package com.ss.matchmakingapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Shubham Singhal
 */
public class ResultDataModel implements Serializable {

	@SerializedName("gender")
	@Expose
	private String gender;

	@SerializedName("name")
	@Expose
	private NameModel name;

	@SerializedName("location")
	@Expose
	private LocationModel location;

	@SerializedName("email")
	@Expose
	private String email;

	@SerializedName("dob")
	@Expose
	private DOBModel dob;

	@SerializedName("phone")
	@Expose
	private String phone;

	@SerializedName("cell")
	@Expose
	private String cell;

	@SerializedName("picture")
	@Expose
	private PictureModel picture;

	@SerializedName("nat")
	@Expose
	private String nat;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public NameModel getName() {
		return name;
	}

	public void setName(NameModel name) {
		this.name = name;
	}

	public LocationModel getLocation() {
		return location;
	}

	public void setLocation(LocationModel location) {
		this.location = location;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public DOBModel getDob() {
		return dob;
	}

	public void setDob(DOBModel dob) {
		this.dob = dob;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public PictureModel getPicture() {
		return picture;
	}

	public void setPicture(PictureModel picture) {
		this.picture = picture;
	}

	public String getNat() {
		return nat;
	}

	public void setNat(String nat) {
		this.nat = nat;
	}


	public static class NameModel implements Serializable {

		@SerializedName("title")
		@Expose
		private String title;

		@SerializedName("first")
		@Expose
		private String first;

		@SerializedName("last")
		@Expose
		private String last;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getFirst() {
			return first;
		}

		public void setFirst(String first) {
			this.first = first;
		}

		public String getLast() {
			return last;
		}

		public void setLast(String last) {
			this.last = last;
		}
	}


	public static class LocationModel implements Serializable {

		@SerializedName("street")
		@Expose
		private String street;

		@SerializedName("city")
		@Expose
		private String city;

		@SerializedName("state")
		@Expose
		private String state;

		@SerializedName("postcode")
		@Expose
		private String postcode;

		@SerializedName("coordinates")
		@Expose
		private CoordinateModel coordinates;

		@SerializedName("timezone")
		@Expose
		private TimeZoneModel timezone;

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getPostcode() {
			return postcode;
		}

		public void setPostcode(String postcode) {
			this.postcode = postcode;
		}

		public CoordinateModel getCoordinates() {
			return coordinates;
		}

		public void setCoordinates(CoordinateModel coordinates) {
			this.coordinates = coordinates;
		}

		public TimeZoneModel getTimezone() {
			return timezone;
		}

		public void setTimezone(TimeZoneModel timezone) {
			this.timezone = timezone;
		}
	}

	public static class CoordinateModel implements Serializable {

		@SerializedName("latitude")
		@Expose
		private String latitude;

		@SerializedName("longitude")
		@Expose
		private String longitude;

		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
	}

	public static class TimeZoneModel implements Serializable {

		@SerializedName("offset")
		@Expose
		private String offset;

		@SerializedName("description")
		@Expose
		private String description;

		public String getOffset() {
			return offset;
		}

		public void setOffset(String offset) {
			this.offset = offset;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}

	public static class PictureModel implements Serializable {

		@SerializedName("large")
		@Expose
		private String large;

		@SerializedName("medium")
		@Expose
		private String medium;

		@SerializedName("thumbnail")
		@Expose
		private String thumbnail;

		public String getLarge() {
			return large;
		}

		public void setLarge(String large) {
			this.large = large;
		}

		public String getMedium() {
			return medium;
		}

		public void setMedium(String medium) {
			this.medium = medium;
		}

		public String getThumbnail() {
			return thumbnail;
		}

		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}
	}

	public static class DOBModel implements Serializable {

		@SerializedName("date")
		@Expose
		private String date;

		@SerializedName("age")
		@Expose
		private String age;

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}
	}
}
