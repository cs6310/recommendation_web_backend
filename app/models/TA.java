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

  public String get_permission() {
	  return permission_type;
  }
}
