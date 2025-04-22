package edu.austral.ingsis;

import edu.austral.ingsis.clifford.CLI;
import java.util.List;

public class FileSystemRunnerImplementation implements FileSystemRunner {
  private final CLI cli = new CLI();

  @Override
  public List<String> executeCommands(List<String> commands) {
    return cli.executeCommands(commands);
  }
}
