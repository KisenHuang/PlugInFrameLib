package com.kisen.plugframelib.utils.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;


import com.kisen.plugframelib.BaseApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件管理类
 * Created by huang on 2017/4/17.
 */
public class FileUtils {
    private static FileUtils instance;

    // 下载目录
    private File downloadDir;
    // 缓存目录
    private File cacheDir;
    // 图片缓存目录
    private File cacheImageDir;
    // 语音文件缓存目录
    private File cacheAudioDir;
    // 错误日志目录
    private File errorDir;
    // 离线缓存目录
    private File cacheOffLineDir;
    // 配置文件目录
    private File cacheConfigDir;
    private File tempCacheDir;
    private File configCacheDir;

    public static FileUtils getInstance() {
        if (instance == null) {
            synchronized (FileUtils.class) {
                if (instance == null) {
                    instance = new FileUtils(BaseApplication.getInstance());
                }
            }
        }
        return instance;
    }

    @SuppressWarnings("unused")
    private FileUtils() {
    }


    private FileUtils(Context context) {
        String cache_dir = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + BaseApplication.getInstance().localName + File.separator;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(cache_dir, "/cache");
        } else {
            cacheDir = context.getCacheDir();
        }
        if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            if (!cacheDir.exists()) {
                throw new FileCreateException(cacheDir + "// create failed!");
            }
        }
        //临时文件缓存-清除缓存时会被清空
        tempCacheDir = newFile(cacheDir, "/tempCache");
        //配置文件缓存-清除缓存时不会被清空
        configCacheDir = newFile(cacheDir, "/configCache");
        //图片缓存目录
        cacheImageDir = newFile(tempCacheDir, "/image/");
        //文件下载目录
        downloadDir = newFile(tempCacheDir, "/download/");
        //多媒体目录
        cacheAudioDir = newFile(tempCacheDir, "/audio/");
        //错误日志目录
        errorDir = newFile(tempCacheDir, "/error/");
        //离线缓存目录
        cacheOffLineDir = newFile(tempCacheDir, "/off_line/");
        //配置文件目录
        cacheConfigDir = newFile(configCacheDir, "/config/");
    }

    private static File newFile(File path, String fileName) {
        File file = new File(path, fileName);
        if (!file.exists() && !file.mkdirs()) {
            if (!file.exists()) {
                throw new FileCreateException(file.getAbsolutePath() + "// create failed!");
            }
        }
        return file;
    }

    private static File newFile(String path) {
        File file = new File(path);
        if (!file.exists() && !file.mkdirs()) {
            if (!file.exists()) {
                throw new FileCreateException(file.getAbsolutePath() + "// create failed!");
            }
        }
        return file;
    }

    /**
     * 获取缓存目录
     */
    @SuppressWarnings("unused")
    public File getCacheDir() {
        return cacheDir;
    }

    /**
     * 获取配置缓存目录
     */
    @SuppressWarnings("unused")
    public File getConfigCacheDir() {
        return configCacheDir;
    }

    /**
     * 获取临时缓存目录
     */
    @SuppressWarnings("unused")
    public File getTempCacheDir() {
        return tempCacheDir;
    }

    /**
     * 获取缓存图片目录
     */
    @SuppressWarnings("unused")
    public File getCacheImageDir() {
        return cacheImageDir;
    }

    /**
     * 获取离线缓存目录
     */
    @SuppressWarnings("unused")
    public File getCacheOffLineDir() {
        return cacheOffLineDir;
    }

    /**
     * 获取离线缓存目录
     */
    @SuppressWarnings("unused")
    public File getCacheConfigDir() {
        return cacheConfigDir;
    }

    /**
     * 获取多媒体缓存目录
     */
    @SuppressWarnings("unused")
    public File getCacheAudioDir() {
        return cacheAudioDir;
    }

    /**
     * 获取下载目录
     */
    @SuppressWarnings("unused")
    public File getDownloadDir() {
        return downloadDir;
    }

    /**
     * 获取错误目录
     */
    @SuppressWarnings("unused")
    public File getErrorDir() {
        return errorDir;
    }

    /**
     * 创建一个临时图片文件
     */
    @SuppressWarnings("unused")
    public File newTempImageFile() {
        return new File(cacheImageDir, System.currentTimeMillis() + ".jpg");
    }

    /**
     * 创建一个临时语音文件
     */
    @SuppressWarnings("unused")
    public File newTempAudioFile() {
        return new File(cacheAudioDir, System.currentTimeMillis() + ".amr");
    }

    /**
     * 判断是否安装SD卡
     */
    @SuppressWarnings("unused")
    public static boolean checkSaveLocationExists() {
        String sDCardStatus = Environment.getExternalStorageState();
        return sDCardStatus.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 删除指定目录下文件及目录
     */
    @SuppressWarnings("all")
    public static void deleteFolderFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath());
                    }
                }
                if (!file.isDirectory()) {// 如果是文件，删除
                    file.delete();
                } else {// 目录
                    if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                        file.delete();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除文件
     */
    @SuppressWarnings("unused")
    public static boolean deleteFile(String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();

        if (!fileName.equals("")) {

            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + fileName);
            checker.checkDelete(newPath.toString());
            if (newPath.isFile()) {
                try {
                    status = newPath.delete();
                } catch (SecurityException se) {
                    se.printStackTrace();
                    status = false;
                }
            } else
                status = false;
        } else
            status = false;
        return status;
    }

    @SuppressWarnings("unused")
    public static String getTempFilePath(Context context) {
        return context.getCacheDir() + File.separator + "temp";
    }

    @Nullable
    @SuppressWarnings("unused")
    public static String getSDPath() {
        File sdDir;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            return sdDir.getPath();
        } else {
            return null;
        }
    }

    /**
     * 将图片保存到指定的路径
     */
    @SuppressWarnings("unused")
    public static String savePic(String filePath, String name, Bitmap bitmap) {
        File file = newFile(filePath);
        File f = new File(filePath + File.separator + name);
        try {
            boolean success = f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f.getPath();
    }

    /**
     * 将图片保存到指定的路径
     */
    @SuppressWarnings("unused")
    public static String savePic(String filePath, Bitmap bitmap) {
        File f = new File(filePath);
        try {
            boolean success = f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f.getPath();
    }

    /**
     * 获取目录文件大小
     */
    @SuppressWarnings("all")
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 转换文件大小
     *
     * @return B/KB/MB/GB
     */
    @SuppressWarnings("unused")
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString;
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    @SuppressWarnings("all")
    public static class FileCreateException extends RuntimeException {

        @SuppressWarnings("all")
        public FileCreateException(String detailMessage) {
            super(detailMessage);
        }
    }

}