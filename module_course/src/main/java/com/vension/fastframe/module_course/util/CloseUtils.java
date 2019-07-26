package com.vension.fastframe.module_course.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/25 14:39
 * 更 新：2019/7/25 14:39
 * 描 述：关闭IO流
 * ========================================================
 */

public final class CloseUtils {

    public CloseUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void closeIO(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
