//@@author chuaweiwen
package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameAndTagsContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> nameKeywords;
    private final List<String> tagKeywords;

    public NameAndTagsContainsKeywordsPredicate(List<String> nameKeywords, List<String> tagKeywords) {
        this.nameKeywords = nameKeywords;
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean tagFound = false;

        int numTagKeywords = tagKeywords.size();
        int tagsMatchedCount = 0;
        if (!tagKeywords.isEmpty()) {
            tagsMatchedCount = countTagMatches(person);
        }

        if (tagsMatchedCount == numTagKeywords) {
            tagFound = true;
        }

        boolean nameFound = false;
        if (!nameKeywords.isEmpty()) {
            nameFound = nameKeywords.stream().allMatch(nameKeywords -> StringUtil
                    .containsWordIgnoreCase(person.getName().fullName, nameKeywords));
        }

        if (nameKeywords.isEmpty() && tagKeywords.isEmpty()) {
            throw new AssertionError("Either name or tag must be non-empty");
        } else if (nameKeywords.isEmpty()) {
            return tagFound;
        } else if (tagKeywords.isEmpty()) {
            return nameFound;
        }

        return nameFound && tagFound;
    }

    /**
     * Counts the number of matching tags of a person and returns the count
     */
    public int countTagMatches(ReadOnlyPerson person) {
        int tagsMatchedCount = 0;

        for (String keywords : tagKeywords) {
            if (hasTag(keywords, person)) {
                tagsMatchedCount++;
            }
        }
        return tagsMatchedCount;
    }

    /**
     * Returns true if the person's tag can be found in the keywords. Otherwise returns false.
     */
    public boolean hasTag(String keywords, ReadOnlyPerson person) {
        Set<Tag> tagsOfPerson = person.getTags();
        for (Tag tag : tagsOfPerson) {
            if (tag.tagName.equalsIgnoreCase(keywords)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameAndTagsContainsKeywordsPredicate // instanceof handles nulls
                && this.nameKeywords.equals(((NameAndTagsContainsKeywordsPredicate) other)
                .nameKeywords)
                && this.tagKeywords.equals(((NameAndTagsContainsKeywordsPredicate) other)
                .tagKeywords)); // state check
    }

}
//@@author
