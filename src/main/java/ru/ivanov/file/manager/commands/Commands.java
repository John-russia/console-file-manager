package ru.ivanov.march.chat.commands;

import java.util.List;

public interface Commands {
    void execute (List<String> parameters, String currentLocation);
}
