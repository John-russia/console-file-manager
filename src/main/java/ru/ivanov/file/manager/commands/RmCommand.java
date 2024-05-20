package ru.ivanov.file.manager.commands;

import ru.ivanov.file.manager.inouts.CommandIn;
import ru.ivanov.file.manager.inouts.CommandOut;

import java.io.File;
import java.io.IOException;

public class RmCommand implements Commands {

    @Override
    public String getName() {
        return "rm";
    }

    @Override
    public String getDescription() {
        return String.format("%-6s - удаляет файл или директорию со всем содержимым по относительному и абсолютному пути", this.getName());
    }

    @Override
    public CommandOut execute(CommandIn request) throws IOException {
        File folder = new File(resultPath(request.currentPath(), request.command()[1]));
        if (folder.exists()) {
            rekursDelDirectory(folder);
            return new CommandOut(request.currentPath(), "Deleted");
        } else {
            return new CommandOut(request.currentPath(), "File or directory doesn't exists");
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

    private void rekursDelDirectory(File directory) {
        File[] contents = directory.listFiles();
        if (contents != null) {
            for (File file : contents) {
                rekursDelDirectory(file);
            }
        }
        directory.delete();
    }

}
