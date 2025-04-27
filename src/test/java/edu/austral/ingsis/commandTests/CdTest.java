package edu.austral.ingsis.commandTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.clifford.command.Cd;
import edu.austral.ingsis.clifford.command.CommandResult;
import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CdTest {

  private Directory root;
  private Directory home;
  private Directory documents;
  private Directory pictures;
  private Directory downloads;
  private File readme;

  @BeforeEach
  void setUp() {
    root = new Directory();
    home = new Directory("home", root);
    documents = new Directory("documents", home);
    pictures = new Directory("pictures", home);
    downloads = new Directory("downloads", home);
    readme = new File("readme.txt", documents, "This is a readme file");

    root = root.withElement(home);
    home = home.withElement(documents).withElement(pictures).withElement(downloads);
    documents = documents.withElement(readme);
  }

  @Test
  void navigateToNonExistentDirectory() {
    Cd cd = new Cd("nonexistent");
    CommandResult result = cd.execute(home);

    assertEquals(home, result.currentDirectory());
    assertEquals("'nonexistent' directory does not exist", result.message());
  }

  @Test
  void navigateToFileNotDirectory() {
    Cd cd = new Cd("readme.txt");
    CommandResult result = cd.execute(documents);

    assertEquals(documents, result.currentDirectory());
    assertEquals("'readme.txt' is not a directory", result.message());
  }

  @Test
  void navigateWithDotPath() {
    Cd cd = new Cd(".");
    CommandResult result = cd.execute(home);

    assertEquals(home, result.currentDirectory());
    assertEquals("moved to directory '.'", result.message());
  }

  @Test
  void navigateToParentOfRoot() {
    Cd cd = new Cd("..");
    CommandResult result = cd.execute(root);

    assertEquals(root, result.currentDirectory());
    assertEquals("moved to directory '/'", result.message());
  }
}
