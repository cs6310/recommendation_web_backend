package models;

public class Student extends Person
{
  private String permission_type;

  Student(int id) {
    super(id);
  }

  public String get_permission() {
	  return permission_type;
  }
}
