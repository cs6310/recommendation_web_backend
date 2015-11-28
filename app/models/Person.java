package models;

/* The Person class is an abstract class that was designed to be inherited from
  * by all of the different types of people in our system. The basic logic about
  * a person is contained in this class.
*/
public class Person
{
  private String first_name;
  private String last_name;
  private int UID;
  private String username;

  Person(int id) {
    this.UID = id;
  }

  /* getter for a persons first name
   * returns a string
   */
  public String get_first_name() {
	  return first_name;
  }

  /* getter for a persons last name
   * returns a string
   */
  public String get_last_name() {
	  return last_name;
  }

  /* getter for a persons unique ID number
   * returns an int
   */
  public int get_UID() {
	  return UID;
  }

  /* getter for a persons username
   * returns an String
   */
  public String get_username() {
	  return username;
  }

}
