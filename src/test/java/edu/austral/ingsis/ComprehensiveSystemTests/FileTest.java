package edu.austral.ingsis.ComprehensiveSystemTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.File;
import org.junit.jupiter.api.Test;

public class FileTest {

  @Test
  void getLocationWithRootParent() {
    Directory root = new Directory();
    File file = new File("test.txt", root, "content");

    assertEquals("/test.txt", file.getLocation());
  }

  @Test
  void getLocationWithNestedDirectories() {
    Directory root = new Directory();
    Directory dir1 = new Directory("dir1", root);
    Directory dir2 = new Directory("dir2", dir1);
    File file = new File("test.txt", dir2, "content");

    assertEquals("/dir1/dir2/test.txt", file.getLocation());
  }

  @Test
  void getLocationWithSingleLevelNesting() {
    Directory root = new Directory();
    Directory dir = new Directory("documents", root);
    File file = new File("test.txt", dir, "content");

    assertEquals("/documents/test.txt", file.getLocation());
  }
}
