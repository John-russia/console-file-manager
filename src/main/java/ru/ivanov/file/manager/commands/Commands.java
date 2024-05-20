package ru.ivanov.file.manager.commands;

import ru.ivanov.file.manager.inouts.CommandIn;
import ru.ivanov.file.manager.inouts.CommandOut;

import java.io.IOException;
import java.util.List;

public interface Commands {

    String getName();

    String getDescription();

    CommandOut execute(CommandIn request) throws IOException;
}
