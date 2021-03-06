package models;

public class Administrator extends Person
{
  private String permission_type;

  /**
   * Constructor
   * @param id (required) The Administrator id.
   */
  Administrator(int id) {
    super(id);
  }

  /**
   * @return a string that is the Administrator's permission level
   */
  public String get_permission() {
	  return permission_type;
  }

}
