package com.clinic.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.primefaces.PrimeFaces;
//import org.primefaces.context.RequestContext;

public class Utility {

	public static Utility UtilityObj = null;
	

	public static Utility getUtility() {

		if (UtilityObj == null) {
			UtilityObj = new Utility();
		}

		return UtilityObj;
	}

	/*********************************************************************************************/

	/*public static String getArLabel(String key) {
		// LabelsAction = (MainLabelsAction) getActionSessionObj(LabelsAction,
		// "mainLabelsAction");
		LabelsAction = (MainLabelsAction) getActionSessionObj(BkBeansConstants.MAIN_LABELS_ACTION_BEAN_NAME);
		if (null == LabelsAction) {
			return "";
		}
		if (null != key && !"".equals(key)) {
			return LabelsAction.retriveMessage(key);
		} else {
			return "";
		}
	}*/

	/*********************************************************************************************/

	/*public static String getEnLabel(String key) {
		Object obj = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(key);
		if (obj == null)
			return "";

		return ((MainLabels) obj).getEnglish();
	}*/

	/*********************************************************************************************/

	public static void showMessages(Severity svr, String result) {

		FacesMessage message = new FacesMessage(svr, result, result);
		FacesContext.getCurrentInstance().addMessage("growl", message);
	}

	/*********************************************************************************************/

	public static void showInfoMessage(String result) {
		showMessages(FacesMessage.SEVERITY_INFO, result);
	}

	/*********************************************************************************************/

	public static void showErrorMessage(String result) {
		showMessages(FacesMessage.SEVERITY_ERROR, result);
	}

	/*********************************************************************************************/

