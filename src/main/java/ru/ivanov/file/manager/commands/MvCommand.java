package ru.ivanov.file.manager.commands;

import ru.ivanov.file.manager.inouts.CommandIn;
import ru.ivanov.file.manager.inouts.CommandOut;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MvCommand implements Commands {

    @Override
    public String getName() {
        return "mv";
    }

    @Override
    public String getDescription() {
        return String.format("%-6s - переносит/переименовывает файл или директорию по относительному и абсолютному пути", this.getName());
    }

    @Override
    public CommandOut execute(CommandIn request) throws IOException {
        File file_to_move = new File(resultPath(request.currentPath(), request.command()[1]));
        File target_file = new File(resultPath(request.currentPath(), request.command()[2]));
        if (file_to_move.exists()) {
            move(file_to_move, target_file);
            return new CommandOut(request.currentPath(), "Completed");
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

    private void move(File file_to_move, File target_file) throws IOException {
        Path fileToMovePath = Paths.get(file_to_move.toURI());
        Path targetPath = Paths.get(target_file.toURI());
        Files.move(fileToMovePath, targetPath);
    }

}
