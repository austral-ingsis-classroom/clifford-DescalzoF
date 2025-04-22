package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.Element;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ls implements Command {

  @Override
  public String execute(Element element, String flag) {
    Directory directory = (Directory) element;
    List<Element> elements = directory.getElements();

    if (elements.isEmpty()) {
      return "";
    }

    if (flag.equals("asc")) {
      elements.sort(Comparator.comparing(Element::getName));
    } else if (flag.equals("desc")) {
      elements.sort(Comparator.comparing(Element::getName).reversed());
    }

    List<String> names = new ArrayList<>();
    for (Element e : elements) {
      names.add(e.getName());
    }

    return String.join(" ", names);
  }
}
