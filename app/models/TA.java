package models;

// This class is for a Teaching Assistant
public class TA extends Person
{
  private String permission_type;

  TA(int id) {
    super(id);
  }

  public String get_permission() {
	  return permission_type;
  }
}
