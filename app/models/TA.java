package models;

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
