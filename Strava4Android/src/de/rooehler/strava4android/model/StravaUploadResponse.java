package de.rooehler.strava4android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StravaUploadResponse {

	@Expose
	private Integer id;
	@SerializedName("external_id")
	@Expose
	private String externalId;
	@Expose
	private Object error;
	@Expose
	private String status;
	@SerializedName("activity_id")
	@Expose
	private Object activityId;

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
	 * The externalId
	 */
	public String getExternalId() {
		return externalId;
	}

	/**
	 * 
	 * @param externalId
	 * The external_id
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	/**
	 * 
	 * @return
	 * The error
	 */
	public Object getError() {
		return error;
	}

	/**
	 * 
	 * @param error
	 * The error
	 */
	public void setError(Object error) {
		this.error = error;
	}

	/**
	 * 
	 * @return
	 * The status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 * The status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return
	 * The activityId
	 */
	public Object getActivityId() {
		return activityId;
	}

	/**
	 * 
	 * @param activityId
	 * The activity_id
	 */
	public void setActivityId(Object activityId) {
		this.activityId = activityId;
	}

}
