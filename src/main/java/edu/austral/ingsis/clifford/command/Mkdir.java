package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.element.Directory;

public final class Mkdir implements Command<Directory> {
  private final String directoryName;

  public Mkdir(String directoryName) {
    this.directoryName = directoryName;
  }

  @Override
  public CommandResult<Directory> execute(Directory currentDirectory) {
    if (directoryName.isEmpty()) {
      return CommandResult.withoutChange(currentDirectory, "No directory name provided");
    }

    if (directoryName.contains("/") || directoryName.contains(" ")) {
      return CommandResult.withoutChange(currentDirectory, "Invalid directory name");
    }

    if (currentDirectory.getElement(directoryName) != null) {
      return CommandResult.withoutChange(
          currentDirectory, "'" + directoryName + "' already exists");
    }

    Directory newDirectory = new Directory(directoryName, currentDirectory);

    Directory updatedCurrentDir = currentDirectory.withElement(newDirectory);

    Directory newRoot = FileSystem.rebuildBranch(currentDirectory, updatedCurrentDir);

    return new CommandResult<>(
        newRoot, updatedCurrentDir, "'" + directoryName + "' directory created");
  }
}
