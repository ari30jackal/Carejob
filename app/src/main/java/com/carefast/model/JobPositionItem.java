package com.carefast.model;

import com.google.gson.annotations.SerializedName;

public class JobPositionItem{

	@SerializedName("id_job_position")
	private String idJobPosition;

	@SerializedName("code_position")
	private String codePosition;

	@SerializedName("gender")
	private String gender;

	@SerializedName("certificate_employment_criteria")
	private String certificateEmploymentCriteria;

	@SerializedName("have_tatoo_criteria")
	private String haveTatooCriteria;

	@SerializedName("martial_status")
	private String martialStatus;

	@SerializedName("nama_position")
	private String namaPosition;

	@SerializedName("sim_photo_criteria")
	private String simPhotoCriteria;

	@SerializedName("upload_stnk_criteria")
	private String uploadBpkbCriteria;

	@SerializedName("ear_piercing_criteria")
	private String earPiercingCriteria;

	@SerializedName("min_height_man_criteria")
	private String minHeightManCriteria;

	@SerializedName("sio_photo_criteria")
	private String sioPhotoCriteria;

	@SerializedName("status_min_age_criteria")
	private Object statusMinAgeCriteria;

	@SerializedName("position_image")
	private String positionImage;

	@SerializedName("min_education_criteria")
	private String minEducationCriteria;

	@SerializedName("min_height_women_criteria")
	private String minHeightWomenCriteria;

	@SerializedName("max_age_criteria")
	private String maxAgeCriteria;

	@SerializedName("certificate_competency_criteria")
	private String certificateCompetencyCriteria;

	@SerializedName("status_max_age_criteria")
	private Object statusMaxAgeCriteria;

	@SerializedName("upload_skck_criteria")
	private String uploadSkckCriteria;

	@SerializedName("min_age_criteria")
	private String minAgeCriteria;

	public String getIdJobPosition(){
		return idJobPosition;
	}

	public String getCodePosition(){
		return codePosition;
	}

	public String getGender(){
		return gender;
	}

	public String getCertificateEmploymentCriteria(){
		return certificateEmploymentCriteria;
	}

	public String getHaveTatooCriteria(){
		return haveTatooCriteria;
	}

	public String getMartialStatus(){
		return martialStatus;
	}

	public String getNamaPosition(){
		return namaPosition;
	}

	public String getSimPhotoCriteria(){
		return simPhotoCriteria;
	}

	public String getUploadBpkbCriteria(){
		return uploadBpkbCriteria;
	}

	public String getEarPiercingCriteria(){
		return earPiercingCriteria;
	}

	public String getMinHeightManCriteria(){
		return minHeightManCriteria;
	}

	public String getSioPhotoCriteria(){
		return sioPhotoCriteria;
	}

	public Object getStatusMinAgeCriteria(){
		return statusMinAgeCriteria;
	}

	public String getPositionImage(){
		return positionImage;
	}

	public String getMinEducationCriteria(){
		return minEducationCriteria;
	}

	public String getMinHeightWomenCriteria(){
		return minHeightWomenCriteria;
	}

	public String getMaxAgeCriteria(){
		return maxAgeCriteria;
	}

	public String getCertificateCompetencyCriteria(){
		return certificateCompetencyCriteria;
	}

	public Object getStatusMaxAgeCriteria(){
		return statusMaxAgeCriteria;
	}

	public String getUploadSkckCriteria(){
		return uploadSkckCriteria;
	}

	public String getMinAgeCriteria(){
		return minAgeCriteria;
	}
}