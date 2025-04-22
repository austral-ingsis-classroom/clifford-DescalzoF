package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.Element;

public class Mkdir implements Command {
  @Override
  public String execute(Element element, String flag) {
    if (flag.isEmpty()) {
      return "No directory name provided";
    }

    if (flag.contains("/") || flag.contains(" ")) {
      return "Invalid directory name";
    }

    Directory currentDirectory = (Directory) element;
    Directory newDirectory = new Directory(flag, currentDirectory);
    currentDirectory.addElement(newDirectory);

    return "'" + flag + "' directory created";
  }
}
