package ru.ivanov.file.manager.commands;

import ru.ivanov.file.manager.inouts.CommandIn;
import ru.ivanov.file.manager.inouts.CommandOut;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class InfoCommand implements Commands {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return String.format("%-6s - отображение детальной информации о файле/папке", this.getName());
    }

    @Override
    public CommandOut execute(CommandIn request) throws IOException {
        File file = new File(resultPath(request.currentPath(), request.command()[1]));
        if (file.exists()) {
            return new CommandOut(request.currentPath(), infoBuilder(file));
        } else {
            return new CommandOut(request.currentPath(), "Directory doesn't exists");
        }
    }

    private String resultPath(String startPath, String targetPath) {
        StringBuilder stringBuilder = new StringBuilder();
        if (targetPath.startsWith("/")) {
            stringBuilder.append(targetPath);
        } else {
            stringBuilder.append(startPath).append("/").append(targetPath);
        }
        return stringBuilder.toString();
    }

    public String infoBuilder(File file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%-15s %-5s", "PARAMETER", "VALUE")).append("\n");
        stringBuilder.append(String.format("%-15s %-5s", "Name: ", file.getName())).append("\n");
        stringBuilder.append(String.format("%-15s %-5s", "isExists: ", file.exists())).append("\n");
        stringBuilder.append(String.format("%-15s %-5s", "AbsolutePath: ", file.getAbsolutePath())).append("\n");
        stringBuilder.append(String.format("%-15s %-5s", "CanonicalPath: ", file.getCanonicalPath())).append("\n");
        stringBuilder.append(String.format("%-15s %-5s", "isDirectory: ", file.isDirectory())).append("\n");
        stringBuilder.append(String.format("%-15s %-5s", "isFile: ", file.isFile())).append("\n");
        stringBuilder.append(String.format("%-15s %-5s", "size: ", file.length())).append("\n");

        return stringBuilder.toString();
    }

}
