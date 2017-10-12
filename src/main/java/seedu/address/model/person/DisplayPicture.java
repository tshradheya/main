package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represent's a person's display picture in the address book
 * Guarantees : immutable; is always valid
 */
public class DisplayPicture {


    public final String path;

    public DisplayPicture(String path) {
        requireNonNull(path);
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayPicture // instanceof handles nulls
                && this.path.equals(((DisplayPicture) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

}
