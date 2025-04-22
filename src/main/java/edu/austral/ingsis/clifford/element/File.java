package edu.austral.ingsis.clifford.element;

public class File implements Element {
  private final String fileName;
  private Directory location;
  private String content;

  public File(String fileName, Directory location, String content) {
    this.fileName = fileName;
    this.location = location;
    this.content = content;
  }

  @Override
  public String getName() {
    return fileName;
  }

  @Override
  public Type getType() {
    return Type.FILE;
  }

  @Override
  public String getLocation() {
    return location.getLocation() + "/" + fileName;
  }
}
