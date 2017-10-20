package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANGE;
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

    public static final String MESSAGE_NOT_IMPLEMENTED = "Export Command not implemented yet";

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED);
    }
}
