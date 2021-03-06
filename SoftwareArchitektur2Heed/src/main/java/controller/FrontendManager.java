package controller;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

import restfulService.EmployeeTO;
import business.ReservationData;
import business.ReservationManagerBean;
import entities.Nutzungskategorie;
import entities.Raum;
import entities.Reservation;

@Named
@RequestScoped
public class FrontendManager {
	
	private ReservationData resData= new ReservationData();
	private EmployeeTO employeeData = new EmployeeTO();

	@EJB
	ReservationManagerBean bean;
	
	public String searchRooms(){
		System.out.println("methode searchRooms() aufgerufen");
		System.out.println("VON: " + resData.getDatumVon());
		System.out.println("BIS: " + resData.getDatumBis());
		bean.storeData(resData);
		System.out.println("methode searchRooms() beendet");
		return "available.xhtml";
		
	}
	
	public List <Raum> getAvailableRooms(){
		System.out.println("frontendmanager getRaumGroesse() aufgerufen");
		return bean.availableRooms();
	}
	
	public List<EmployeeTO> getCheckEmployeeName(){
		System.out.println("Methode checkEmployeeName() aufgerufen");
		return bean.loadEmployeeName();
	}
	
	
	public String store(){
		System.out.println("gespeichert");
		System.out.println(employeeData.getId());
		System.out.println(resData.getIdentifyRoom().getBezeichnung());
		bean.addReservation(employeeData.getId());
		return "confirmation.xhtml";
	}

	
	
	
	public ReservationData getResData() {
		return resData;
	}

	public void setResData(ReservationData resData) {
		this.resData = resData;
	}

	public EmployeeTO getEmployeData() {
		return employeeData;
	}

	public void setEmployeData(EmployeeTO employeData) {
		this.employeeData = employeData;
	}
	
	
	public Date getMinDate() {
		
		Calendar c = Calendar.getInstance();
		int diffToAdd = 15 - (c.getTime().getMinutes() % 15);
		c.add(Calendar.MINUTE, diffToAdd);		
		return c.getTime();
	}
	
	

	
}
