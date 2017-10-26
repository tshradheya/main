package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a Popular Contact
 */
public class PopularContactCard extends UiPart<Region> {

    private static final String FXML = "PopularContactCard.fxml";
    private static final Integer IMAGE_WIDTH = 70;
    private static final Integer IMAGE_HEIGHT = 70;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;

    @FXML
    private VBox popularContactPane;
    @FXML
    private Label popularContactName;
    @FXML
    private ImageView popularContactDisplayPicture;
    @FXML
    private Label rank;

    public PopularContactCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        rank.setText("#" + displayedIndex);
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        popularContactName.textProperty().bind(Bindings.convert(person.nameProperty()));
        assignImage(person);
    }

    /**
     * Assigns URL to the image depending on the path
     */
    private void assignImage(ReadOnlyPerson person) {

        if (!person.getDisplayPicture().getPath().equals("")) {

            Image image = new Image("file:" + "pictures/" + person.getDisplayPicture().getPath() + ".png",
                    IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

            centerImage();
            popularContactDisplayPicture.setImage(image);

        }
    }

    /**
     * Centre the image in ImageView
     */
    public void centerImage() {
        Image image = popularContactDisplayPicture.getImage();
        if (image != null) {
            double width;
            double height;

            double ratioX = popularContactDisplayPicture.getFitWidth() / image.getWidth();
            double ratioY = popularContactDisplayPicture.getFitHeight() / image.getHeight();

            double reducCoeff;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            width = image.getWidth() * reducCoeff;
            height = image.getHeight() * reducCoeff;

            popularContactDisplayPicture.setX((popularContactDisplayPicture.getFitWidth() - width) / 2);
            popularContactDisplayPicture.setY((popularContactDisplayPicture.getFitHeight() - height) / 2);

        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PopularContactCard card = (PopularContactCard) other;
        return rank.getText().equals(card.rank.getText())
                && person.equals(card.person);
    }
}
