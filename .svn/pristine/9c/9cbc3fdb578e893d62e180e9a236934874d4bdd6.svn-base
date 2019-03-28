package cn.ruicz.basecore.manager;

import android.os.Environment;

import cn.ruicz.utilcode.util.FileUtils;


/**
 * Created by CLW on 2017/3/23.
 * 本地缓存文件目录管理
 */

public class FileManager {

    private final static String APP_DIR = "/health/";
    private final static String CACHE = "Cache/";
    private final static String FILE = "File/";
    private final static String FILE_NET = "Net/";
    private final static String FILE_LOG = FILE+"Log/";
    private final static String MEDIA = "Media/";
    private final static String MEDIA_VOICE = MEDIA+"VOICE/";
    private final static String MEDIA_IMAGE = MEDIA+"IMAGE/";
    private final static String MEDIA_MAP_SCREEN = MEDIA+"MAP_SCREEN/";
    private final static String MEDIA_VIDEO = MEDIA+"VIDEO/";
    private final static String MEDIA_IMAGE_THUMB = MEDIA_IMAGE+"THUMB/";
    private final static String MEDIA_AVATAR = MEDIA+"AVATAR/";

    public static String getAppDir(){
        return Environment.getExternalStorageDirectory()+"/"+APP_DIR;
    }

    public static String getCacheDir(){
        FileUtils.createOrExistsDir(getAppDir()+CACHE);
        return getAppDir()+CACHE;
    }

    public static String getFileDir(){
        FileUtils.createOrExistsDir(getAppDir()+FILE);
        return getAppDir()+FILE;
    }

    public static String getFileLog(){
        FileUtils.createOrExistsDir(getAppDir()+FILE_LOG);
        return getAppDir()+FILE_LOG;
    }

    public static String getFileNet(){
        FileUtils.createOrExistsDir(getAppDir()+FILE_NET);
        return getAppDir()+FILE_NET;
    }

    public static String getMediaDir(){
        FileUtils.createOrExistsDir(getAppDir()+MEDIA);
        return getAppDir()+MEDIA;
    }

    public static String getMEDIA_AVATAR(){
        FileUtils.createOrExistsDir(getAppDir()+MEDIA_AVATAR);
        return getAppDir()+MEDIA_AVATAR;
    }

    public static String getMEDIA_VOICE(){
        FileUtils.createOrExistsDir(getAppDir()+MEDIA_VOICE);
        return getAppDir()+MEDIA_VOICE;
    }

    public static String getMEDIA_IMAGE(){
        FileUtils.createOrExistsDir(getAppDir()+MEDIA_IMAGE);
        return getAppDir()+MEDIA_IMAGE;
    }

    public static String getMEDIA_VIDEO(){
        FileUtils.createOrExistsDir(getAppDir()+MEDIA_VIDEO);
        return getAppDir()+MEDIA_VIDEO;
    }

    public static String getMEDIA_IMAGE_THUMB(){
        FileUtils.createOrExistsDir(getAppDir()+MEDIA_IMAGE_THUMB);
        return getAppDir()+MEDIA_IMAGE_THUMB;
    }

    public static String getMEDIA_MAP_SCREEN(){
        FileUtils.createOrExistsDir(getAppDir()+MEDIA_MAP_SCREEN);
        return getAppDir()+MEDIA_MAP_SCREEN;
    }
}
