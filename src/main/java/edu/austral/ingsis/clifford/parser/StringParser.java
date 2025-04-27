package edu.austral.ingsis.clifford.parser;

import edu.austral.ingsis.clifford.command.Cd;
import edu.austral.ingsis.clifford.command.Command;
import edu.austral.ingsis.clifford.command.Ls;
import edu.austral.ingsis.clifford.command.Mkdir;
import edu.austral.ingsis.clifford.command.Pwd;
import edu.austral.ingsis.clifford.command.Rm;
import edu.austral.ingsis.clifford.command.Touch;
import edu.austral.ingsis.clifford.element.Directory;

public class StringParser implements Parser<String> {
  @Override
  public Command<Directory> parse(String commandLine) {
    String[] parts = commandLine.split(" ", 2);
    String commandName = parts[0];
    String args = parts.length > 1 ? parts[1] : "";

    switch (commandName) {
      case "ls":
        if (args.startsWith("--ord=")) {
          return new Ls(args.substring(6));
        } else {
          return new Ls();
        }
      case "cd":
        return new Cd(args);
      case "mkdir":
        return new Mkdir(args);
      case "touch":
        return new Touch(args);
      case "pwd":
        return new Pwd();
      case "rm":
        if (args.startsWith("--recursive")) {
          String targetName = args.split(" ", 2).length > 1 ? args.split(" ", 2)[1] : "";
          return new Rm(targetName, true);
        } else {
          return new Rm(args);
        }
      default:
        throw new IllegalArgumentException("Unknown command: " + commandName);
    }
  }
}
