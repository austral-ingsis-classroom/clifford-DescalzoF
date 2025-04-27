package edu.austral.ingsis.commandTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import edu.austral.ingsis.clifford.command.CommandResult;
import edu.austral.ingsis.clifford.command.Rm;
import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RmTest {

  private Directory root;
  private Directory home;
  private Directory documents;
  private File readme;
  private File config;

  @BeforeEach
  void setUp() {
    root = new Directory();
    home = new Directory("home", root);
    documents = new Directory("documents", home);
    readme = new File("readme.txt", home, "This is a readme file");
    config = new File("config.json", home, "{}");

    root = root.withElement(home);
    home = home.withElement(documents).withElement(readme).withElement(config);
  }

  @Test
  void removeFile() {
    Rm rm = new Rm("readme.txt");
    CommandResult result = rm.execute(home);

    Directory updatedHome = (Directory) result.currentDirectory();
    assertNull(updatedHome.getElement("readme.txt"));
    assertNotNull(updatedHome.getElement("config.json"));
    assertEquals("'readme.txt' removed", result.message());
  }

  @Test
  void removeNonExistentFile() {
    Rm rm = new Rm("nonexistent.txt");
    CommandResult result = rm.execute(home);

    assertEquals(home, result.currentDirectory());
    assertEquals("'nonexistent.txt' does not exist", result.message());
  }

  @Test
  void removeDirectoryWithoutRecursiveFlag() {
    Rm rm = new Rm("documents");
    CommandResult result = rm.execute(home);

    assertEquals(home, result.currentDirectory());
    assertNotNull(((Directory) result.currentDirectory()).getElement("documents"));
    assertEquals("cannot remove 'documents', is a directory", result.message());
  }

  @Test
  void removeDirectoryWithRecursiveFlag() {
    Rm rm = new Rm("documents", true);
    CommandResult result = rm.execute(home);

    Directory updatedHome = (Directory) result.currentDirectory();
    assertNull(updatedHome.getElement("documents"));
    assertNotNull(updatedHome.getElement("readme.txt"));
    assertEquals("'documents' removed", result.message());
  }

  @Test
  void removeWithEmptyName() {
    Rm rm = new Rm("");
    CommandResult result = rm.execute(home);

    assertEquals(home, result.currentDirectory());
    assertEquals("No element name provided", result.message());
  }

  @Test
  void removeMultipleFilesSequentially() {
    Rm rm1 = new Rm("readme.txt");
    CommandResult result1 = rm1.execute(home);

    Directory updatedHome = (Directory) result1.currentDirectory();
    assertNull(updatedHome.getElement("readme.txt"));

    Rm rm2 = new Rm("config.json");
    CommandResult result2 = rm2.execute(updatedHome);

    Directory finalHome = (Directory) result2.currentDirectory();
    assertNull(finalHome.getElement("config.json"));
    assertEquals("'config.json' removed", result2.message());
  }
}
