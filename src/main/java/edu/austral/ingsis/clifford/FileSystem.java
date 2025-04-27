package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.command.CommandResult;
import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.Element;
import edu.austral.ingsis.clifford.element.Type;

public class FileSystem {
  private final Directory root;
  private final Directory currentDirectory;

  public FileSystem() {
    this.root = new Directory();
    this.currentDirectory = this.root;
  }

  private FileSystem(Directory root, Directory currentDirectory) {
    this.root = root;
    this.currentDirectory = currentDirectory;
  }

  public Directory getCurrentDirectory() {
    return currentDirectory;
  }

  public FileSystem apply(CommandResult<Directory> result) {
    if (result.newRoot() == null) {
      Directory targetDir = result.currentDirectory();

      if (targetDir != root && targetDir != currentDirectory) {
        Directory resolvedDir = findDirectoryInTree(root, targetDir.getLocation());
        if (resolvedDir != null) {
          return new FileSystem(root, resolvedDir);
        }
      }

      return new FileSystem(root, targetDir);
    }

    return new FileSystem(result.newRoot(), result.currentDirectory());
  }

  private Directory findDirectoryInTree(Directory searchRoot, String path) {
    if (path.equals("/")) {
      return root;
    }

    if (!path.startsWith("/")) {
      path = "/" + path;
    }

    String[] components = path.split("/");
    Directory current = searchRoot;

    for (int i = 1; i < components.length; i++) {
      String component = components[i];
      if (component.isEmpty()) continue;

      Element element = current.getElement(component);
      if (element == null || element.getType() != Type.DIRECTORY) {
        return null;
      }

      current = (Directory) element;
    }

    return current;
  }

  public static Directory rebuildBranch(Directory originalDirectory, Directory modifiedDirectory) {
    if (originalDirectory.parent() == null) {
      return modifiedDirectory;
    }

    Directory newParent = originalDirectory.parent().withoutElement(originalDirectory.getName());
    newParent = newParent.withElement(modifiedDirectory);

    return rebuildBranch(originalDirectory.parent(), newParent);
  }
}
