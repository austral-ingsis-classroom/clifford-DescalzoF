package edu.austral.ingsis.clifford.element;

public sealed interface Element permits Directory, File {
  String getName();

  Type getType();

  String getLocation();
}
