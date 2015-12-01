package models;

// This is the class to represent a Professor, which inherits from the Person class
public class Professor extends Person
{
  private String permission_type;

  /**
   * Constructor
   * @param id (required) The Professor id.
   */
  Professor(int id) {
    super(id);
  }

  /**
   * @return a string that is the Professor's permission level
   */
  public String get_permission() {
	  return permission_type;
  }
}
