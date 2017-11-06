//@@author tshradheya
package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;

/**
 * To create a display picture resource folder
 */
public class AddressBookPictureStorage {

    private static final Logger logger = LogsCenter.getLogger(AddressBookPictureStorage.class);

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

        if (filePath == null) {
            assert false : "Wrong execution as path is given by program and is fixed";
        }

        logger.info("Picture folder "  + filePath + " created if missing");
        File file  = new File(filePath);
        FileUtil.createIfMissing(file);

    }
}
