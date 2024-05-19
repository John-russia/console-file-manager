package ru.ivanov.file.manager.commands;

import ru.ivanov.file.manager.inouts.CommandIn;
import ru.ivanov.file.manager.inouts.CommandOut;

import java.io.File;
import java.util.Arrays;

public class lsCommand implements Commands{
    @Override
    public String getName() {
        return "ls";
    }

    @Override
    public String getDescription() {
        return String.format("%6s - Возвращает список файлов в директории", this.getName());
    }

    @Override
    public CommandOut execute(CommandIn request) {
        File file = new File(request.currentPath());
        return new CommandOut(request.currentPath(),
        Arrays.stream(file.listFiles()).map(File::getName).toList());
    }
}
