package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Semester
{
	@Id
	// id for keeping track of semesters
	private int id;
	// name of the semester such as "Fall 2016"
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

	/**
	 * @return the id for the Semester as an int
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id (required) the id for the Semester as an int
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the string for the semester's name
	 */
	public String getSemesterName() {
		return semesterName;
	}

	/**
	 * @param semesterName (required) the string for the semester's name
	 */
	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}

	/**
	 * @return a string representation of the semester
	 */
	public String toString() {
		return "id: " + id
				+ " semesterName: " + semesterName;
	}

}
