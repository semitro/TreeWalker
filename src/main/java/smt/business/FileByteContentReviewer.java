package smt.business;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class FileByteContentReviewer implements FileContentReviewer{
    private FileInputStream fis;
    private static final int INPUT_BUFFER_SIZE = 2048;
    private byte[] input_buffer = new byte[INPUT_BUFFER_SIZE];
    public boolean contains(File file, String text) throws FileNotFoundException {
        return contains(file, text.getBytes());
    }

    public boolean contains(File file, byte[] bytes) throws FileNotFoundException {
        fis = new FileInputStream(file);
        // Чтобы исключить ситуацию, когда все байты из буфера файла
        // уже совпали с байтами текста, а тот ещё не кончился. В таком случае пришлось бы
        // загружать следующую порцию, но если бы после этого совпадение не произошло,
        // следовало бы вернуться к предыдущй порции, которая уже потеряна
        if(bytes.length > input_buffer.length) input_buffer = new byte[bytes.length];

        try {
            int was_read = fis.read(input_buffer, 0, input_buffer.length);
            if(was_read == -1)
                return false;

            for(int i = 0; i < bytes.length;i++){
                int file_i = i;
                boolean match = true;
                for(int k = 0; k < bytes.length; k++){
                    if(input_buffer[file_i] != bytes[k] || file_i == was_read){
                        match = false;
                        break;
                    }
                    file_i++;
                   // file_i can't be > bytes.length
                }
                if(match) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
