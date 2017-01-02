package org.meteosatstation.format.hrit.Headers;

public class ImageNavigationHeader {
    /**
     * The header type ID
     */
    public static int HEADER_TYPE_ID = 1;

    /**
     * The length of this record (always 9)
     */
    public int headerLength;

    /**
     * Projection name
     */
    public String projectionName;

    /**
     * Column scaling factor (CFAC)
     */
    public int columnScalingFactor;

    /**
     * Line scaling factor (LFAC)
     */
    public int lineScalingFactor;

    /**
     * Column offset
     */
    public int columnOffset;

    /**
     * Line offset
     */
    public int lineOffset;
}
