package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.element.Element;

public record CommandResult<T extends Element>(T newRoot, T currentDirectory, String message) {
  public static <T extends Element> CommandResult<T> withoutChange(
      T currentDirectory, String message) {
    return new CommandResult<>(null, currentDirectory, message);
  }
}
