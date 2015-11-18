package models;

public class Professor extends Person
{
  private String permission_type;

  Professor(int id) {
    super(id);
  }

  public String get_permission() {
	  return permission_type;
  }
}
