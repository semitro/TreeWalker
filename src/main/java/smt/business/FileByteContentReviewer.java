package smt.business;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class FileByteContentReviewer implements FileContentReviewer{
    private static final int INPUT_BUFFER_SIZE = 2048;
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
        try(FileInputStream fis = new FileInputStream(file)) {
            return containsNonClosing(fis, bytes);
        }
    }

    private boolean containsNonClosing(FileInputStream fis, byte[] bytes) throws IOException {
        // Чтобы исключить ситуацию, когда все байты из буфера файла
        // уже совпали с байтами текста, а тот ещё не кончился. В таком случае пришлось бы
        // загружать следующую порцию, но если бы после этого совпадение не произошло,
        // следовало бы вернуться к предыдущй порции, которая уже потеряна
        if (bytes.length > input_buffer.length) input_buffer = new byte[bytes.length];
        extra_buffer = new byte[input_buffer.length];
        current_buffer = input_buffer;
        boolean another_buffer_filled = false;
        boolean have_swapped = false;

        while (true) {
            int was_read = fis.read(current_buffer, 0, input_buffer.length);
            if (was_read == -1) return false;

            for (int i = 0; i < current_buffer.length; i++) {
                int file_i = i;
                boolean match = true;
                for (int k = 0; k < bytes.length; k++) {
                    if (input_buffer[file_i] != bytes[k] || file_i == was_read) {
                        match = false;
                        if (have_swapped) swapBuffers(); // if
                        break;
                    }
                    file_i++;
                    // in the case like:
                    // input_buffer: [ 0 b b ] [ b e 0 ]
                    // text:             [ b b e]
                    // if it's equals characters in the sequence and the buffer
                    // and we've just crossed the border of the current buffer
                    // then we need to use an extra one
                    if (file_i == current_buffer.length) {
                        swapBuffers();
                        if (!another_buffer_filled) {
                            was_read = fis.read(current_buffer, 0, current_buffer.length);
                            another_buffer_filled = true;
                            if (was_read == -1) return false;
                        }
                        file_i = 0;
                    }
                }
                if (match) return true;
            }
        }
    }
}
