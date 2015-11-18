package models;

public class Ta extends Person
{
  private String permission_type;
  Ta(int id) {
    super(id);
  }

  public String get_permission() {
	  return permission_type;
  }
}
