package seedu.address.logic.parser;

/**
 * A value used to specify the theme of the address book.
 */
public class Theme {
    private final String theme;
    private final String CSS;

    public Theme(String theme, String CSS) {
        this.theme = theme;
        this.CSS = CSS;
    }

    public String getTheme() {
        return theme;
    }

    public String getCSS() {
        return CSS;
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
        return otherTheme.getTheme().equals(getTheme()) && otherTheme.getCSS().equals(getCSS());
    }
}
