package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.element.Element;

public interface Command {
  String execute(Element element, String flag);
}
