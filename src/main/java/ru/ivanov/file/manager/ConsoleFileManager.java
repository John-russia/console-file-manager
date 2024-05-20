package ru.ivanov.file.manager;

import ru.ivanov.file.manager.commands.Commands;
import ru.ivanov.file.manager.inouts.CommandIn;
import ru.ivanov.file.manager.inouts.CommandOut;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConsoleFileManager {

    private Scanner scanner;
    public String currentPath;
    public String currentUserName;
    public String currentPcName;
    public List<Commands> commandsList;
    public Map<String, Commands> commandsMap;

    public ConsoleFileManager(List<Commands> commandsList) throws IOException {
        this.currentPath = new File(".").getCanonicalPath();
        this.commandsList = commandsList;
        this.commandsMap = commandsList.stream().collect(Collectors.toMap(Commands::getName, Function.identity()));
        this.currentUserName = System.getProperty("user.name");
        this.currentPcName = InetAddress.getLocalHost().getHostName();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        String[] command = getCommand();
        while (!command[0].equals("exit")) {
            if (command[0].equals("help")) {
                String help = commandsList.stream().map(Commands::getDescription).collect(Collectors.joining("\n"));
                System.out.println(help);
            } else if (!checkCommand(command[0])) {
                System.out.println(String.format("Command %s not found, type help for help", command[0]));
                command = getCommand();
                continue;
            } else {
                CommandOut commandOut = null;
                try {
                    commandOut = commandsMap.get(command[0]).execute(new CommandIn(currentPath, command));
                } catch (IOException e) {
                    e.getMessage();
                }
                if (commandOut.result() != null) {
                    System.out.println(commandOut.result());
                }
                currentPath = commandOut.resultPath();
            }
            command = getCommand();

        }
        System.out.println("Finished");
    }

    private String[] getCommand() {
        System.out.print(currentUserName + "@" + currentPcName + ": " + lastFolderInCurrentPath() + "$ ");
        return scanner.nextLine().split(" ");
    }

    private String lastFolderInCurrentPath() {
        String[] path = currentPath.split("/");
        if (path.length == 0) {
            return "/";
        } else {
            return path[path.length - 1];
        }
    }

    private boolean checkCommand(String inCommand) {
        boolean result = false;
        for (Commands command : commandsList) {
            if (inCommand.equals(command.getName())) {
                result = true;
            }
        }
        return result;
    }

}
