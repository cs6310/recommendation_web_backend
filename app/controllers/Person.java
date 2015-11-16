package controllers;

import java.util.ArrayList;

public class Person
{
  private int id;

  Person(int id) {
    // we are not actually going to implement the person class
    // so a constructor for it should blow up
    setId(id);
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
