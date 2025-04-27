package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.File;

public final class Touch implements Command<Directory> {
  private final String fileName;

  public Touch(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public CommandResult<Directory> execute(Directory currentDirectory) {
    if (fileName.isEmpty()) {
      return CommandResult.withoutChange(currentDirectory, "No file name provided");
    }

    if (fileName.contains("/") || fileName.contains(" ")) {
      return CommandResult.withoutChange(currentDirectory, "Invalid file name");
    }

    if (currentDirectory.getElement(fileName) != null) {
      return CommandResult.withoutChange(currentDirectory, "'" + fileName + "' already exists");
    }

    File newFile = new File(fileName, currentDirectory, "");

    Directory updatedCurrentDir = currentDirectory.withElement(newFile);

    Directory newRoot = FileSystem.rebuildBranch(currentDirectory, updatedCurrentDir);

    return new CommandResult<>(newRoot, updatedCurrentDir, "'" + fileName + "' file created");
  }
}
