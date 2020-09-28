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
import com.clinic.util.Constants;
import com.clinic.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@ManagedBean
@SessionScoped
public class ClinicPatientAction {

	/*******************************************************/
	/* INITIALIZATION */
	/*******************************************************/

	@ManagedProperty("#{clinicPatientService}")
	private ClinicPatientService clinicPatientService;

	private ClinicPatient patient;
	private ClinicPatient selectedPatient;
	private ClinicPatient searchPatient;
	private List<ClinicPatient> patientList;
	private List<ClinicPatient> patientBackupList;

	@PostConstruct
	public void init() {
		patient = new ClinicPatient();
		selectedPatient = new ClinicPatient();
		searchPatient = new ClinicPatient();
		patientList = new ArrayList<ClinicPatient>();
		patientBackupList = new ArrayList<ClinicPatient>();
	}

	/*******************************************************/
	/* OTHER METHODS */
	/*******************************************************/

	public void searchPatients() {
		if (!Utility.isEmpty(getSearchPatient())) {
			setPatientList(getClinicPatientService().findPatientListByPatObj(getSearchPatient()));
			if (getPatientList().isEmpty()) {
				setPatientList(new ArrayList<ClinicPatient>());
			}
		} else {
			setPatientList(new ArrayList<ClinicPatient>());
		}
	}

	/*******************************************************/

	public String getToday() {
		return Utility.getFormatDate(new Date(), Constants.DateFormats.DATE_TIME_AM_PM_FORMAT);
	}

	/*******************************************************/

	public void prepareNewPatient() {
		this.setPatient(new ClinicPatient());

	}

	/*******************************************************/

	public String register() {
		Date today = new Date();
		String msg = "";

		if (!Utility.isEmpty(patient) && !Utility.isEmpty(patient.getPatientName())) {

			patient.setPatientCreateDt(
					Utility.convertDateToString(today, Constants.DateFormats.DATE_TIME_AM_PM_FORMAT));
			if (isNewPatient(patient.getPatientName().trim())) {
				this.persist(patient, Constants.Operations.INSERT);
				searchAddedPatient();
				Utility.executePfCommand("PF('patSrchDatalist').clearFilters()");
				Utility.executePfCommand("PF('patientAddDialog').hide()");
			} else {
				msg = "Patient is already Registered";
				Utility.showErrorMessage(msg);
			}
		} else {
			msg = "Error While Registering Patient please refresh the page and try again later";
			Utility.showErrorMessage(msg);
		}
		return "";
	}

	/*******************************************************/

	private void searchAddedPatient() {
		// TODO Auto-generated method stub
		this.setSearchPatient(new ClinicPatient(this.getPatient()));
		this.searchPatients();
	}

	/*******************************************************/

	private void testMethod(int patientId) {
		// TODO Auto-generated method stub
		ClinicPatient testObj = getClinicPatientService().findPatientByPaId(patientId);
		for (PatientVisit visit : testObj.getPatientVisits()) {
			System.out.println("VIISSSSIIITT>>>> " + visit.getId().getVisitNo());
			System.out.println("getPatientDiagnose()>>>> " + visit.getPatientDiagnose());
			System.out.println("getVisitDt>>>> " + visit.getVisitDt());
		}
		List<PatientVisit> vList = new ArrayList<PatientVisit>(testObj.getPatientVisits());
		PatientVisit visit = new PatientVisit(vList.get((vList.size() - 1)));
		visit.getId().incrementVisitNo();
//		testObj.getPatientVisits().add(visit);
		this.persist(testObj, Constants.Operations.EDIT);
	}

	/*******************************************************/

	private boolean isNewPatient(String patientName) {
		List<ClinicPatient> pList;
		pList = clinicPatientService.findPatientListByPatName(patientName);
		if (pList.isEmpty()) {
			return true;
		}
		if (Utility.isEmpty(pList)) {
			return true;
		}
		return false;
	}

	/*******************************************************/

	private boolean isNameExsist(int patientId, String patientName) {
		List<ClinicPatient> pList;
		pList = clinicPatientService.findOtherPatient(patientId, patientName);
		if (pList.isEmpty()) {
			return false;
		}
		if (Utility.isEmpty(pList)) {
			return false;
		}
		return true;
	}

	/*******************************************************/

	public void refreshSelectedPatient() {
		if (!Utility.isEmpty(getSelectedPatient())) {
			setSelectedPatient(getClinicPatientService().findPatientByPaId(getSelectedPatient().getPatientId()));
		}

	}

	/*******************************************************/

	public int getSearchResultSize() {
		if (!Utility.isEmpty(getPatientList()) && !getPatientList().isEmpty()) {
			return getPatientList().size();
		}

		return 0;
	}

	/*******************************************************/

	public void sayHi() {
		System.out.println(">>>>>>>HIIIIIIIII>>>>>>>");
		System.out.println(this.getSelectedPatient().getPatientName() + " >>>>>> got selected");
	}

	/*******************************************************/

