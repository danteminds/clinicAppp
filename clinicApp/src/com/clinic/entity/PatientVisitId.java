package com.clinic.entity;
// Generated Sep 5, 2020 3:52:44 PM by Hibernate Tools 4.3.1

/**
 * PatientVisitId generated by hbm2java
 */
public class PatientVisitId implements java.io.Serializable {

	private int patientId;
	private int visitNo;

	public PatientVisitId() {
	}

	public PatientVisitId(PatientVisitId patientVisitId) {
		this(patientVisitId.getPatientId(), patientVisitId.getVisitNo());
	}

	public PatientVisitId(int patientId, int visitNo) {
		this.patientId = patientId;
		this.visitNo = visitNo;
	}

	public int getPatientId() {
		return this.patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public int getVisitNo() {
		return this.visitNo;
	}

	public void setVisitNo(int visitNo) {
		this.visitNo = visitNo;
	}

	public void incrementVisitNo() {
		this.visitNo = visitNo + 1;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PatientVisitId))
			return false;
		PatientVisitId castOther = (PatientVisitId) other;

		return (this.getPatientId() == castOther.getPatientId()) && (this.getVisitNo() == castOther.getVisitNo());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getPatientId();
		result = 37 * result + this.getVisitNo();
		return result;
	}

}
