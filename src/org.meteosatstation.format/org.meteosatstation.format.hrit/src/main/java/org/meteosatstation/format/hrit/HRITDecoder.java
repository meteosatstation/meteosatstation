package org.meteosatstation.format.hrit;

import org.meteosatstation.format.formatinterface.FormatInterface;
import org.meteosatstation.format.hrit.Headers.PrimaryHeader;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.log.LogService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

public class HRITDecoder implements FormatInterface {
    private LogService logService;
    public PrimaryHeader primaryHeader;
    private FileInputStream fileInputStream;

    public HRITDecoder(LogService logService) {
        this.logService = logService;
    }

    public static boolean canDecodeFile(File file) {
       return true;
    }

    public void decodeFile(File file) {
        FileInputStream fileInputStream;

        try {
            this.fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logService.log(LogService.LOG_ERROR, "Unable to load file: " + e.getMessage());
        }

        this.readPrimaryHeader();
    }

    private void readPrimaryHeader() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byte[] bytes = new byte[2];

        try {
            fileInputStream.read(bytes);
        } catch (IOException e) {
            logService.log(LogService.LOG_ERROR, "Unable to read data: " + e.getMessage());
        }

        byteBuffer.put(bytes);

        this.primaryHeader.headerLength = byteBuffer.getShort();

        logService.log(LogService.LOG_DEBUG, String.format("Primary header length is %u bytes", bytes));
    }
}
