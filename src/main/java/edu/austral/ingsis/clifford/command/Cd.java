package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.Element;
import edu.austral.ingsis.clifford.element.Type;

public final class Cd implements Command<Directory> {
  private final String path;

  public Cd(String path) {
    this.path = path;
  }

  @Override
  public CommandResult<Directory> execute(Directory currentDirectory) {
    if (path.isEmpty() || path.equals(".")) {
      return CommandResult.withoutChange(currentDirectory, "moved to directory '.'");
    }

    if (path.equals("..")) {
      Directory parent = currentDirectory.parent();
      if (parent == null) {
        return CommandResult.withoutChange(currentDirectory, "moved to directory '/'");
      }
      return CommandResult.withoutChange(parent, "moved to directory '" + parent.getName() + "'");
    }

    if (path.startsWith("/")) {
      Directory root = getRootDirectory(currentDirectory);

      if (path.equals("/")) {
        return CommandResult.withoutChange(root, "moved to directory '/'");
      }

      String relativePath = path.substring(1);
      return navigatePath(root, relativePath);
    }

    return navigatePath(currentDirectory, path);
  }

  private Directory getRootDirectory(Directory dir) {
    Directory currentDir = dir;
    while (currentDir.parent() != null) {
      currentDir = currentDir.parent();
    }
    return currentDir;
  }

  private CommandResult<Directory> navigatePath(Directory startDir, String path) {
    String[] pathComponents = path.split("/");
    Directory currentDir = startDir;

    for (String component : pathComponents) {
      if (component.isEmpty() || component.equals(".")) {
        continue;
      }

      if (component.equals("..")) {
        Directory parent = currentDir.parent();
        if (parent == null) {
          continue;
        }
        currentDir = parent;
        continue;
      }

      Element nextElement = currentDir.getElement(component);

      if (nextElement == null) {
        return CommandResult.withoutChange(
            startDir, "'" + component + "' directory does not exist");
      }

      if (nextElement.getType() != Type.DIRECTORY) {
        return CommandResult.withoutChange(startDir, "'" + component + "' is not a directory");
      }

      currentDir = (Directory) nextElement;
    }

    return CommandResult.withoutChange(
        currentDir,
        "moved to directory '"
            + (currentDir.getName().equals("/") ? "/" : currentDir.getName())
            + "'");
  }
}
