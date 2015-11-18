package models;

public class Person
{
  private String first_name;
  private String last_name;
  private int UID;
  private String username;

  Person(int id) {
    // we are not actually going to implement the person class
    // so a constructor for it should blow up
    this.UID = id;
  }
  
  public String get_first_name() {
	  return first_name;
  }
  
  public String get_last_name() {
	  return last_name;
  }
  
  public int get_UID() {
	  return UID;
  }
  
  public String get_username() {
	  return username;
  }
  
}
