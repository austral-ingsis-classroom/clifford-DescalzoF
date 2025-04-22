package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.command.Cd;
import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.command.Ls;
import edu.austral.ingsis.clifford.command.Mkdir;
import edu.austral.ingsis.clifford.command.Pwd;
import edu.austral.ingsis.clifford.command.Rm;
import edu.austral.ingsis.clifford.command.Touch;
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
      String result = command.execute(currentDirectory, args);
      currentDirectory = ((Cd) command).getNewDirectory();
      return result;
    } else if (commandName.equals("ls")) {
      if (args.startsWith("--ord=")) {
        return command.execute(currentDirectory, args.substring(6));
      }
      return command.execute(currentDirectory, args);
    } else {
      return command.execute(currentDirectory, args);
    }
  }
}