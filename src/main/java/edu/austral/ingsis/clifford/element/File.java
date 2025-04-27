package edu.austral.ingsis.clifford.element;

public record File(String name, Directory parent, String content) implements Element {
  @Override
  public String getName() {
    return name;
  }

  @Override
  public Type getType() {
    return Type.FILE;
  }

  @Override
  public String getLocation() {
    String parentLocation = parent.getLocation();
    if ("/".equals(parentLocation)) {
      return parentLocation + name;
    } else {
      return parentLocation + "/" + name;
    }
  }
}
