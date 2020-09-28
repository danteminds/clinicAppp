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
import com.clinic.util.Utility;

@Component
public class ClinicPatientService {

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
	public void save(ClinicPatient patient) {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		session.save(patient);
	}

	/*******************************************************/

	@Transactional
	public void persist(ClinicPatient patient) {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		session.persist(patient);
	}

	/*******************************************************/

	@Transactional
	public void merge(ClinicPatient patient) {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		session.merge(patient);
	}

	/*******************************************************/

	@Transactional
	public void delete(ClinicPatient patient) {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		session.delete(patient);
	}

	/*******************************************************/

	@Transactional
	public List<ClinicPatient> getAllPatients() {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		Query patienQuery = session.createQuery(" from ClinicPatient");
		try {
			return patienQuery.list();
		} catch (Exception e) {
			e.printStackTrace();
			Utility.showErrorMessage("Error please refresh the page and try again later");
			return new ArrayList<ClinicPatient>();
		}
	}

	/*******************************************************/

	@Transactional
	public List<ClinicPatient> findPatientListByPatName(String patientName) {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		Query patienQuery = session.createQuery(" from ClinicPatient cp where cp.patientName=?");
		patienQuery.setString(0, patientName);
		try {
			return patienQuery.list();
		} catch (Exception e) {
			e.printStackTrace();
			Utility.showErrorMessage("Error please refresh the page and try again later");
			return new ArrayList<ClinicPatient>();
		}
	}

	/*******************************************************/

	@Transactional
	public List<ClinicPatient> findOtherPatient(int patientId, String patientName) {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		Query patienQuery = session.createQuery(
				" from ClinicPatient cp where cp.patientName = :patientName and cp.patientId != :patientId");
		patienQuery.setString("patientName", patientName);
		patienQuery.setInteger("patientId", patientId);
		try {
			return patienQuery.list();
		} catch (Exception e) {
			e.printStackTrace();
			Utility.showErrorMessage("Error please refresh the page and try again later");
			return new ArrayList<ClinicPatient>();
		}
	}

	/*******************************************************/

	@Transactional
	public ClinicPatient findPatientByPaId(int patientId) {
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		Query patienQuery = session.createQuery(" from ClinicPatient cp where cp.patientId = :patientId");
		patienQuery.setInteger("patientId", patientId);
		try {
			return (ClinicPatient) patienQuery.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			Utility.showErrorMessage("Error please refresh the page and try again later");
			return null;
		}
	}

	/*******************************************************/

	@Transactional
	public List<ClinicPatient> findPatientListByPatObj(ClinicPatient patientSrch) {
		StringBuilder queryBuilder = new StringBuilder("");
		List<ClinicPatient> patientList = new ArrayList<ClinicPatient>();
		if (!Utility.isEmpty(patientSrch)) {

			queryBuilder.append("from ClinicPatient CP where 1=1");

			if (!Utility.isEmpty(patientSrch.getPatientName())) {
				queryBuilder.append(" and CP.patientName like '%");
				queryBuilder.append(patientSrch.getPatientName());
				queryBuilder.append("%'");
			}

			if (!Utility.isEmpty(patientSrch.getPatientPhone())) {
				queryBuilder.append(" and CP.patientPhone like '%");
				queryBuilder.append(patientSrch.getPatientPhone());
				queryBuilder.append("%'");
			}

			queryBuilder.append(" order by CP.patientId asc, STR_TO_DATE(CP.patientCreateDt,'%Y%m%d %h%i') asc");

			// Acquire session
			Session session = sessionFactory.getCurrentSession();
			Query patienQuery = session.createQuery(queryBuilder.toString());
			try {
				patientList = patienQuery.list();
			} catch (Exception e) {
				e.printStackTrace();
				Utility.showErrorMessage("Error please refresh the page and try again later");
			}
		}

		return patientList;
	}
}