	public static synchronized boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		} else if ((value instanceof String)) {
			String val = (String) value;
			if (val.trim().length() == 0) {
				return true;
			}
		} else if ((value instanceof Integer)) {
			Integer val = (Integer) value;
			if (val.intValue() == 0) {
				return true;
			}
		} else if ((value instanceof Long)) {
			Long val = (Long) value;
			if (val.longValue() == 0L) {
				return true;
			}
		} else if ((value instanceof Short)) {
			Short val = (Short) value;
			if (val.shortValue() == 0) {
				return true;
			}
		}
		return false;
	}

	public static Date setTimeZero() {
		return setTimeZero(new Date());
	}

	public static Date setTimeZero(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);

		return calendar.getTime();
	}
	
	/*********************************************************************************************/

	public static String getFormatDate(Date date) {

		if (date == null)
			return null;
		DateFormat dateFormat = new SimpleDateFormat(Constants.DateFormats.DATE_FORMAT);
		return dateFormat.format(date);
	}
	
	/*********************************************************************************************/

	public static String getFormatDate(Date date,String format) {

		if (date == null)
			return null;
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/*********************************************************************************************/

	public static Date getFormatDateFromStr(String dateStr) {

		if (isEmpty(dateStr))
			return null;
		DateFormat dateFormat = new SimpleDateFormat(Constants.DateFormats.DATE_FORMAT);
		Date dateFromStr;
		try {
			dateFromStr = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			dateFromStr = null;
			e.printStackTrace();
		}

		return dateFromStr;
	}

	/*********************************************************************************************/

	public static String getFormatDate(String date) {
		DateFormat dateFormat = new SimpleDateFormat(Constants.DateFormats.DATE_FORMAT);
		return dateFormat.format(date);
	}

	/*********************************************************************************************/

	public static Date getFormateDate(Date dateStr) {

		if (dateStr == null)
			return null;

		Date date = new Date();
		String ss;
		try {
			DateFormat format = new SimpleDateFormat(Constants.DateFormats.DATE_FORMAT, Locale.ENGLISH);
			ss = format.format(dateStr);
			date = format.parse(ss);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/*********************************************************************************************/

	// this is used for Procedures
	public static java.sql.Date getTodayDt() {
		java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		return today;
	}

	/*********************************************************************************************/

	public static java.sql.Date convertUtilToSql(Date utilDate) {
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(utilDate);
		// java.sql.Date sqlDate = new
		// java.sql.Date(calendar.getInstance().getTimeInMillis());
		if (isEmpty(utilDate)) {
			return null;
		}
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}

	/*********************************************************************************************/

	public static boolean isDateBefore(Date before, Date after) {
		return before.before(after);
	}

	/*********************************************************************************************/

	public static boolean isTheSameDate(Date before, Date after) {
		return getFormatDate(before).equals(getFormatDate(after));
	}

	/*********************************************************************************************/

	public static Object getActionSessionObj(String actionName) {
		// returns obj from session
		ELContext elContext;
		elContext = FacesContext.getCurrentInstance().getELContext();
		Object sessionObj = (Object) elContext.getELResolver().getValue(elContext, null, actionName);
		if (null != sessionObj) {
			return sessionObj;
		} else {
			return null;
		}
	}

	/*********************************************************************************************/

	public static void resetTableSortBy(String tableId) {
		// reset table state
		UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(tableId);
		table.setValueExpression("sortBy", null);

	}

	/*********************************************************************************************/

	public static int getYearFromDate(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/*********************************************************************************************/

	public static int getMonthFromDate(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1; // it gets month with less 1 month
	}

	/*********************************************************************************************/

	public static String convertDateToString(Date time, String format) {
		if (time == null) {
			return null;
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

		return simpleDateFormat.format(time);
	}

	/*********************************************************************************************/

	public static boolean isEmpty(String value) {

		if (value == null)
			return true;

		if (value.trim().equals(""))
			return true;
		return false;

	}

	/*********************************************************************************************/

	public static String getTableName(ClassMetadata hibernateMetadata) {
		if (hibernateMetadata != null) {
			if (hibernateMetadata instanceof AbstractEntityPersister) {
				AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
				return persister.getTableName();
				// String[] columnNames = persister.getKeyColumnNames();
			}
		}
		return "";
	}

	/*********************************************************************************************/

	public static String[] getColumnNames(ClassMetadata hibernateMetadata) {
		String[] columnNames = new String[] {};
		if (hibernateMetadata != null) {
			if (hibernateMetadata instanceof AbstractEntityPersister) {
				AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
				columnNames = persister.getPropertyNames();
			}
		}
		return columnNames;
	}

	/*********************************************************************************************/

	public static Object getColumnValues(ClassMetadata hibernateMetadata, Object object, String propertyName) {
		Object columnValue = null;
		if (hibernateMetadata != null) {
			if (hibernateMetadata instanceof AbstractEntityPersister) {
				AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
				columnValue = persister.getPropertyValue(object, propertyName);
			}
		}
		return columnValue;
	}

	/*********************************************************************************************/

	/*public static void showPersistenceErrors(Throwable throwable) {

		String errorCode = "";

		if (null != throwable && throwable instanceof java.sql.SQLIntegrityConstraintViolationException) {
			java.sql.SQLIntegrityConstraintViolationException ex = (java.sql.SQLIntegrityConstraintViolationException) throwable;
			String message = ex.getMessage();
			String sqlState = ex.getSQLState();
			errorCode = getStrPaddLeft(ex.getErrorCode(), "5");
			System.err.println("\n" + message + ">>>>>>>>>" + sqlState + ">>>>>>>>>>>>>> " + errorCode + "\n\n");
		} else {
			throwable.printStackTrace();
			errorCode = Constants.ProcedureMsgs.ERROR;
		}

		switch (errorCode) {
		case Constants.ErrorCodes.CHILD_FOUND:
			errorCode = "Children for this record found,delete them first";
			break;

		case Constants.ProcedureMsgs.ERROR:
			errorCode = getArLabel(errorCode);
			break;

		case Constants.ErrorCodes.UNIQUE_CONSTRAINT:
			errorCode = getArLabel("CSB_" + Constants.ProcedureMsgs.DUPLICATE_PK);
			break;

		default:
			errorCode = getArLabel(Constants.ProcedureMsgs.ERROR);
			break;
		}
		showErrorMessage(errorCode);
	}*/

	/*********************************************************************************************/

	/*public static void showExceptionErrors(String operation) {
		String msg = "";

		switch (operation) {

		case Constants.Opeartions.INSERT:
			msg = getArLabel("CSB_" + Constants.ProcedureMsgs.RECORD_INSERT_FAILURE);
			break;
		case Constants.Opeartions.EDIT:
			msg = getArLabel("CSB_" + Constants.ProcedureMsgs.RECORD_UPDATE_FAILURE);
			break;
		case Constants.Opeartions.DELETE:
			msg = getArLabel("CSB_" + Constants.ProcedureMsgs.RECORD_DELETE_FAILURE);
			break;
		default:
			msg = getArLabel(Constants.ProcedureMsgs.ERROR);
			break;

		}

		Utility.showErrorMessage(msg);
	}*/

	/*********************************************************************************************/

	public static void executePfCommand(String script) {
		// executePfCommand
		PrimeFaces.current().executeScript(script);
	}

	/*********************************************************************************************/

	public static void updateUiElement(String elementName) {
		PrimeFaces.current().ajax().update(elementName);
	}

	/***************************************************************************************/
	public static String getStrPaddLeft(long code, String lenght) {
		String newCode = String.format("%0" + lenght + "d", code);
		// getMaxSerial("ALLOW_C",
		// "",Utility.getTableName(getSessionFactory().getClassMetadata(lkPyAllow.getClass())));
		return newCode;
	}

	/***************************************************************************************/
	public static String getStrPaddRight(long code, String lenght) {
		String newCode = String.format("%-" + lenght + "s", code).replace(' ', '0');
		// getMaxSerial("ALLOW_C",
		// "",Utility.getTableName(getSessionFactory().getClassMetadata(lkPyAllow.getClass())));
		return newCode;
	}

	/***************************************************************************************/
	/*public static MainUsers getMainUser() {
		return Utility.mainUser = (MainUsers) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("MainUser");
	}*/

	/***************************************************************************************/
	/*public static void setMainUser(MainUsers mainUser) {
		Utility.mainUser = mainUser;
	}*/

	/***************************************************************************************/
	public static String getRemoteAddr() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteUser() + ":" + request.getRemoteHost();
		}
		return ipAddress;
	}

	/***************************************************************************************/
	public static Object getObjectFromSessionMap(String key) {
		return (Object) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
	}

	/***************************************************************************************/
	public static void putObjectIntoSessionMap(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
	}

}
