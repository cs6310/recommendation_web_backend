package models;

// This class is for a Teaching Assistant
public class TA extends Person
{
  private String permission_type;

  /**
   * Constructor
   * @param id (required) The TA id.
   */
  TA(int id) {
    super(id);
  }

  /**
   * @return a string that is the TAs permission level
   */
  public String get_permission() {
	  return permission_type;
  }
}
