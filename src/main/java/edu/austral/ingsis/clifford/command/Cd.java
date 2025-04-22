package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.Element;
import edu.austral.ingsis.clifford.element.Type;

public class Cd implements Command {
  private String result;

  @Override
  public Object execute(Element element, String flag) {
    Directory currentDir = (Directory) element;

    if (flag.isEmpty() || flag.equals(".")) {
      result = "moved to directory '.'";
      return currentDir;
    }

    if (flag.equals("..")) {
      Directory parent = currentDir.getParent();
      if (parent == null) {
        parent = currentDir;
      }
      result = "moved to directory '/'";
      return parent;
    }

    if (flag.startsWith("/")) {
      // Navigate from root
      Directory root = currentDir;
      while (root.getParent() != null) {
        root = root.getParent();
      }

      if (flag.equals("/")) {
        result = "moved to directory '/'";
        return root;
      }

      return navigatePath(root, flag.substring(1));
    } else {
      // Relative path
      return navigatePath(currentDir, flag);
    }
  }

  private Directory navigatePath(Directory startDir, String path) {
    Directory current = startDir;
    String[] parts = path.split("/");

    for (String part : parts) {
      if (part.equals(".")) {
        continue;
      }

      if (part.equals("..")) {
        if (current.getParent() != null) {
          current = current.getParent();
        }
        continue;
      }

      Element nextElement = current.getElement(part);
      if (nextElement == null) {
        result = "'" + part + "' directory does not exist";
        return current;
      }

      if (nextElement.getType() != Type.DIRECTORY) {
        result = "'" + part + "' is not a directory";
        return current;
      }

      current = (Directory) nextElement;
    }

    result = "moved to directory '" + current.getName() + "'";
    return current;
  }

  public String getResult() {
    return result;
  }
}
