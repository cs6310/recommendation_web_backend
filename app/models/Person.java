package models;



import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Entity;
import javax.persistence.InheritanceType;

/* The Person class is an abstract class that was designed to be inherited from
 * by all of the different types of people in our system. The basic logic about
 * a person is contained in this class.
*/
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Person
{
  private String first_name;
  private String last_name;
  @Id
  @Column(name="PERSON_ID")
  private int UID;
  private String password;

  /**
   * Constructor
   * @param id (required) The Person id.
   */
  Person(int id) {
    this.UID = id;
    this.password = helpers.Constants.DEFAULT_PASSWORD;
  }

  /* getter for a persons first name
   * @return a string for the first name
   */
  public String get_first_name() {
	  return first_name;
  }

  /* getter for a persons last name
   * @return a string for the last name
   */
  public String get_last_name() {
	  return last_name;
  }

  /* getter for a persons unique ID number
   * @return an int for the ID
   */
  public int get_UID() {
	  return UID;
  }


  /* getter for a persons password
   * @return a string for checking the password
   */
  public String getPassword() {
	  return password;
  }

  /* setter for a persons password
   * @param newPassword (required) a new Password for the user
   * @return nothing
   */
  public void setPassword(String newPassword) {
	  this.password = newPassword;
  }

}
