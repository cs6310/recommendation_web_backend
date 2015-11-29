package services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Semester;
import play.db.jpa.JPA;

public class SemesterService {
	public Collection<Semester> getAllSemesters() {
		EntityManager em = JPA.em();
		String queryString = "SELECT s from Semester s"; //HSQL syntax
		TypedQuery<Semester> query = em.createQuery(queryString, Semester.class);
		return query.getResultList();		
	}
	public boolean storeSemester(Semester semester) {
		EntityManager em = JPA.em();
		try {
			em.persist(semester);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
