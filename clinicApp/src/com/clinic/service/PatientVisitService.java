package com.clinic.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.entity.ClinicPatient;
import com.clinic.entity.PatientVisit;
import com.clinic.util.Utility;

@Component
public class PatientVisitService {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*******************************************************/

	@Transactional
	public void save(PatientVisit patVisit) {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		session.save(patVisit);
	}

	/*******************************************************/

	@Transactional
	public void persist(PatientVisit patVisit) {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		session.persist(patVisit);
	}

	/*******************************************************/

	@Transactional
	public void merge(PatientVisit patVisit) {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		session.merge(patVisit);
	}

	/*******************************************************/

	@Transactional
	public void delete(PatientVisit patVisit) {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		session.delete(patVisit);
	}

	/*******************************************************/

	@Transactional
	public List<PatientVisit> getAllClinicVisits() {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		Query visitQuery = session.createQuery(" from PatientVisit");
		try {
			return visitQuery.list();
		} catch (Exception e) {
			e.printStackTrace();
			Utility.showErrorMessage("Error please refresh the page and try again later");
			return new ArrayList<PatientVisit>();
		}
	}

	/*******************************************************/

	@Transactional
	public List<PatientVisit> findPatientVisitsByPatId(int patientId) {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		Query visitQuery = session.createQuery(" from PatientVisit pv where pv.id.patientId = :patientId");
		visitQuery.setInteger("patientId", patientId);
		try {
			return visitQuery.list();
		} catch (Exception e) {
			e.printStackTrace();
			Utility.showErrorMessage("Error please refresh the page and try again later");
			return new ArrayList<PatientVisit>();
		}
	}

	/*******************************************************/

	@Transactional
	public PatientVisit findPatientVisitById(int patientId, int visitNo) {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		Query visitQuery = session
				.createQuery(" from PatientVisit pv where pv.id.patientId = :patientId and pv.id.visitNo = :visitNo");
		visitQuery.setInteger("patientId", patientId);
		visitQuery.setInteger("visitNo", visitNo);
		try {
			return (PatientVisit) visitQuery.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			Utility.showErrorMessage("Error please refresh the page and try again later");
			return null;
		}
	}

	/*******************************************************/

	@Transactional
	public int getNextPatVisitNo(int patientId) {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		Query visitQuery = session
				.createQuery("select max(pv.id.visitNo) from PatientVisit pv where pv.id.patientId = :patientId");
		visitQuery.setInteger("patientId", patientId);
		int nextVisitNo = 0;
		try {
			nextVisitNo = (int) visitQuery.uniqueResult();
			if (Utility.isEmpty(nextVisitNo)) {
				nextVisitNo = 1;
			}
		} catch (NullPointerException nullE) {

			nextVisitNo = 0;

		} catch (Exception e) {
			e.printStackTrace();
			Utility.showErrorMessage("Error please refresh the page and try again later");
			nextVisitNo = 0;
		}

		return nextVisitNo + 1;
	}

}
