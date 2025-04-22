package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.command.*;
import edu.austral.ingsis.clifford.element.Directory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CLI {
  private Directory root;
  private Directory currentDirectory;
  private Map<String, Command> commands;

  public CLI() {
    this.root = new Directory("", null);
    this.currentDirectory = root;
    commands = new HashMap<>();
    commands.put("ls", new Ls());
    commands.put("cd", new Cd());
    commands.put("touch", new Touch());
    commands.put("mkdir", new Mkdir());
    commands.put("rm", new Rm());
    commands.put("pwd", new Pwd());
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
    String[] parts = commandLine.split(" ", 2);
    String commandName = parts[0];
    String args = parts.length > 1 ? parts[1] : "";

    Command command = commands.get(commandName);
    if (command == null) {
      return "Unknown command: " + commandName;
    }

    if (commandName.equals("cd")) {
      Object result = command.execute(currentDirectory, args);
      if (result instanceof Directory) {
        currentDirectory = (Directory) result;
        return ((Cd) command).getResult();
      }
      return (String) result;
    } else if (commandName.equals("ls")) {
      if (args.startsWith("--ord=")) {
        return (String) command.execute(currentDirectory, args.substring(6));
      }
      return (String) command.execute(currentDirectory, args);
    } else if (commandName.equals("rm")) {
      return (String) command.execute(currentDirectory, args);
    } else {
      return (String) command.execute(currentDirectory, args);
    }
  }
}
