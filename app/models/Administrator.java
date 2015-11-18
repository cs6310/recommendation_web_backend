package models;

public class Administrator extends Person
{
  private String permission_type;
	
  Administrator(int id) {
    super(id);
  }
  
  public String get_permission() {
	  return permission_type;
  }

}
