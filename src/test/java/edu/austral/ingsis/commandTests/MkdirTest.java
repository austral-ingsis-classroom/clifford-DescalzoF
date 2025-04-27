package edu.austral.ingsis.commandTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.austral.ingsis.clifford.command.CommandResult;
import edu.austral.ingsis.clifford.command.Mkdir;
import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.Element;
import edu.austral.ingsis.clifford.element.File;
import edu.austral.ingsis.clifford.element.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MkdirTest {

  private Directory root;
  private Directory home;

  @BeforeEach
  void setUp() {
    root = new Directory();
    home = new Directory("home", root);
    root = root.withElement(home);
  }

  @Test
  void createDirectory() {
    Mkdir mkdir = new Mkdir("documents");
    CommandResult result = mkdir.execute(home);

    Directory updatedHome = (Directory) result.currentDirectory();
    Element newDir = updatedHome.getElement("documents");

    assertNotNull(newDir);
    assertEquals(Type.DIRECTORY, newDir.getType());
    assertEquals("documents", newDir.getName());
    assertEquals("'documents' directory created", result.message());
  }

  @Test
  void createDirectoryWithEmptyName() {
    Mkdir mkdir = new Mkdir("");
    CommandResult result = mkdir.execute(home);

    assertEquals(home, result.currentDirectory());
    assertEquals("No directory name provided", result.message());
  }

  @Test
  void createDirectoryWithInvalidName() {
    Mkdir mkdir = new Mkdir("documents/subfolder");
    CommandResult result = mkdir.execute(home);

    assertEquals(home, result.currentDirectory());
    assertEquals("Invalid directory name", result.message());
  }

  @Test
  void createDirectoryWithSpaceInName() {
    Mkdir mkdir = new Mkdir("my documents");
    CommandResult result = mkdir.execute(home);

    assertEquals(home, result.currentDirectory());
    assertEquals("Invalid directory name", result.message());
  }

  @Test
  void createDirectoryThatAlreadyExists() {
    Directory existingDir = new Directory("existing", home);
    home = home.withElement(existingDir);

    Mkdir mkdir = new Mkdir("existing");
    CommandResult result = mkdir.execute(home);

    assertEquals(home, result.currentDirectory());
    assertEquals("'existing' already exists", result.message());
  }

  @Test
  void createDirectoryWithSameNameAsFile() {
    File existingFile = new File("data", home, "content");
    home = home.withElement(existingFile);

    Mkdir mkdir = new Mkdir("data");
    CommandResult result = mkdir.execute(home);

    assertEquals(home, result.currentDirectory());
    assertEquals("'data' already exists", result.message());
  }

  @Test
  void createMultipleDirectoriesSequentially() {
    Mkdir mkdir1 = new Mkdir("dir1");
    CommandResult result1 = mkdir1.execute(home);

    Directory updatedHome = (Directory) result1.currentDirectory();
    assertNotNull(updatedHome.getElement("dir1"));

    Mkdir mkdir2 = new Mkdir("dir2");
    CommandResult result2 = mkdir2.execute(updatedHome);

    Directory finalHome = (Directory) result2.currentDirectory();
    assertNotNull(finalHome.getElement("dir1"));
    assertNotNull(finalHome.getElement("dir2"));
  }
}
