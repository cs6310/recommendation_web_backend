package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/**
 * Class to handle Administrator users input
 * provided through files.
 */
public class AdministratorRequest {
	// List of classes offered next semester and their enrollment limit
	private Map<Integer,CourseSemester> classEnrollment;
	//List of classes offered next semester and their preference(the amount of students that wish to take a class)
	private Map<Integer,Integer> studentPreference;
	// Professor-Course Assignment
	private Map<Course, Professor> professorCourse;
	// TA-Course Assignment
	private Map<Course, TA> taCourseAssignment;
	// List of files with the information to be parsed.
	private List<File> inputFileList;

	//Default Constructor
	public AdministratorRequest(){
	}

	/**
	 * Constructor
	 * @param fileList (required) the list for the config files
	 */
	public AdministratorRequest(List<File> fileList){
		inputFileList = fileList;
		classEnrollment = new TreeMap<Integer, CourseSemester>();
		professorCourse = new HashMap<Course, Professor>();
		taCourseAssignment = new HashMap<Course, TA>();
	}
	//Could implement in parallel as a future enhancement
	public void processInput(){
		processInputClassEnrollment();
		processInputProfCourse();
		processInputTACourse();

	}

	/**
	 * This method contains all the logic for parsing the input and setting the state of the system
	 * @return whether the parsing was successful or not
	 */
	public boolean processInputClassEnrollment(){
		boolean result = false;
//		% Format is: Course.id;Course.courseName;CourseSemester.enrollment_limit
//		5001;CS6210;30;
		File file = inputFileList.get(0);
		try{
			FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String line;
		    while((line = br.readLine()) != null){
		        if(line.contains("%")){
		        	System.out.println("Comment Line found. Skipping...");
		        	continue;
		        }
		        System.out.println(line);
		        addClassEnrollmentInfo(line, ";");
		    }
		    br.close();
		}catch(IOException e){
			System.out.println("IOException: " + e.getMessage());
		}
		return result;
	}

	/**
	 * Method to parse lines read from file into entries in the classEnrollment map
	 * @param line
	 * @param separator
	 */
	private void addClassEnrollmentInfo(String line, String separator){
		String[] arr = line.split(";");
		for(String s: arr){
			System.out.println("Token: " + s);
		}
        if(arr.length == 3){
        	int id = Integer.parseInt(arr[0]);
            int enrollLimit = Integer.parseInt(arr[2]);
            classEnrollment.put(id,
            		new CourseSemester(true, new Course(id, arr[1]), new Semester(0,"Spring 2016"),enrollLimit));

        }
	}

	/**
	 * @return whether or not the parsing was successful
	 */
	public boolean processInputProfCourse(){
		boolean result = false;
//		% Format is: Professor.id;Course.id;Course.courseName;
//		601;5001;CS6210;
		File file = inputFileList.get(1);
		try{
			FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String line;
		    while((line = br.readLine()) != null){
		        if(line.contains("%")){
		        	System.out.println("Comment Line found. Skipping...");
		        	continue;
		        }
		        System.out.println(line);
		        addProfessorCourseInfo(line, ";");
		    }
		    br.close();
		}catch(IOException e){
			System.out.println("IOException: " + e.getMessage());
		}

		return true;
	}
	/**
	 * Method to parse lines read from file into entries in the professorCourse map
	 * @param line
	 * @param separator
	 */
	private void addProfessorCourseInfo(String line, String separator){
		String[] arr = line.split(";");
		for(String s: arr){
			System.out.println("Prof-Course Token: " + s);
		}
        if(arr.length == 3){
        	int id = Integer.parseInt(arr[0]);
            professorCourse.put(new Course(id, arr[1]), new Professor(id)) ;
        }
	}

	/**
	 * @return whether or not the parsing was successful
	 */
	public boolean processInputTACourse(){
		boolean result = false;
//		% Format is: TA.id;Course.id;Course.courseName;
//		801;5001;CS6210;
		File file = inputFileList.get(1);
		try{
			FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String line;
		    while((line = br.readLine()) != null){
		        if(line.contains("%")){
		        	System.out.println("Comment Line found. Skipping...");
		        	continue;
		        }
		        System.out.println(line);
		        addTACourseInfo(line, ";");
		    }
		    br.close();
		}catch(IOException e){
			System.out.println("IOException: " + e.getMessage());
		}

		return true;
	}
	/**
	 * Method to parse lines read from file into entries in the professorCourse map
	 * @param line
	 * @param separator
	 */
	private void addTACourseInfo(String line, String separator){
		String[] arr = line.split(";");
		for(String s: arr){
			System.out.println("TA-Course Token: " + s);
		}
        if(arr.length == 3){
        	int id = Integer.parseInt(arr[0]);
            taCourseAssignment.put(new Course(id, arr[1]), new TA(id));
        }
	}

 	/**
	 * @return a hash of students mapping to the course semesters they are taking
	 */
	public Map<Integer, CourseSemester> getClassEnrollment() {
		return classEnrollment;
	}

	/**
	 * @param a hash of students mapping to the course semesters they are taking
	 */
	public void setClassEnrollment(Map<Integer, CourseSemester> classEnrollment) {
		this.classEnrollment = classEnrollment;
	}

	/**
	 * @return a mapping from courses to professors to communicate which professors can teach which courses
	 */
	public Map<Course, Professor> getProfessorCourse() {
		return professorCourse;
	}

	/**
	 * @param a mapping from courses to professors to communicate which professors can teach which courses
	 */
	public void setProfessorCourse(Map<Course, Professor> professorCourse) {
		this.professorCourse = professorCourse;
	}

	/**
	 * @return a mapping from courses to TAs to communicate which TAs can teach which courses
	 */
	public Map<Course, TA> getTaCourseAssignment() {
		return taCourseAssignment;
	}

	/**
	 * @param a mapping from courses to TAs to communicate which TAs can teach which courses
	 */
	public void setTaCourseAssignment(Map<Course, TA> taCourseAssignment) {
		this.taCourseAssignment = taCourseAssignment;
	}

	/**
	 * @return the list of files used for sending input
	 */
	public List<File> getInputFileList() {
		return inputFileList;
	}

	/**
	 * @param the list of files used for sending input
	 */
	public void setInputFileList(List<File> inputFileList) {
		this.inputFileList = inputFileList;
	}

	/**
	 * @return a mapping of students to their preferences
	 */
	public Map<Integer, Integer> getStudentPreference() {
		return studentPreference;
	}

	/**
	 * @param a mapping of students to their preferences
	 */
	public void setStudentPreference(Map<Integer, Integer> studentPreference) {
		this.studentPreference = studentPreference;
	}


}
