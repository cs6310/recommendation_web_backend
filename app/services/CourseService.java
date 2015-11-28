package services;

import models.Course;
import play.db.jpa.JPA;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CourseService {
	public Course getById(int id) {
		return JPA.em().find(Course.class, id);
	}
	public Collection<Course> getAllCourses() {
		EntityManager em = JPA.em();
		String queryString = "SELECT c from Course c"; //HSQL syntax
		TypedQuery<Course> query = em.createQuery(queryString, Course.class);
		return query.getResultList();		
	}
	public boolean storeCourse(Course course) {
		EntityManager em = JPA.em();
		try {
			em.persist(course);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}