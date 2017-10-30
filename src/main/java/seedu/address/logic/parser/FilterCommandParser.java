package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameAndTagsContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String name;
        List<String> tags;

        String regex = "\\s+";
        String[] nameKeywords;

        List<String> nameKeywordsList;
        List<String> tagsKeywordsList;

        // Extracting name
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = argMultimap.getValue(PREFIX_NAME).get();
            // name cannot be empty
            if (name.length() == 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            }
            nameKeywords = name.split(regex);
            nameKeywordsList = Arrays.asList(nameKeywords);
        } else {
            nameKeywordsList = new ArrayList<>(); // empty list
        }

        if (argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            tags = new ArrayList<>();
        } else {
            tags = argMultimap.getAllValues(PREFIX_TAG);
        }

        // Throws an error if both name and tags are empty.
        if (tags.isEmpty() && nameKeywordsList.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (!tags.isEmpty()) {
            tagsKeywordsList = Arrays.asList(getKeywordsFromTags(tags, regex));
        } else {
            tagsKeywordsList = new ArrayList<>(); // empty list
        }

        return new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywordsList, tagsKeywordsList));
    }

    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    private String[] getKeywordsFromTags(List<String> tagList, String regex) throws ParseException {
        String tagKeywords = "";
        for (String tag : tagList) {
            // tags cannot be empty
            if (tag.length() == 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            }
            tagKeywords = tagKeywords + " " + tag;
        }
        return tagKeywords.trim().split(regex);
    }
}
