package ru.ivanov.file.manager.commands;

import ru.ivanov.file.manager.inouts.CommandIn;
import ru.ivanov.file.manager.inouts.CommandOut;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class findCommand implements Commands {

    public List<File> fileList;

    public findCommand() {
        this.fileList = new ArrayList<>();
    }

    @Override
    public String getName() {
        return "find";
    }

    @Override
    public String getDescription() {
        return String.format("%-6s - ищет файлы [где ищем] [имя для поиска]", this.getName());
    }

    @Override
    public CommandOut execute(CommandIn request) throws IOException {
        File directory = new File(resultPath(request.currentPath(), request.command()[1]));
        String fileToSearch = request.command()[2];
        if (directory.exists()) {
            find(directory, fileToSearch);
            return new CommandOut(request.currentPath(), resultOfSearch());
        } else {
            return new CommandOut(request.currentPath(), "Directory doesn't exists");
        }
    }

    private String resultOfSearch() {
        StringBuilder stringBuilder = new StringBuilder();
        for (File file : fileList) {
            stringBuilder.append(file.getAbsoluteFile()).append("\n");
        }
        return stringBuilder.toString();
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

    private void find(File directory, String fileToSearch) {
        if (directory.isDirectory()) {
            File[] directoryFiles = directory.listFiles();
            if (directoryFiles != null) {
                for (File file : directoryFiles) {
                    if (file.isDirectory()) {
                        find(file, fileToSearch);
                    } else {
                        if (file.getName().equals(fileToSearch)) {
                            fileList.add(file);
                        }
                    }
                }
            }
        }
    }

}
