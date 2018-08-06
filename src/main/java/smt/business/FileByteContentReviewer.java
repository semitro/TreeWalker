package smt.business;

import java.io.*;

/**
 * This class determines if file consist substring or not
 * string is considering as a sequence of bytes.
 * File reads by peaces of 4KB.
 * It uses kind of double buffering to except
 * situation where we read the same bytes from file system twice
 *
 **/
public class FileByteContentReviewer implements FileContentReviewer{
    private static final int INPUT_BUFFER_SIZE = 4096;
    private byte[] input_buffer = new byte[INPUT_BUFFER_SIZE];
    private byte[] extra_buffer;
    private byte[] current_buffer; // used as a pointer to change buffers fast

    public boolean contains(File file, String text) throws IOException {
        return contains(file, text.getBytes());
    }

    public boolean contains(File file, byte[] bytes) throws IOException {
        if(bytes.length == 0) return true;
        try(FileInputStream fis = new FileInputStream(file)) {
            return containsNonClosing(fis, bytes);
        }
    }

    /**
     * Doesn't close the stream
     ***/
    private boolean containsNonClosing(InputStream fis, byte[] bytes) throws IOException {
        // We will use slow hdd to often
        // if input buffer is less than the text we're looking for
        if (bytes.length > input_buffer.length) input_buffer = new byte[bytes.length];
        extra_buffer = new byte[input_buffer.length];
        current_buffer = input_buffer;
        boolean another_buffer_filled = false; // used to not read bytes from os twice

        while (true) {
            int was_read = fis.read(current_buffer, 0, current_buffer.length);
            if (was_read == -1) return false;

            // there could be a situation then we need to compare
            // the text with end of one buffer and the beginning of another one
            boolean haveCrossedFirstBuffer = false;
            for (int i = 0; i < current_buffer.length; i++) {
                int file_i = i;

                for (int k = 0; k < bytes.length; k++) {
                    if (file_i == was_read) return false;
                    if (current_buffer[file_i] != bytes[k]) { // if it doesn't match
                        if(haveCrossedFirstBuffer) {
                            // we've already switched to another buffer,
                            // so if it was last symbol, forget old bytes
                            if(file_i == current_buffer.length - 1)
                                another_buffer_filled = false;
                            else
                                swapBuffers(); // swap to initial buffer
                        }
                        break;
                    }
                    // if the loop is over and there weren't unmatches
                    if(k == bytes.length - 1)
                        return true;
                    file_i++;
                    // in the case like:
                    // input_buffer: [ 0 b b ] [ b e 0 ]
                    // text:             [ b b e]
                    // if it's equals characters in the sequence and the buffer
                    // and we've just crossed the border of the current buffer
                    // then we need to use an extra one
                    if (file_i == current_buffer.length) {
                        haveCrossedFirstBuffer = true;
                        swapBuffers();
                        if (!another_buffer_filled) {
                            was_read = fis.read(current_buffer, 0, current_buffer.length);
                            another_buffer_filled = true;
                            if (was_read == -1) return false;
                        }
                        file_i = 0;
                    }
                }
            }
        }
    }

    private void swapBuffers(){
        current_buffer = getAnotherBuffer(current_buffer);
    }

    private byte[] getAnotherBuffer(byte[] buffer){
        if(buffer == input_buffer) return extra_buffer;
        else return input_buffer;
    }
}
