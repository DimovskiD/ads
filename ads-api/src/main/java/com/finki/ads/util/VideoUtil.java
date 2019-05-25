package com.finki.ads.util;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.net.URLConnection;

public class VideoUtil {

    public static boolean isVideoFile(MultipartFile file) {
        String mimeType = file.getContentType();
        return mimeType != null && mimeType.startsWith("video");
    }

}
