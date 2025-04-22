package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.Element;
import edu.austral.ingsis.clifford.element.Type;

public class Rm implements Command {
  @Override
  public String execute(Element element, String flag) {
    Directory currentDirectory = (Directory) element;
    String[] parts = flag.split(" ", 2);

    boolean recursive = false;
    String targetName;

    if (parts[0].equals("--recursive")) {
      recursive = true;
      targetName = parts.length > 1 ? parts[1] : "";
    } else {
      targetName = parts[0];
    }

    Element target = currentDirectory.getElement(targetName);

    if (target == null) {
      return "'" + targetName + "' does not exist";
    }

    if (target.getType() == Type.DIRECTORY && !recursive) {
      return "cannot remove '" + targetName + "', is a directory";
    }

    currentDirectory.removeElement(targetName);
    return "'" + targetName + "' removed";
  }
}
