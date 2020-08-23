package com.home.pratice.bootstrap.common.util;

import com.home.pratice.bootstrap.common.constant.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class FileConverter {
    private static int KB = 1024;
    private static String imageTag = "data:image/jpg;base64,";
    private static byte[] imageBytes = imageTag.getBytes();
    private static List<String> imageFileExtension = new ArrayList<>(Arrays.asList("jpg", "jpeg", "png", "gif", "tif", "tiff"));
    private static List<String> otherFileExtension = new ArrayList<>(Arrays.asList("pdf", "txt"));

    /**
     * if中的not會使效率變差 但能讓程式不成為死亡金字塔 兩種取捨視情況而定
     *
     * @param args
     */
    public static void main(String[] args) {
        File[] files = new File(Const.IMAGE_PATH).listFiles();
        if (files == null) return;
        for (File file : files) {
            if (!file.isFile()) continue;
            String fileExtension = FilenameUtils.getExtension(file.getPath());
            boolean isImage = checkFileExtension(fileExtension);
            if (!isImage) continue;
            //file -> base64 byte array -> insert into db -> base64 byte array -> (imageTag +) base64 string -> output
            byte[] bytes = bigFileToBytes(file);
            //file -> (imageTag byte +) base64 byte array -> insert into db -> (imageTag byte +) base64 byte array -> base64 string -> output
            byte[] base64Byte = fileToBase64Bytes(file, fileExtension);
            //TODO:into service
            log.info("[methodName] {}", "into service");
        }
    }

    private static boolean checkFileExtension(String fileExtension) {
        return imageFileExtension.contains(fileExtension) || otherFileExtension.contains(fileExtension);
    }

    public static byte[] fileToBase64Bytes(File file, String fileExtension) {
        byte[] allBytes;
        if (otherFileExtension.contains(fileExtension)) {
            allBytes = bytesToBase64Bytes(bigFileToBytes(file));
        } else {
            byte[] base64Bytes = bytesToBase64Bytes(bigFileToBytes(file));
            allBytes = arrayMerge(imageBytes, base64Bytes);
        }
        log.info(new String(allBytes));
        return allBytes;
    }

    public static byte[] bigFileToBytes(File file) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] buf = new byte[KB];
            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum);
            }
        } catch (IOException e) {
            //TODO:insert log table
            log.error("[bigDocToBytes] {}", "轉換失敗");
        }
        return bos.toByteArray();
    }

    public static String bigFileToBase64String(File file) {
        StringBuilder sb = new StringBuilder();
        int bufferSize = 3 * KB; //3 mb is the size of a chunk
        byte[] bytes = new byte[bufferSize];
        int readSize = 0;
        try {
            FileInputStream fis = new FileInputStream(file);
            while ((readSize = fis.read(bytes)) == bufferSize) {
                sb.append(Base64.encodeBase64String(bytes));
            }
        } catch (IOException e) {
            //TODO:insert log table
            log.error("[bigDocToBytes] {}", "轉換失敗");
        }

        if (readSize > 0) {
            bytes = Arrays.copyOf(bytes, readSize);
            sb.append(Base64.encodeBase64String(bytes));
        }

        return sb.toString();
    }

    public void buildBigFile(File file, OutputStream base64OutputStream) {
        try {
            InputStream is = new FileInputStream(file);
            OutputStream out = new Base64OutputStream(base64OutputStream);
            IOUtils.copy(is, out);
            is.close();
            out.close();
        } catch (IOException e) {
            //TODO:insert log table
            log.error("[bigDocToBytes] {} is due to {}", "檔案產生失敗", ExceptionUtils.getStackTrace(e));
        }
    }

    public static byte[] arrayMerge(byte[] first, byte[] second) {
        byte[] newArray = new byte[first.length + second.length];
        System.arraycopy(first, 0, newArray, 0, first.length);
        System.arraycopy(second, 0, newArray, first.length, second.length);
        return newArray;
    }

    public static <T> T[] arrayMerge(Class<T[]> clazz, T[] first, T[] second) {
        T[] newArray = clazz.cast(Array.newInstance(clazz.getComponentType(), first.length + second.length));
        System.arraycopy(first, 0, newArray, 0, first.length);
        System.arraycopy(second, 0, newArray, first.length, second.length);
        return newArray;
    }

    //以下代碼直接使用 不需要使用函式
    private static byte[] bytesToBase64Bytes(byte[] bytes) {
        return Base64.encodeBase64(bytes);
    }

    private static String base64BytesToString(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }
}
