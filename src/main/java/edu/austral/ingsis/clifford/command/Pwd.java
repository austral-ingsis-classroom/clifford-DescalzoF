package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.element.Directory;

public final class Pwd implements Command<Directory> {
  public Pwd() {}

  @Override
  public CommandResult<Directory> execute(Directory directory) {
    String location = directory.getLocation();

    return CommandResult.withoutChange(directory, location);
  }
}
