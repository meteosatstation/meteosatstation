package org.meteosatstation.filemanager;

public class Config {
    /**
     * The directory that will be scanned
     */
    public String file_dir;

    /**
     * Should we delete files once they are read
     */
    public boolean deleteFilesAfterImport;

    /**
     * Should we delete files if we cannot read them
     */
    public boolean deleteFilesIfCannotRead;

    /**
     * Returns true if all the required configuration has been set
     * @return boolean
     */
    public boolean isConfigured() {
        if (file_dir == null) {
            return false;
        } else {
            return true;
        }
    }
}
