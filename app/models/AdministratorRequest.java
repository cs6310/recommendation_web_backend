package models;

import java.io.File;
import java.util.List;
import java.util.Map;
/**
 * Class to handle Administrator users input 
 * provided through files. 
 *
 */
public class AdministratorRequest {
	// List of classes offered next semester and their enrollment limit
	private List<CourseSemester> classEnrollment;
	// Professor-Course Assignment
	private Map<CourseSemester, Professor>	professorCourse;
	// TA-Course Assignment
	private Map<CourseSemester, TA> taCourseAssignment;
	// List of files with the information to be parsed.
	private List<File> inputFileList;
	
	public AdministratorRequest(List<File> fileList){
		inputFileList = fileList;
	}
	
	public boolean processInput(){		 
		return processInputClassEnrollment() && processInputProfCourse() && processInputTACourse();
	}
	
	public boolean processInputClassEnrollment(){
		boolean result = false; 
		
		
		return result;
	}
	public boolean processInputProfCourse(){
		boolean result = false; 
		
		
		return result;
	}
	public boolean processInputTACourse(){
		boolean result = false; 
		
		
		return result;
	}
	
	public List<CourseSemester> getClassEnrollment() {
		return classEnrollment;
	}
	public void setClassEnrollment(List<CourseSemester> classEnrollment) {
		this.classEnrollment = classEnrollment;
	}
	public Map<CourseSemester, Professor> getProfessorCourse() {
		return professorCourse;
	}
	public void setProfessorCourse(Map<CourseSemester, Professor> professorCourse) {
		this.professorCourse = professorCourse;
	}
	public Map<CourseSemester, TA> getTaCourseAssignment() {
		return taCourseAssignment;
	}
	public void setTaCourseAssignment(Map<CourseSemester, TA> taCourseAssignment) {
		this.taCourseAssignment = taCourseAssignment;
	}
	public List<File> getInputFileList() {
		return inputFileList;
	}
	public void setInputFileList(List<File> inputFileList) {
		this.inputFileList = inputFileList;
	}
	
	
}
