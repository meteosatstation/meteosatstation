package org.meteosatstation.format.hrit.Headers;

public class PrimaryHeader {
    /**
     * The header type ID
     */
    public byte HEADER_TYPE_ID = 0;

    /**
     * The length of this record (always 16)
     */
    public short headerLength;

    /**
     * The possible file type ids
     */
    public enum FileType{
        IMAGE_DATA_FILE(0), GTS_MESSAGE(1), ALPHANUMERIC_TEXT_FILE(2), ENCRYPTION_KEY_FILE(3),
        REPEAT_CYCLE_PROLOGE(128), REPEAT_CYCLE_EPILOGUE(129), DCP_MESSAGE(130), BINARY_FILE(144);

        private int code;

        FileType(int c) {
            code = c;
        }

        public int getCode() {
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
