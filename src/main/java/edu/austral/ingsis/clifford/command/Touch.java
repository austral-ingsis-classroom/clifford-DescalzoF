package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.Element;
import edu.austral.ingsis.clifford.element.File;

public class Touch implements Command {
  @Override
  public String execute(Element element, String flag) {
    if (flag.isEmpty()) {
      return "No file name provided";
    }

    if (flag.contains("/") || flag.contains(" ")) {
      return "Invalid file name";
    }

    Directory currentDirectory = (Directory) element;
    File newFile = new File(flag, currentDirectory, "");
    currentDirectory.addElement(newFile);

    return "'" + flag + "' file created";
  }
}
