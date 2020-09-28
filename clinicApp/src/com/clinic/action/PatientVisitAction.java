package com.clinic.action;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.clinic.entity.ClinicPatient;
import com.clinic.entity.PatientVisit;
import com.clinic.entity.PatientVisitId;
import com.clinic.service.ClinicPatientService;
import com.clinic.service.PatientVisitService;
import com.clinic.util.Constants;
import com.clinic.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@SessionScoped
public class PatientVisitAction {

	/*******************************************************/
	/* INITIALIZATION */
	/*******************************************************/

	@ManagedProperty("#{patientVisitService}")
	private PatientVisitService patientVisitService;

	private PatientVisit selectedPatVisit;
	private PatientVisit newPatVisit;
	private PatientVisit singlePatVisit;
	private List<PatientVisit> patVisitList;
	private List<PatientVisit> patVisitBackupList;

	@PostConstruct
	public void init() {
		selectedPatVisit = new PatientVisit(new PatientVisitId());
		newPatVisit = new PatientVisit(new PatientVisitId());
		patVisitList = new ArrayList<PatientVisit>();
		patVisitBackupList = new ArrayList<PatientVisit>();
	}

	/*******************************************************/
	/* OTHER METHODS */
	/*******************************************************/

	public void searchPatVisits(int patientId) {
		if (!Utility.isEmpty(patientId)) {
			setPatVisitList(getPatientVisitService().findPatientVisitsByPatId(patientId));
			if (getPatVisitList().isEmpty()) {
				setPatVisitList(new ArrayList<PatientVisit>());
			}
		} else {
			setPatVisitList(new ArrayList<PatientVisit>());
		}
	}

	/*******************************************************/

