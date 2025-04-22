package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.Element;

public class Pwd implements Command {
  @Override
  public String execute(Element element, String flag) {
    Directory directory = (Directory) element;
    String location = directory.getLocation();

    // Root directory needs special handling
    if (directory.getParent() == null) {
      return "/";
    }

    return location;
  }
}
