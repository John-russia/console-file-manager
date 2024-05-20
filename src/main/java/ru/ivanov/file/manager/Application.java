package ru.ivanov.file.manager;

import ru.ivanov.file.manager.commands.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        List<Commands> commandsList= List.of(
                new LsCommand(),
                new CdCommand(),
                new PwdCommand(),
                new MkDirCommand(),
                new RmCommand(),
                new MvCommand(),
                new CpCommand(),
                new InfoCommand(),
                new findCommand()
        );
        ConsoleFileManager consoleFileManager = new ConsoleFileManager(commandsList);
        consoleFileManager.run();
    }
}