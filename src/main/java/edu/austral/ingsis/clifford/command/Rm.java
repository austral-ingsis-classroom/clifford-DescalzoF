package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.Element;
import edu.austral.ingsis.clifford.element.Type;

public final class Rm implements Command<Directory> {
  private final String targetName;
  private final boolean recursive;

  public Rm(String targetName) {
    this(targetName, false);
  }

  public Rm(String targetName, boolean recursive) {
    this.targetName = targetName;
    this.recursive = recursive;
  }

  @Override
  public CommandResult<Directory> execute(Directory currentDirectory) {
    if (targetName.isEmpty()) {
      return CommandResult.withoutChange(currentDirectory, "No element name provided");
    }

    Element target = currentDirectory.getElement(targetName);

    if (target == null) {
      return CommandResult.withoutChange(currentDirectory, "'" + targetName + "' does not exist");
    }

    if (target.getType() == Type.DIRECTORY && !recursive) {
      return CommandResult.withoutChange(
          currentDirectory, "cannot remove '" + targetName + "', is a directory");
    }

    Directory updatedCurrentDir = currentDirectory.withoutElement(targetName);
    Directory newRoot = FileSystem.rebuildBranch(currentDirectory, updatedCurrentDir);

    return new CommandResult<>(newRoot, updatedCurrentDir, "'" + targetName + "' removed");
  }
}