	public String onEditSelectedPatient() {

		String msg = "";

		if (!Utility.isEmpty(getSelectedPatient()) && !Utility.isEmpty(getSelectedPatient().getPatientName())) {

			if (!isNameExsist(getSelectedPatient().getPatientId(), getSelectedPatient().getPatientName().trim())) {
				persist(getSelectedPatient(), Constants.Operations.EDIT);
				refreshSelectedPatient();
				Utility.executePfCommand("PF('patientAgeEditDialog').hide()");
			} else {
				msg = "Other Patient is Registered Under The Same Name!";
				Utility.showErrorMessage(msg);
			}
		} else {
			msg = "Error While Editing Patient please refresh the page and try again later";
			Utility.showErrorMessage(msg);
		}
		return "";
	}

	/*******************************************************/

	public void onDeletePatient(ClinicPatient deletePatient) {
		this.setSelectedPatient(deletePatient);
//		if (!Utility.isEmpty(deletePatient) && !Utility.isEmpty(deletePatient.getPatientId())) {
//			persist(deletePatient, Constants.Operations.DELETE);
//			setSelectedPatient(new ClinicPatient());
//			searchPatients();
//		} else {
//			Utility.showErrorMessage("Error While Deleting Patient please refresh the page and try again later");
//		}
	}

	/*******************************************************/

	public void onDeleteSelectedPatient() {
		if (!Utility.isEmpty(getSelectedPatient()) && !Utility.isEmpty(getSelectedPatient().getPatientId())) {
			persist(getSelectedPatient(), Constants.Operations.DELETE);
			setSelectedPatient(new ClinicPatient());
			searchAddedPatient();
			Utility.executePfCommand("PF('patSrchDatalist').clearFilters()");
			Utility.executePfCommand("PF('patientDeleteDialog').hide()");
		} else {
			Utility.showErrorMessage("Error While Deleting Patient please refresh the page and try again later");
		}
	}

	/*******************************************************/

	public String onPatientSelected() {
		if (!Utility.isEmpty(this.getSelectedPatient())) {
			PatientVisitAction patientController = (PatientVisitAction) Utility
					.getActionSessionObj("patientVisitAction");
			patientController.init();
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("pages/patient/clinicPatientVisits.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return "";
		}
		return "";
	}

	public String redirect() {
		return "clinicPatientVisits.xhtml";
	}

	/*******************************************************/

	public void onBackupSelected() {
		setPatientBackupList(getClinicPatientService().getAllPatients());
		PatientVisitAction visitController = (PatientVisitAction) Utility.getActionSessionObj("patientVisitAction");
		visitController.setPatVisitBackupList(visitController.getPatientVisitService().getAllClinicVisits());
	}

	/*******************************************************/

	public void onBackupCompleted() {
		setPatientBackupList(new ArrayList<ClinicPatient>());
		PatientVisitAction visitController = (PatientVisitAction) Utility.getActionSessionObj("patientVisitAction");
		visitController.setPatVisitBackupList(new ArrayList<PatientVisit>());
	}

	/*******************************************************/
	/* PERSIST METHODS */
	/*******************************************************/

	public void persist(ClinicPatient persistPatient, String operation) {
		String msg = "";
		try {
			switch (operation) {
			case Constants.Operations.INSERT:
				getClinicPatientService().persist(persistPatient);
				msg = "The Patient " + persistPatient.getPatientName() + " Has Been Registered Successfully";
				break;
			case Constants.Operations.EDIT:
				getClinicPatientService().merge(persistPatient);
				msg = "The Patient " + persistPatient.getPatientName() + " Has Been Edited Successfully";
				break;
			case Constants.Operations.DELETE:
				getClinicPatientService().delete(persistPatient);
				msg = "The Patient " + persistPatient.getPatientName() + " Has Been Deleted Successfully";
				break;
			}
			Utility.showInfoMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error While";
			switch (operation) {
			case Constants.Operations.INSERT:
				msg = " Registering Patient";
				break;
			case Constants.Operations.EDIT:
				msg = " Editing Patient";
				break;
			case Constants.Operations.DELETE:
				msg = " Deleting Patient";
				break;
			}
			msg = msg + " ,please refresh the page and try again later";
			Utility.showErrorMessage(msg);
		}

	}

	/*******************************************************/
	/* GETTERS AND SETTERS */
	/*******************************************************/

	public ClinicPatient getPatient() {
		return patient;
	}

	public void setPatient(ClinicPatient patient) {
		this.patient = patient;
	}

	/*******************************************************/

	public ClinicPatientService getClinicPatientService() {
		return clinicPatientService;
	}

	public void setClinicPatientService(ClinicPatientService clinicPatientService) {
		this.clinicPatientService = clinicPatientService;
	}

	/*******************************************************/

	public ClinicPatient getSelectedPatient() {
		return selectedPatient;
	}

	public void setSelectedPatient(ClinicPatient selectedPatient) {
		this.selectedPatient = selectedPatient;
	}

	/*******************************************************/

	public ClinicPatient getSearchPatient() {
		return searchPatient;
	}

	public void setSearchPatient(ClinicPatient searchPatient) {
		this.searchPatient = searchPatient;
	}

	/*******************************************************/

	public List<ClinicPatient> getPatientList() {
		return patientList;
	}

	public void setPatientList(List<ClinicPatient> patientList) {
		this.patientList = patientList;
	}

	/*******************************************************/

	public List<ClinicPatient> getPatientBackupList() {
		return patientBackupList;
	}

	public void setPatientBackupList(List<ClinicPatient> patientBackupList) {
		this.patientBackupList = patientBackupList;
	}

}
