package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.Element;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Ls implements Command<Directory> {
  private final String sortOrder;

  public Ls() {
    this("");
  }

  public Ls(String sortOrder) {
    this.sortOrder = sortOrder;
  }

  @Override
  public CommandResult<Directory> execute(Directory directory) {
    List<Element> elements = directory.getElements();

    if (elements.isEmpty()) {
      return CommandResult.withoutChange(directory, "");
    }

    List<Element> sortedElements = new ArrayList<>(elements);

    if (sortOrder.equals("asc")) {
      sortedElements.sort(Comparator.comparing(Element::getName));
    } else if (sortOrder.equals("desc")) {
      sortedElements.sort(Comparator.comparing(Element::getName).reversed());
    }

    List<String> names = new ArrayList<>();
    for (Element e : sortedElements) {
      names.add(e.getName());
    }

    return CommandResult.withoutChange(directory, String.join(" ", names));
  }
}
