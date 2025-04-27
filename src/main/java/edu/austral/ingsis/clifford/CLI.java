package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.command.CommandResult;
import edu.austral.ingsis.clifford.element.Directory;
import edu.austral.ingsis.clifford.parser.Parser;
import edu.austral.ingsis.clifford.parser.StringParser;
import java.util.ArrayList;
import java.util.List;

public class CLI {
  private FileSystem filesystem;
  private final Parser<String> parser;

  public CLI() {
    this.filesystem = new FileSystem();
    this.parser = new StringParser();
  }

  public List<String> executeCommands(List<String> commandLines) {
    List<String> results = new ArrayList<>();

    for (String commandLine : commandLines) {
      String result = executeCommand(commandLine);
      results.add(result);
    }

    return results;
  }

  private String executeCommand(String commandLine) {
    try {
      Command<Directory> command = parser.parse(commandLine);
      Directory currentDirectory = filesystem.getCurrentDirectory();

      CommandResult<Directory> result = command.execute(currentDirectory);

      filesystem = filesystem.apply(result);

      return result.message();
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    }
  }
}
