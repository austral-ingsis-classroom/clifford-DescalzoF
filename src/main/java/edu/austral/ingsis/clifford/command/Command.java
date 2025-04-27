package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.element.Element;

public sealed interface Command<T extends Element> permits Cd, Ls, Mkdir, Pwd, Touch, Rm {
  CommandResult<T> execute(T element);
}
