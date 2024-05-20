package ru.ivanov.file.manager.commands;

import ru.ivanov.file.manager.inouts.CommandIn;
import ru.ivanov.file.manager.inouts.CommandOut;

import java.io.File;
import java.io.IOException;

public class MkDirCommand implements Commands {
    @Override
    public String getName() {
        return "mkdir";
    }

    @Override
    public String getDescription() {
        return String.format("%-6s - создаёт директорию по относительному или абсолютному пути, включая промежуточные папки", this.getName());
    }

    @Override
    public CommandOut execute(CommandIn request) throws IOException {
        File folder = new File(resultPath(request.currentPath(), request.command()[1]));
        if (folder.exists()) {
            return new CommandOut(request.currentPath(), "Directory already exists");
        } else {
            folder.mkdirs();
            return new CommandOut(request.currentPath(), String.format("Directory %s created", folder.getCanonicalPath()));
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
