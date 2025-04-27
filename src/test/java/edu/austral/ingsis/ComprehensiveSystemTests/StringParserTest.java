package edu.austral.ingsis.ComprehensiveSystemTests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.austral.ingsis.clifford.parser.StringParser;
import org.junit.jupiter.api.Test;

public class StringParserTest {

  private final StringParser parser = new StringParser();

  @Test
  void shouldParseValidCommands() {
    // Just verify no exceptions are thrown for valid commands
    assertDoesNotThrow(() -> parser.parse("ls"));
    assertDoesNotThrow(() -> parser.parse("cd test"));
    assertDoesNotThrow(() -> parser.parse("mkdir test"));
    assertDoesNotThrow(() -> parser.parse("touch test"));
    assertDoesNotThrow(() -> parser.parse("pwd"));
    assertDoesNotThrow(() -> parser.parse("rm test"));
    assertDoesNotThrow(() -> parser.parse("rm --recursive test"));
  }

  @Test
  void shouldThrowExceptionForUnknownCommand() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> parser.parse("unknown_command"));

    assertEquals("Unknown command: unknown_command", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionForEmptyCommand() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> parser.parse(""));

    assertEquals("Unknown command: ", exception.getMessage());
  }
}
