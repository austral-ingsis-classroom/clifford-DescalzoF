package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.element.Element;

public interface Command {
  Object execute(Element element, String flag);
}
