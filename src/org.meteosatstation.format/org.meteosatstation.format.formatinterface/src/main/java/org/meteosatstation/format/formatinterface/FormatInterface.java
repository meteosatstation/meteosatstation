package org.meteosatstation.format.formatinterface;

public interface FormatInterface {
    /**
     * Checks if this module can decode this file
     * @param file The file to be checked
     * @return boolean
     */
    public boolean canDecodeFile(File file);
}