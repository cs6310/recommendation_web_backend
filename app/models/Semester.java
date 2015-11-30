package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Semester
{
	@Id
	private int id;
	private String semesterName;

	public Semester() {}

		/**
	   * Constructor
	   * @param id (required) The id for the semester
	   * @param semesterName (required) The name of the semester like Fall, Spring, or Summer.
	   */
	public Semester(int id, String semesterName) {
		this.id = id;
		this.semesterName = semesterName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSemesterName() {
		return semesterName;
	}

	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}

	public String toString() {
		return "id: " + id
				+ " semesterName: " + semesterName;
	}

}
