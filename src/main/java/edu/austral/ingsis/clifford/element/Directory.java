package edu.austral.ingsis.clifford.element;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Directory implements Element {
  private final String directoryName;
  private Directory parent;
  private Map<String, Element> children;
  private List<String> order;

  public Directory(String directoryName, Directory location) {
    this.directoryName = directoryName;
    this.parent = location;
    this.children = new LinkedHashMap<>();
    this.order = new ArrayList<>();
  }

  @Override
  public String getName() {
    return directoryName;
  }

  @Override
  public Type getType() {
    return Type.DIRECTORY;
  }

  @Override
  public String getLocation() {
    if (parent == null) {
      return "/";
    }
    if (parent.getParent() == null) {
      return "/" + directoryName;
    }
    return parent.getLocation() + "/" + directoryName;
  }

  public Element getElement(String name) {
    return children.get(name);
  }

  public void removeElement(String name) {
    children.remove(name);
    order.remove(name);
  }

  public void addElement(Element element) {
    children.put(element.getName(), element);
    order.add(element.getName());
  }

  public Directory getParent() {
    return parent;
  }

  public List<Element> getElements() {
    List<Element> elements = new ArrayList<>();
    for (String name : order) {
      elements.add(children.get(name));
    }
    return elements;
  }
}
