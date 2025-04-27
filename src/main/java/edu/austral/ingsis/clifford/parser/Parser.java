package edu.austral.ingsis.clifford.parser;

import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.element.Directory;

public interface Parser<T> {
  Command<Directory> parse(T input);
}
