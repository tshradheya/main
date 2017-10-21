package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;

/**
 * To create a display picture resource folder
 */
public class AddressBookPictureStorage {

    private String filePath;

    public AddressBookPictureStorage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the file path of the pictures folder.
     */
    public String getAddressBookPicturePath() {
        return filePath;
    }

    /**
     * Creates a new folder for pictures storage
     */
    public void createPictureStorageFolder() throws IOException {
        requireNonNull(filePath);

        File file  = new File(filePath);
        FileUtil.createIfMissing(file);

    }
}