	public void prepareCreate(int patientId) {
		setNewPatVisit(new PatientVisit(new PatientVisitId(patientId, 0)));
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("patientVisitDetails.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*******************************************************/

	public void prepareEdit() {
		setNewPatVisit(new PatientVisit(getSelectedPatVisit()));
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("patientVisitDetails.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*******************************************************/

	public void searchSinglePatVisit(int patientId, int visitNo) {
		if (!Utility.isEmpty(patientId) && !Utility.isEmpty(visitNo)) {
			setSinglePatVisit(getPatientVisitService().findPatientVisitById(patientId, visitNo));
			if (Utility.isEmpty(getSinglePatVisit())) {
				setSinglePatVisit(new PatientVisit(new PatientVisitId(patientId, this.getNextVisitNumber(patientId))));
			}
		} else {
			setSinglePatVisit(new PatientVisit(new PatientVisitId(patientId, this.getNextVisitNumber(patientId))));
		}
	}

	/*******************************************************/

	public void addNewVisit() {
		String msg = "";
		if (!Utility.isEmpty(getNewPatVisit())) {
			if (Utility.isEmpty(getNewPatVisit().getVisitUtilDt())) {
				Date today = new Date();
				getNewPatVisit().setVisitUtilDt(today);
			}
			getNewPatVisit().setVisitDt(Utility.convertDateToString(getNewPatVisit().getVisitUtilDt(),
					Constants.DateFormats.DATE_TIME_AM_PM_FORMAT));
			getNewPatVisit().getId().setVisitNo(getNextVisitNumber(getNewPatVisit().getId().getPatientId()));
			this.persist(getNewPatVisit(), Constants.Operations.INSERT);
		} else {
			msg = "Error While Inserting Patient's New Visist please refresh the page and try again later";
			Utility.showErrorMessage(msg);
		}

		refreshPatVisitList();
	}

	/*******************************************************/

	public void editVisit() {
		String msg = "";
		if (!Utility.isEmpty(getNewPatVisit())) {
			if (Utility.isEmpty(getNewPatVisit().getVisitDt())) {
				getNewPatVisit().setVisitUtilDt(new Date());
				getNewPatVisit().setVisitDt(Utility.convertDateToString(getNewPatVisit().getVisitUtilDt(),
						Constants.DateFormats.DATE_TIME_AM_PM_FORMAT));
			}
			this.persist(getNewPatVisit(), Constants.Operations.EDIT);
		} else {
			msg = "Error While Editing Patient's Visist please refresh the page and try again later";
			Utility.showErrorMessage(msg);
		}

		refreshPatVisitList();
	}

	/*******************************************************/

	private void refreshPatVisitList() {
		ClinicPatientAction clinicController = (ClinicPatientAction) Utility.getActionSessionObj("clinicPatientAction");
		clinicController.refreshSelectedPatient();
	}

	/*******************************************************/

	private int getNextVisitNumber(int patientId) {
		int pNextVisitNo = getPatientVisitService().getNextPatVisitNo(patientId);
		return pNextVisitNo;
	}

	/*******************************************************/

	public void onEditorSave() {
		if (!Utility.isEmpty(getNewPatVisit()) && !Utility.isEmpty(getNewPatVisit().getId())
				&& getNewPatVisit().getId().getVisitNo() != 0) {
			// Not New Visit
			editVisit();
		} else {
			//New Visit
			addNewVisit();
		}
	}

	/*******************************************************/
	/* PERSIST METHODS */
	/*******************************************************/

	public void persist(PatientVisit patVisit, String operation) {
		String msg = "";
		try {
			switch (operation) {
			case Constants.Operations.INSERT:
				getPatientVisitService().persist(patVisit);
				msg = "New Patient Visit Has Been Added Successfully!";
				break;
			case Constants.Operations.EDIT:
				getPatientVisitService().merge(patVisit);
				msg = "The Patient Visit Has Been Edited Successfully";
				break;
			case Constants.Operations.DELETE:
				getPatientVisitService().delete(patVisit);
				msg = "The Patient Visit Has Been Deleted Successfully";
				break;
			}
			Utility.showInfoMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error While";
			switch (operation) {
			case Constants.Operations.INSERT:
				msg = " Adding a New Patient Visit";
				break;
			case Constants.Operations.EDIT:
				msg = " Editing Patient's Visit";
				break;
			case Constants.Operations.DELETE:
				msg = " Deleting Patient's Visit";
				break;
			}
			msg = msg + " ,please refresh the page and try again later";
			Utility.showErrorMessage(msg);
		}

	}

	/*******************************************************/
	/* GETTERS AND SETTERS */
	/*******************************************************/

	public PatientVisitService getPatientVisitService() {
		return patientVisitService;
	}

	public void setPatientVisitService(PatientVisitService patientVisitService) {
		this.patientVisitService = patientVisitService;
	}

	/*******************************************************/

	public PatientVisit getSelectedPatVisit() {
		return selectedPatVisit;
	}

	public void setSelectedPatVisit(PatientVisit selectedPatVisit) {
		this.selectedPatVisit = selectedPatVisit;
	}

	/*******************************************************/

	public PatientVisit getNewPatVisit() {
		return newPatVisit;
	}

	public void setNewPatVisit(PatientVisit newPatVisit) {
		this.newPatVisit = newPatVisit;
	}

	/*******************************************************/

	public PatientVisit getSinglePatVisit() {
		return singlePatVisit;
	}

	public void setSinglePatVisit(PatientVisit singlePatVisit) {
		this.singlePatVisit = singlePatVisit;
	}

	/*******************************************************/

	public List<PatientVisit> getPatVisitList() {
		return patVisitList;
	}

	public void setPatVisitList(List<PatientVisit> patVisitList) {
		this.patVisitList = patVisitList;
	}

	/*******************************************************/
	
	public List<PatientVisit> getPatVisitBackupList() {
		return patVisitBackupList;
	}

	public void setPatVisitBackupList(List<PatientVisit> patVisitBackupList) {
		this.patVisitBackupList = patVisitBackupList;
	}

}
