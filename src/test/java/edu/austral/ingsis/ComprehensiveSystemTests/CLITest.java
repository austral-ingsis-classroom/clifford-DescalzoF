package edu.austral.ingsis.ComprehensiveSystemTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.clifford.CLI;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CLITest {

  @Test
  void shouldHandleIllegalArgumentException() {
    CLI cli = new CLI();

    List<String> commands = Arrays.asList("unknown_command", "invalid");

    List<String> results = cli.executeCommands(commands);

    assertEquals(2, results.size());
    assertEquals("Unknown command: unknown_command", results.get(0));
    assertEquals("Unknown command: invalid", results.get(1));
  }

  @Test
  void shouldExecuteValidCommands() {
    CLI cli = new CLI();

    List<String> commands = Arrays.asList("mkdir test", "ls");

    List<String> results = cli.executeCommands(commands);

    assertEquals(2, results.size());
    assertEquals("'test' directory created", results.get(0));
    assertEquals("test", results.get(1));
  }

  @Test
  void shouldHandleMixOfValidAndInvalidCommands() {
    CLI cli = new CLI();

    List<String> commands = Arrays.asList("mkdir test", "unknown_command", "ls");

    List<String> results = cli.executeCommands(commands);

    assertEquals(3, results.size());
    assertEquals("'test' directory created", results.get(0));
    assertEquals("Unknown command: unknown_command", results.get(1));
    assertEquals("test", results.get(2));
  }
}
