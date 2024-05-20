package ru.ivanov.file.manager.commands;

import ru.ivanov.file.manager.inouts.CommandIn;
import ru.ivanov.file.manager.inouts.CommandOut;

import java.io.File;
import java.io.IOException;

public class PwdCommand implements Commands {
    @Override
    public String getName() {
        return "pwd";
    }

    @Override
    public String getDescription() {
        return String.format("%-6s - Отображает абсолютный путь текущей директории", this.getName());
    }

    @Override
    public CommandOut execute(CommandIn request) throws IOException {
        return new CommandOut(request.currentPath(), request.currentPath());
    }

}
