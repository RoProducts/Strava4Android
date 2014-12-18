package de.rooehler.strava4android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Strava implements Serializable{


	private static final long serialVersionUID = 1L;
	@SerializedName("access_token")
	@Expose
	private String accessToken;
	@SerializedName("token_type")
	@Expose
	private String tokenType;
	@Expose
	private Athlete athlete;
 
	/**
	 * 
	 * @return
	 * The accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * 
	 * @param accessToken
	 * The access_token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * 
	 * @return
	 * The tokenType
	 */
	public String getTokenType() {
		return tokenType;
	}

	/**
	 * 
	 * @param tokenType
	 * The token_type
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * 
	 * @return
	 * The athlete
	 */
	public Athlete getAthlete() {
		return athlete;
	}

	/**
	 * 
	 * @param athlete
	 * The athlete
	 */
	public void setAthlete(Athlete athlete) {
		this.athlete = athlete;
	}

	public class Athlete implements Serializable{

		private static final long serialVersionUID = 1L;
		@Expose
		private Integer id;
		@SerializedName("resource_state")
		@Expose
		private Integer resourceState;
		@Expose
		private String firstname;
		@Expose
		private String lastname;
		@SerializedName("profile_medium")
		@Expose
		private String profileMedium;
		@Expose
		private String profile;
		@Expose
		private String city;
		@Expose
		private String state;
		@Expose
		private String country;
		@Expose
		private String sex;
		@Expose
		private Object friend;
		@Expose
		private Object follower;
		@Expose
		private Boolean premium;
		@SerializedName("created_at")
		@Expose
		private String createdAt;
		@SerializedName("updated_at")
		@Expose
		private String updatedAt;
		@SerializedName("badge_type_id")
		@Expose
		private Integer badgeTypeId;
		@SerializedName("follower_count")
		@Expose
		private Integer followerCount;
		@SerializedName("friend_count")
		@Expose
		private Integer friendCount;
		@SerializedName("mutual_friend_count")
		@Expose
		private Integer mutualFriendCount;
		@SerializedName("date_preference")
		@Expose
		private String datePreference;
		@SerializedName("measurement_preference")
		@Expose
		private String measurementPreference;
		@Expose
		private String email;
		@Expose
		private Object ftp;
		@Expose
		private List<Object> clubs = new ArrayList<Object>();
		@Expose
		private List<Bike> bikes = new ArrayList<Bike>();
		@Expose
		private List<Object> shoes = new ArrayList<Object>();

		/**
		 * 
		 * @return
		 * The id
		 */
		public Integer getId() {
			return id;
		}

		/**
		 * 
		 * @param id
		 * The id
		 */
		public void setId(Integer id) {
			this.id = id;
		}

		/**
		 * 
		 * @return
		 * The resourceState
		 */
		public Integer getResourceState() {
			return resourceState;
		}

		/**
		 * 
		 * @param resourceState
		 * The resource_state
		 */
		public void setResourceState(Integer resourceState) {
			this.resourceState = resourceState;
		}

		/**
		 * 
		 * @return
		 * The firstname
		 */
		public String getFirstname() {
			return firstname;
		}

		/**
		 * 
		 * @param firstname
		 * The firstname
		 */
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		/**
		 * 
		 * @return
		 * The lastname
		 */
		public String getLastname() {
			return lastname;
		}

		/**
		 * 
		 * @param lastname
		 * The lastname
		 */
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		/**
		 * 
		 * @return
		 * The profileMedium
		 */
		public String getProfileMedium() {
			return profileMedium;
		}

		/**
		 * 
		 * @param profileMedium
		 * The profile_medium
		 */
		public void setProfileMedium(String profileMedium) {
			this.profileMedium = profileMedium;
		}

		/**
		 * 
		 * @return
		 * The profile
		 */
		public String getProfile() {
			return profile;
		}

		/**
		 * 
		 * @param profile
		 * The profile
		 */
		public void setProfile(String profile) {
			this.profile = profile;
		}

		/**
		 * 
		 * @return
		 * The city
		 */
		public String getCity() {
			return city;
		}

		/**
		 * 
		 * @param city
		 * The city
		 */
		public void setCity(String city) {
			this.city = city;
		}

		/**
		 * 
		 * @return
		 * The state
		 */
		public String getState() {
			return state;
		}

		/**
		 * 
		 * @param state
		 * The state
		 */
		public void setState(String state) {
			this.state = state;
		}

		/**
		 * 
		 * @return
		 * The country
		 */
		public String getCountry() {
			return country;
		}

		/**
		 * 
		 * @param country
		 * The country
		 */
		public void setCountry(String country) {
			this.country = country;
		}

		/**
		 * 
		 * @return
		 * The sex
		 */
		public String getSex() {
			return sex;
		}

		/**
		 * 
		 * @param sex
		 * The sex
		 */
		public void setSex(String sex) {
			this.sex = sex;
		}

		/**
		 * 
		 * @return
		 * The friend
		 */
		public Object getFriend() {
			return friend;
		}

		/**
		 * 
		 * @param friend
		 * The friend
		 */
		public void setFriend(Object friend) {
			this.friend = friend;
		}

		/**
		 * 
		 * @return
		 * The follower
		 */
		public Object getFollower() {
			return follower;
		}

		/**
		 * 
		 * @param follower
		 * The follower
		 */
		public void setFollower(Object follower) {
			this.follower = follower;
		}

		/**
		 * 
		 * @return
		 * The premium
		 */
		public Boolean getPremium() {
			return premium;
		}

		/**
		 * 
		 * @param premium
		 * The premium
		 */
		public void setPremium(Boolean premium) {
			this.premium = premium;
		}

		/**
		 * 
		 * @return
		 * The createdAt
		 */
		public String getCreatedAt() {
			return createdAt;
		}

		/**
		 * 
		 * @param createdAt
		 * The created_at
		 */
		public void setCreatedAt(String createdAt) {
			this.createdAt = createdAt;
		}

		/**
		 * 
		 * @return
		 * The updatedAt
		 */
		public String getUpdatedAt() {
			return updatedAt;
		}

		/**
		 * 
		 * @param updatedAt
		 * The updated_at
		 */
		public void setUpdatedAt(String updatedAt) {
			this.updatedAt = updatedAt;
		}

		/**
		 * 
		 * @return
		 * The badgeTypeId
		 */
		public Integer getBadgeTypeId() {
			return badgeTypeId;
		}

		/**
		 * 
		 * @param badgeTypeId
		 * The badge_type_id
		 */
		public void setBadgeTypeId(Integer badgeTypeId) {
			this.badgeTypeId = badgeTypeId;
		}

		/**
		 * 
		 * @return
		 * The followerCount
		 */
		public Integer getFollowerCount() {
			return followerCount;
		}

		/**
		 * 
		 * @param followerCount
		 * The follower_count
		 */
		public void setFollowerCount(Integer followerCount) {
			this.followerCount = followerCount;
		}

		/**
		 * 
		 * @return
		 * The friendCount
		 */
		public Integer getFriendCount() {
			return friendCount;
		}

		/**
		 * 
		 * @param friendCount
		 * The friend_count
		 */
		public void setFriendCount(Integer friendCount) {
			this.friendCount = friendCount;
		}

		/**
		 * 
		 * @return
		 * The mutualFriendCount
		 */
		public Integer getMutualFriendCount() {
			return mutualFriendCount;
		}

		/**
		 * 
		 * @param mutualFriendCount
		 * The mutual_friend_count
		 */
		public void setMutualFriendCount(Integer mutualFriendCount) {
			this.mutualFriendCount = mutualFriendCount;
		}

		/**
		 * 
		 * @return
		 * The datePreference
		 */
		public String getDatePreference() {
			return datePreference;
		}

		/**
		 * 
		 * @param datePreference
		 * The date_preference
		 */
		public void setDatePreference(String datePreference) {
			this.datePreference = datePreference;
		}

		/**
		 * 
		 * @return
		 * The measurementPreference
		 */
		public String getMeasurementPreference() {
			return measurementPreference;
		}

		/**
		 * 
		 * @param measurementPreference
		 * The measurement_preference
		 */
		public void setMeasurementPreference(String measurementPreference) {
			this.measurementPreference = measurementPreference;
		}

		/**
		 * 
		 * @return
		 * The email
		 */
		public String getEmail() {
			return email;
		}

		/**
		 * 
		 * @param email
		 * The email
		 */
		public void setEmail(String email) {
			this.email = email;
		}

		/**
		 * 
		 * @return
		 * The ftp
		 */
		public Object getFtp() {
			return ftp;
		}

		/**
		 * 
		 * @param ftp
		 * The ftp
		 */
		public void setFtp(Object ftp) {
			this.ftp = ftp;
		}

		/**
		 * 
		 * @return
		 * The clubs
		 */
		public List<Object> getClubs() {
			return clubs;
		}

		/**
		 * 
		 * @param clubs
		 * The clubs
		 */
		public void setClubs(List<Object> clubs) {
			this.clubs = clubs;
		}

		/**
		 * 
		 * @return
		 * The bikes
		 */
		public List<Bike> getBikes() {
			return bikes;
		}

		/**
		 * 
		 * @param bikes
		 * The bikes
		 */
		public void setBikes(List<Bike> bikes) {
			this.bikes = bikes;
		}

		/**
		 * 
		 * @return
		 * The shoes
		 */
		public List<Object> getShoes() {
			return shoes;
		}

		/**
		 * 
		 * @param shoes
		 * The shoes
		 */
		public void setShoes(List<Object> shoes) {
			this.shoes = shoes;
		}

	}

	public class Bike implements Serializable{


		private static final long serialVersionUID = 1L;
		@Expose
		private String id;
		@Expose
		private Boolean primary;
		@Expose
		private String name;
		@SerializedName("resource_state")
		@Expose
		private Integer resourceState;
		@Expose
		private Double distance;

		/**
		 * 
		 * @return
		 * The id
		 */
		public String getId() {
			return id;
		}

		/**
		 * 
		 * @param id
		 * The id
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * 
		 * @return
		 * The primary
		 */
		public Boolean getPrimary() {
			return primary;
		}

		/**
		 * 
		 * @param primary
		 * The primary
		 */
		public void setPrimary(Boolean primary) {
			this.primary = primary;
		}

		/**
		 * 
		 * @return
		 * The name
		 */
		public String getName() {
			return name;
		}

		/**
		 * 
		 * @param name
		 * The name
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 
		 * @return
		 * The resourceState
		 */
		public Integer getResourceState() {
			return resourceState;
		}

		/**
		 * 
		 * @param resourceState
		 * The resource_state
		 */
		public void setResourceState(Integer resourceState) {
			this.resourceState = resourceState;
		}

		/**
		 * 
		 * @return
		 * The distance
		 */
		public Double getDistance() {
			return distance;
		}

		/**
		 * 
		 * @param distance
		 * The distance
		 */
		public void setDistance(Double distance) {
			this.distance = distance;
		}

	}
}