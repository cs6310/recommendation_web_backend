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

  Person(int id) {
    this.UID = id;
    this.password = helpers.Constants.DEFAULT_PASSWORD;
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

 
  public String getPassword() {
	  return password;
  }
  
  public void setPassword(String newPassword) {
	  this.password = newPassword;
  }

}
