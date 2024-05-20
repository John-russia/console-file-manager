package ru.ivanov.file.manager.commands;

import ru.ivanov.file.manager.inouts.CommandIn;
import ru.ivanov.file.manager.inouts.CommandOut;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CpCommand implements Commands {

    @Override
    public String getName() {
        return "cp";
    }

    @Override
    public String getDescription() {
        return String.format("%-6s - копирует файл или директорию по относительному и абсолютному пути", this.getName());
    }

    @Override
    public CommandOut execute(CommandIn request) throws IOException {
        File file_to_move = new File(resultPath(request.currentPath(), request.command()[1]));
        File target_file = new File(resultPath(request.currentPath(), request.command()[2]));
        if (file_to_move.exists()) {
            copy(file_to_move, target_file);
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

    private void copy(File file_to_move, File target_file) throws IOException {
        Path fileToMovePath = Paths.get(file_to_move.toURI());
        Path targetPath = Paths.get(target_file.toURI());
        Files.copy(fileToMovePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

}
