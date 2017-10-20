package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANGE;
import java.nio.file.Path;
import seedu.address.logic.commands.exceptions.CommandException;

public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export selected person/s\n"
            + "by the index number or range in the last person listing\n"
            + "Parameters: " + PREFIX_RANGE + "[RANGE] "
            + PREFIX_PATH + "[PATH]\n"
            + "Example 1: " + COMMAND_WORD + " " + PREFIX_RANGE + "all " + PREFIX_PATH + "C:\\Users\\Default\\Desktop\n"
            + "Example 2: " + COMMAND_WORD + " " + PREFIX_RANGE + "1 " + PREFIX_PATH + "C:\\Users\\Default\\Desktop\n"
            + "Example 3: " + COMMAND_WORD + " " + PREFIX_RANGE + "1,2 " + PREFIX_PATH + "C:\\Users\\Default\\Desktop\n"
            + "Example 4: " + COMMAND_WORD + " " + PREFIX_RANGE + "1-5 " + PREFIX_PATH + "C:\\Users\\Default\\Desktop";

    public static final String MESSAGE_ARGUMENTS = "Range: %1$s, Path: %2$s";

    private final String range;
    private final String path;

    public ExportCommand(String range, String path) {
        requireNonNull(range);
        requireNonNull(path);

        this.range = range;
        this.path = path;
    }

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, range, path));
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }

        if(!(other instanceof ExportCommand)) {
            return false;
        }

        ExportCommand e = (ExportCommand) other;
        return range.equals(e.range) && path.equals(e.path);
    }
}
