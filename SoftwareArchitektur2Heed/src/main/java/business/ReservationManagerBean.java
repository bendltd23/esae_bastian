package business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;

import restfulService.EmployeeTO;
import entities.Raum;
import entities.Reservation;
import restfulService.RestfulClient;

@Stateful
@Named
public class ReservationManagerBean {
	
	@PersistenceContext(unitName = "primary")
	EntityManager em;
	
	private ReservationData data = new ReservationData ();
	private Reservation reservation = new Reservation();
	
	public void storeData(ReservationData resData){
		data.setDatumBis(resData.getDatumBis());
		data.setDatumVon(resData.getDatumVon());
		data.setRaumGroesse(resData.getRaumGroesse());
		data.setDatumVon(resData.getDatumVon());
		data.setDatumBis(resData.getDatumBis());
		System.out.println(data.getDatumBis());
		System.out.println("Methode availableRooms aufgerufen");
	}
	
	
	public List<Raum> availableRooms(){
		
		TypedQuery<Raum> query = em.createQuery("SELECT r FROM entities.Raum r", Raum.class);
		List <Raum> list =query.getResultList();
		/*System.out.println(data.getRooms().get(0).getBezeichnung());
		List<Raum> list = new LinkedList<Raum>();
		Raum testraum = new Raum();
		testraum.setBezeichnung("test");
		Raum testraum2 = new Raum();
		testraum2.setBezeichnung("test2");
		list.add(testraum);
		list.add(testraum2);
		System.out.println("availableRooms() durchgeführt");*/
		return list;
		
	}
	
	
	public List<EmployeeTO> loadEmployeeName(){
		List <EmployeeTO> employees = new LinkedList <EmployeeTO>();
			try {
				ClientRequest request = new ClientRequest("http://employeemanager-esaeservice.rhcloud.com/rs/Employees");
				//request.accept("application/json");
				
				ClientResponse <List <EmployeeTO>> response = request.get(new GenericType<List<EmployeeTO>>(){}) ;
				
				if (response.getStatus() != 200){
					throw new RuntimeException("Failed : HTTP error code : " 
							+ response.getStatus());
				}		
				
				System.out.println ("laden funktioniert!");
				
				employees = response.getEntity();			
				
				Iterator <EmployeeTO> iter = employees.iterator();
				while (iter.hasNext()){
					System.out.println (iter.next());
				}
				
				
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			
			return employees;
			
		}
	
	
	
	public void addReservation(int employeeID){
		System.out.println("hinzugefügt");
		reservation.setIdMitarbeiter(employeeID);
		reservation.setReserviertBis(data.getDatumBis());
		reservation.setReserviertVon(data.getDatumVon());
		Raum testraum = new Raum();
		testraum.setIdRaum(1);
		reservation.setRaum(testraum);
		em.persist(reservation);
	}
	
	

}
