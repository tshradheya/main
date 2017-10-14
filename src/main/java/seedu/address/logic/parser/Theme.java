package seedu.address.logic.parser;

/**
 * A value used to specify the theme of the address book.
 */
public class Theme {
    private final String theme;

    public Theme(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public String toString() {
        return getTheme();
    }

    @Override
    public int hashCode() {
        return theme == null ? 0 : theme.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Theme)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Theme otherTheme = (Theme) obj;
        return otherTheme.getTheme().equals(getTheme());
    }
}
