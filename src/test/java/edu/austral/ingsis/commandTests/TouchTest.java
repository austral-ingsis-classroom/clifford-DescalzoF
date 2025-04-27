package edu.austral.ingsis.commandTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.austral.ingsis.clifford.command.CommandResult;
import edu.austral.ingsis.clifford.command.Touch;
import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.Element;
import edu.austral.ingsis.clifford.element.File;
import edu.austral.ingsis.clifford.element.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TouchTest {

  private Directory root;
  private Directory home;

  @BeforeEach
  void setUp() {
    root = new Directory();
    home = new Directory("home", root);
    root = root.withElement(home);
  }

  @Test
  void createFile() {
    Touch touch = new Touch("test.txt");
    CommandResult result = touch.execute(home);

    Directory updatedHome = (Directory) result.currentDirectory();
    Element newFile = updatedHome.getElement("test.txt");

    assertNotNull(newFile);
    assertEquals(Type.FILE, newFile.getType());
    assertEquals("test.txt", newFile.getName());
    assertEquals("'test.txt' file created", result.message());
  }

  @Test
  void createFileWithEmptyName() {
    Touch touch = new Touch("");
    CommandResult result = touch.execute(home);

    assertEquals(home, result.currentDirectory());
    assertEquals("No file name provided", result.message());
  }

  @Test
  void createFileWithInvalidName() {
    Touch touch = new Touch("test/file.txt");
    CommandResult result = touch.execute(home);

    assertEquals(home, result.currentDirectory());
    assertEquals("Invalid file name", result.message());
  }

  @Test
  void createFileWithSpaceInName() {
    Touch touch = new Touch("test file.txt");
    CommandResult result = touch.execute(home);

    assertEquals(home, result.currentDirectory());
    assertEquals("Invalid file name", result.message());
  }

  @Test
  void createFileThatAlreadyExists() {
    File existingFile = new File("existing.txt", home, "content");
    home = home.withElement(existingFile);

    Touch touch = new Touch("existing.txt");
    CommandResult result = touch.execute(home);

    assertEquals(home, result.currentDirectory());
    assertEquals("'existing.txt' already exists", result.message());
  }

  @Test
  void createMultipleFilesSequentially() {
    Touch touch1 = new Touch("file1.txt");
    CommandResult result1 = touch1.execute(home);

    Directory updatedHome = (Directory) result1.currentDirectory();
    assertNotNull(updatedHome.getElement("file1.txt"));

    Touch touch2 = new Touch("file2.txt");
    CommandResult result2 = touch2.execute(updatedHome);

    Directory finalHome = (Directory) result2.currentDirectory();
    assertNotNull(finalHome.getElement("file1.txt"));
    assertNotNull(finalHome.getElement("file2.txt"));
  }
}
