package org.meteosatstation.format.hrit.Headers;

public class ImageStructureHeader {
    /**
     * The header type ID
     */
    public static int HEADER_TYPE_ID = 1;

    /**
     * The length of this record (always 9)
     */
    public int headerLength;

    /**
     * Number of bits per pixel
     */
    public int bitsPerPixel;

    /**
     * Number of columns
     */
    public int numberOfColumns;

    /**
     * Number of lines
     */
    public int numberOfLines;

     /**
     * The possible file type ids
     */
    public enum Compression {
        NO_COMPRESSION(0), LOSSLESS_COMPRESSION(1), LOSSY_COMPRESSION(2);

        private int code;

        Compression(int c) {
            code = c;
        }

        public int getCompression() {
            return code;
        }
    }

    /**
     * Total header length for all headers (including this one)
     */
    public int totalHeaderLength;

    /**
     * Total data field length
     */
    public double totalDataLength;

}