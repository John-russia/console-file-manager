package ru.ivanov.file.manager.commands;

import ru.ivanov.file.manager.inouts.CommandIn;
import ru.ivanov.file.manager.inouts.CommandOut;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LsCommand implements Commands {
    @Override
    public String getName() {
        return "ls";
    }

    @Override
    public String getDescription() {
        return String.format("%-6s - Возвращает список файлов в директории", this.getName());
    }

    @Override
    public CommandOut execute(CommandIn request) {
        File folder = new File(request.currentPath());
        List<String> filelist = Arrays.stream(folder.listFiles()).map(File::getName).toList();
        String result;
        if (request.command().length == 1) {
            result = withoutHiddenFiles(filelist);
        } else if (request.command()[1].equals("-a")) {
            result = withHiddenFiles(filelist);
        } else if (request.command()[1].equals("-l")) {
            result = fullFileInfoWithoutHidden(folder);
        } else if (request.command()[1].equals("-la") || request.command()[1].equals("-al")) {
            result = fullFileInfoWithHidden(folder);
        } else if (request.command()[1].equals("-help")) {
            result = helpComand();
        } else {
            result = "Некорректный аргумент, введите ls -help для помощи";
        }
        return new CommandOut(request.currentPath(), result);
    }

    private static String helpComand() {
        return
                """
                        Добро пожаловать в помощник команды ls.
                        Команда отображает список файлов в текущей директории.
                        Возможные аргументы:
                        -help   - вызов информации о команде
                        -l      - полная информация о файлах
                        -a      - показать скрытые файлы
                        -la(al) - полная информация, включая скрытые фалы
                        """.stripIndent();
    }


    public String withHiddenFiles(List<String> filelist) {
        String result = "";
        for (String fileName : filelist) {
            result += fileName + "   ";
        }
        return result;
    }

    public String withoutHiddenFiles(List<String> filelist) {
        String result = "";
        for (String fileName : filelist) {
            if (!fileName.startsWith(".")) {
                result += fileName + "   ";
            }
        }
        return result;
    }

    public String fullFileInfoWithHidden(File folder) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%-12s %-8s %-12s", "NAME", "SIZE", "LAST_MODIFIED")).append("\n");
        for (File file : Arrays.stream(folder.listFiles()).toList()) {
            Date date = new Date(file.lastModified());
            stringBuilder.append(String.format("%-12s %-8s %-12s", file.getName(), file.length(), date)).append("\n");
        }
        return stringBuilder.toString();
    }

    public String fullFileInfoWithoutHidden(File folder) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%-12s %-8s %-12s", "NAME", "SIZE", "LAST_MODIFIED")).append("\n");
        for (File file : Arrays.stream(folder.listFiles()).toList()) {
            if (!file.getName().startsWith(".")) {
                Date date = new Date(file.lastModified());
                stringBuilder.append(String.format("%-12s %-8s %-12s", file.getName(), file.length(), date)).append("\n");
            }
        }
        return stringBuilder.toString();
    }


}
