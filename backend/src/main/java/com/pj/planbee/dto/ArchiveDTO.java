package com.pj.planbee.dto;

import java.util.List;

public class ArchiveDTO {
	
	int archiveId;
	
	String archiveDate, archiveMemo;
	
	double archiveProgress;
	
	String userId;
	
	List<ArchDetailDTO> archiveDetails;

	public int getArchiveId() {
		return archiveId;
	}
	public void setArchiveId(int archiveId) {
		this.archiveId = archiveId;
	}
	public String getArchiveDate() {
		return archiveDate;
	}
	public void setArchiveDate(String archiveDate) {
		this.archiveDate = archiveDate;
	}
	public String getArchiveMemo() {
		return archiveMemo;
	}
	public void setArchiveMemo(String archiveMemo) {
		this.archiveMemo = archiveMemo;
	}
	public double getArchiveProgress() {
		return archiveProgress;
	}
	public void setArchiveProgress(double archiveProgress) {
		this.archiveProgress = archiveProgress;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<ArchDetailDTO> getArchiveDetails() {
		return archiveDetails;
	}
	public void setArchiveDetails(List<ArchDetailDTO> archiveDetails) {
		this.archiveDetails = archiveDetails;
	}
	
}
