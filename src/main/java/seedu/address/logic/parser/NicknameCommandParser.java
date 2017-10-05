package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NICKNAME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.NicknameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nickname;

/**
 * Parses input arguments and creates a new NicknameCommand object
 */
public class NicknameCommandParser implements Parser<NicknameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NicknameCommand
     * and returns an NicknameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NicknameCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NICKNAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NicknameCommand.MESSAGE_USAGE));
        }

        Nickname nickname;
        try {
            nickname = new Nickname(argMultimap.getValue(PREFIX_NICKNAME).get());
        } catch (NullPointerException npe) {
            throw new ParseException(npe.getMessage(), npe);
        }

        return new NicknameCommand(index, nickname);
    }
}
