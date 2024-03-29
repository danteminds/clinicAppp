package com.clinic.entity;
// Generated Sep 5, 2020 3:52:44 PM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.clinic.util.Utility;

/**
 * ClinicPatient generated by hbm2java
 */
public class ClinicPatient implements java.io.Serializable {

	private int patientId;
	private String patientName;
	private String patientPhone;
	private String patientEmail;
	private String patientBirthDt;
	private String patientAge;
	private String patientCreateDt;
	private Set<PatientVisit> patientVisits = new HashSet(0);
	private Date patientBirthUtilDate;

	public ClinicPatient() {
	}
	
	public ClinicPatient(ClinicPatient clinicPatient) {
		this(clinicPatient.getPatientName(), clinicPatient.getPatientPhone(), clinicPatient.getPatientEmail(), clinicPatient.getPatientBirthDt(),
				clinicPatient.getPatientAge(), clinicPatient.getPatientCreateDt(), clinicPatient.getPatientVisits());
		this.patientBirthUtilDate=clinicPatient.getPatientBirthUtilDate();
	}

	public ClinicPatient(String patientName, String patientAge) {
		this.patientName = patientName;
		this.patientAge = patientAge;
	}

	public ClinicPatient(String patientName, String patientPhone, String patientEmail, String patientBirthDt,
			String patientAge, String patientCreateDt, Set patientVisits) {
		this.patientName = patientName;
		this.patientPhone = patientPhone;
		this.patientEmail = patientEmail;
		this.patientBirthDt = patientBirthDt;
		this.patientAge = patientAge;
		this.patientCreateDt = patientCreateDt;
		this.patientVisits = patientVisits;
	}

	public int getPatientId() {
		return this.patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return this.patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientPhone() {
		return this.patientPhone;
	}

	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}

	public String getPatientEmail() {
		return this.patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public String getPatientBirthDt() {
		return this.patientBirthDt;
	}

	public void setPatientBirthDt(String patientBirthDt) {
		this.patientBirthDt = patientBirthDt;
	}

	public String getPatientAge() {
		return this.patientAge;
	}

	public void setPatientAge(String patientAge) {
		this.patientAge = patientAge;
	}

	public String getPatientCreateDt() {
		return this.patientCreateDt;
	}

	public void setPatientCreateDt(String patientCreateDt) {
		this.patientCreateDt = patientCreateDt;
	}

	public Set<PatientVisit> getPatientVisits() {
		return this.patientVisits;
	}

	public void setPatientVisits(Set<PatientVisit> patientVisits) {
		this.patientVisits = patientVisits;
	}

	public Date getPatientBirthUtilDate() {
		return patientBirthUtilDate;
	}

	public void setPatientBirthUtilDate(Date patientBirthUtilDate) {
		this.patientBirthUtilDate = patientBirthUtilDate;
	}

	public int getNumberOfVisits() {

		if (!Utility.isEmpty(getPatientVisits()) && !getPatientVisits().isEmpty()) {
			return getPatientVisits().size();
		}

		return 0;
	}

}
