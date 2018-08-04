package smt.business;

import java.io.*;

public class FileByteContentReviewer implements FileContentReviewer{
    private static final int INPUT_BUFFER_SIZE = 4096;
    private byte[] input_buffer = new byte[INPUT_BUFFER_SIZE];
    private byte[] extra_buffer;
    private byte[] current_buffer; // used as a pointer
    public boolean contains(File file, String text) throws IOException {
        return contains(file, text.getBytes());
    }

    private byte[] getAnotherBuffer(byte[] buffer){
        if(buffer == input_buffer) return extra_buffer;
        else return input_buffer;
    }
    private void swapBuffers(){
        current_buffer = getAnotherBuffer(current_buffer);
    }

    public boolean contains(File file, byte[] bytes) throws IOException {
        if(bytes.length == 0) return true;
        try(FileInputStream fis = new FileInputStream(file)) {
            return containsNonClosing(fis, bytes);
        }
    }

    private boolean containsNonClosing(InputStream fis, byte[] bytes) throws IOException {
        if (bytes.length > input_buffer.length) input_buffer = new byte[bytes.length];
        extra_buffer = new byte[input_buffer.length];
        current_buffer = input_buffer;
        boolean another_buffer_filled = false;

        while (true) {
            int was_read = fis.read(current_buffer, 0, current_buffer.length);
            if (was_read == -1) return false;

            boolean haveCrossedFirstBuffer = false;
            for (int i = 0; i < current_buffer.length; i++) {
                int file_i = i;

                for (int k = 0; k < bytes.length; k++) {
                    if (file_i == was_read) return false;
                    if (current_buffer[file_i] != bytes[k]) {
                        if(haveCrossedFirstBuffer){
                            // we've already switched to another buffer,
                            // so if it was last symbol, forget old bytes
                            if(file_i == current_buffer.length - 1)
                                another_buffer_filled = false;
                            else
                                swapBuffers(); // swap to initial buffer
                        }
                        break;
                    }
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
}
