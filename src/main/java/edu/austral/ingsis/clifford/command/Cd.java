package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.Element;
import edu.austral.ingsis.clifford.element.Type;

public class Cd implements Command {
  private String result;
  private Directory newDirectory;

  @Override
  public String execute(Element element, String flag) {
    Directory currentDir = (Directory) element;
    newDirectory = currentDir;
    if (flag.isEmpty() || flag.equals(".")) {
      result = "moved to directory '.'";
      return result;
    }

    if (flag.equals("..")) {
      Directory parent = currentDir.getParent();
      if (parent == null) {
        parent = currentDir;
      }
      newDirectory = parent;
      result = "moved to directory '/'";
      return result;
    }

    if (flag.startsWith("/")) {
      Directory root = currentDir;
      while (root.getParent() != null) {
        root = root.getParent();
      }

      if (flag.equals("/")) {
        newDirectory = root;
        result = "moved to directory '/'";
        return result;
      }

      navigatePath(root, flag.substring(1));
      return result;
    } else {
      navigatePath(currentDir, flag);
      return result;
    }
  }

  private void navigatePath(Directory startDir, String path) {
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
        newDirectory = current;
        return;
      }

      if (nextElement.getType() != Type.DIRECTORY) {
        result = "'" + part + "' is not a directory";
        newDirectory = current;
        return;
      }

      current = (Directory) nextElement;
    }

    result = "moved to directory '" + current.getName() + "'";
    newDirectory = current;
  }

  public Directory getNewDirectory() {
    return newDirectory;
  }
}