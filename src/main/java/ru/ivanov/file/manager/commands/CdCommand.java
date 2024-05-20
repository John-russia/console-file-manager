package ru.ivanov.file.manager.commands;

import ru.ivanov.file.manager.inouts.CommandIn;
import ru.ivanov.file.manager.inouts.CommandOut;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CdCommand implements Commands {
    @Override
    public String getName() {
        return "cd";
    }

    @Override
    public String getDescription() {
        return String.format("%-6s - Переходит в папку по относительному или абсолютному пути", this.getName());
    }

    @Override
    public CommandOut execute(CommandIn request) throws IOException {
        File folder = new File(resultPath(request.currentPath(), request.command()[1]));
        if (folder.exists()) {
            return new CommandOut(folder.getCanonicalPath(), null);
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

}
