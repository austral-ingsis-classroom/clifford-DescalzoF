package edu.austral.ingsis.ComprehensiveSystemTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.command.CommandResult;
import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.element.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SystemTest {

  private FileSystem fileSystem;
  private Directory root;
  private Directory home;
  private Directory documents;
  private Directory pictures;
  private File config;

  @BeforeEach
  void setUp() {
    fileSystem = new FileSystem();
    root = fileSystem.getCurrentDirectory();

    home = new Directory("home", root);
    documents = new Directory("documents", home);
    pictures = new Directory("pictures", home);
    config = new File("config.txt", home, "config content");

    root = root.withElement(home);
    home = home.withElement(documents).withElement(pictures).withElement(config);
    fileSystem = new FileSystem();
  }

  @Test
  void testApplyWithNewRoot() {
    Directory newRoot = new Directory();
    Directory newDir = new Directory("newdir", newRoot);
    newRoot = newRoot.withElement(newDir);

    CommandResult<Directory> result = new CommandResult<>(newRoot, newDir, "test message");

    FileSystem newFileSystem = fileSystem.apply(result);

    assertEquals(newDir, newFileSystem.getCurrentDirectory());
    assertEquals("newdir", newFileSystem.getCurrentDirectory().getName());
  }
}
