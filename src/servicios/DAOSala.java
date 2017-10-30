package servicios;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Actividad;
import entidades.Sala;
import servicios.EMF;

public class DAOSala {
	
	private static DAOSala daoSala;
	
	private DAOSala(){
	}
	
	public static  DAOSala getInstance() {
		if(daoSala == null)
			daoSala = new DAOSala();
		return daoSala;
	}
	
	public Sala crearSala(String nombre, String direccion) {
		EntityManager em=EMF.createEntityManager();
		em.getTransaction( ).begin( );
		Sala salaA = new Sala(nombre,direccion);
		em.persist(salaA);
		em.getTransaction().commit();
		em.close();
		return salaA;
	}
	
	public  Sala getSala(int id) {
		// regresa una sala en base a su ID
		EntityManager em=EMF.createEntityManager();
		String jpql = "SELECT s FROM Sala s WHERE s.id = ?"; 
		Query query = em.createQuery(jpql); 
		query.setParameter(1, id);
		return (Sala) query.getSingleResult();
		 
	}
	
	public List<Sala> getSalas(){
		EntityManager em=EMF.createEntityManager();
		String jpql = "SELECT s FROM Sala s"; 
	    Query query = em.createQuery(jpql); 
	    List<Sala> resultados = query.getResultList(); 
	    return resultados;
	    
	}
	

	public  boolean hayLugar(int idSala,Date fechaInicio, Date fechafin) {
		EntityManager em=EMF.createEntityManager();	
		em.getTransaction().begin();		
		String jpql = "SELECT a1 FROM Actividad a1"
				+ "WHERE a.lugar_id = ?1"
				+ " AND (a1.fechaInicio < ?2" + " AND ?2 < a1.fechaFin"
				+ " OR a1.fechaInicio <= ?3" + " AND ?3 <= a1.fechaInicio)";
		Query query = em.createQuery(jpql); 
		query.setParameter(1, idSala);
		query.setParameter(2, fechaInicio);
		query.setParameter(3, fechafin);
		List<Actividad> resultados = query.getResultList();
		em.close();
		return (resultados == null);
	}
	
	/// UPDATE AND DELETE
	
	public  Sala updateSala(int id,String nombre, String direccion) {
		EntityManager em=EMF.createEntityManager();
		em.getTransaction().begin();		
		String jpql = "UPDATE Sala s SET nombre=?2, "
				+ "direccion=?3 WHERE s.id = ?1"; 
        Query query = em.createQuery(jpql);
        query.setParameter(1, id);
        query.setParameter(2, nombre);
        query.setParameter(3, direccion);
        query.executeUpdate();
        em.getTransaction().commit();
		em.close();
        Sala sl = getSala(id);
        return sl;
	}
	
	public  boolean deleteSala(Integer id) {
		EntityManager em=EMF.createEntityManager();
		em.getTransaction().begin();
		String jpql = "DELETE FROM Sala s WHERE s.id = ?1"; 
        Query query = em.createQuery(jpql);
        query.setParameter(1, id);
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
        Sala sala = getSala(id);
		if (sala == null) {
			return  true;
		}	
		return false;
       
	}
	
	
}
