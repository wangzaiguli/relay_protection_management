package com.relay.protection.common.util;

import com.relay.protection.common.error.BizException;
import com.relay.protection.common.error.JsonReturnCode;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author zhuHx
 * @version 1.0
 * @date 2022/4/28 15:20
 */
public class FileUtil {

    public static final String CLASS_PATH;
    public static final String CACHE = "cache";

    static {
        String property = System.getProperty("user.dir");
        CLASS_PATH = property.substring(0, property.lastIndexOf(File.separator) + 1);
    }

    public static String getCachePath(String name) {
        String s = DateUtil.date2Str(LocalDateTime.now(), DateUtil.DATE_YMD);
        return CLASS_PATH + CACHE + File.separator  + s + File.separator + makeFileName(name);
    }

    public static byte[] file2Bytes(File file) {
        byte[] imageBytes = null;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            imageBytes = new byte[fileInputStream.available()];
            fileInputStream.read(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BizException(JsonReturnCode.FILE_DOWNLOAD_ERROR);
        }
        return imageBytes;
    }

    public static void outFile(HttpServletResponse response, File file) {
        String fileName = file.getName();
        if (!file.exists()) {
            throw new BizException(JsonReturnCode.FILE_DOWNLOAD_ERROR);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        // 创建缓冲输入流
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(file2Bytes(file));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 重新生成文件名
     *
     * @param originalFilename 原文件名
     * @return 新文件名
     */
    public static String makeFileName(String originalFilename) {
        String suffix = "";
        int beginIndex = originalFilename.lastIndexOf(".");
        if (beginIndex > 0) {
            suffix = originalFilename.substring(beginIndex);
        }
        return UUID.randomUUID().toString().replace("-", "") + suffix;

    }

    /**
     * @param path 原文路径
     * @return 新文件名
     */
    public static String getPath(String path) {
        String s = DateUtil.date2Str(LocalDateTime.now(), DateUtil.DATE_YMD);
        return CLASS_PATH + File.separator + path + s + File.separator;
    }


}
