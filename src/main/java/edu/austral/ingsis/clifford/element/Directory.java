package edu.austral.ingsis.clifford.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public record Directory(String name, Directory parent, Map<String, Element> children)
    implements Element {

  public Directory {
    children = Collections.unmodifiableMap(new LinkedHashMap<>(children));
  }

  public Directory(String name, Directory parent) {
    this(name, parent, new LinkedHashMap<>());
  }

  public Directory() {
    this("/", null, new LinkedHashMap<>());
  }

  @Override
  public String getName() {
    return name;
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
    if (parent.parent == null) {
      return "/" + name;
    }
    return parent.getLocation() + "/" + name;
  }

  public Element getElement(String elementName) {
    return children.get(elementName);
  }

  public List<Element> getElements() {
    return new ArrayList<>(children.values());
  }

  public Directory withElement(Element element) {
    LinkedHashMap<String, Element> newChildren = new LinkedHashMap<>(children);
    newChildren.put(element.getName(), element);
    return new Directory(name, parent, newChildren);
  }

  public Directory withoutElement(String elementName) {
    LinkedHashMap<String, Element> newChildren = new LinkedHashMap<>(children);
    newChildren.remove(elementName);
    return new Directory(name, parent, newChildren);
  }
}
