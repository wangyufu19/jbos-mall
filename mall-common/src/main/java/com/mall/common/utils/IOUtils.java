package com.mall.common.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * IOUtils
 *
 * @author youfu.wang
 * @date 2023/9/7 9:25
 */
public class IOUtils {

    public static void write(OutputStream out, InputStream in) {
        byte[] buff = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(in);
        int len = 0;
        try {
            while ((len = bis.read(buff)) != -1) {
                out.write(buff, 0, len);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(bis);
        }
    }

    public static void close(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
