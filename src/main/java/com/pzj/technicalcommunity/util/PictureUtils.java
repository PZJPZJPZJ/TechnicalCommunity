package com.pzj.technicalcommunity.util;

import java.util.UUID;

public class PictureUtils {
    public static String getFileExtension(String filename) {
        if (filename != null && filename.length() > 0 && filename.lastIndexOf(".") != -1) {
            return filename.substring(filename.lastIndexOf("."));
        } else {
            return "";
        }
    }
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
