package seedu.address.model.person;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;


/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tags} matches the keyword given.
 */

public class PersonContainsTagPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;

    public PersonContainsTagPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<Tag> tags = person.getTags();
        Tag keywordInTag = null;

        try {
            keywordInTag= new Tag(keyword);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        return tags.contains(keywordInTag);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsTagPredicate // instanceof handles nulls
                && this.keyword.equals(((PersonContainsTagPredicate) other).keyword)); // state check
    }

}
