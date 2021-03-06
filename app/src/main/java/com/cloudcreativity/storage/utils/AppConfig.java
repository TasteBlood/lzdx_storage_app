package com.cloudcreativity.storage.utils;

/**
 * 这个app的属性配置
 */
public class AppConfig {
    /**
     * 是否是开发调试阶段
     */
    public static boolean DEBUG = true;
    /**
     * 网络数据缓存的文件夹名称
     */
    public static final String CACHE_FILE_NAME = "app_cache";
    /**
     * 网络图片缓存的文件夹名称
     */
    public static final String CACHE_IMAGE_NAME = "app_image_cache";
    /**
     * 这是SharePreference的名称
     */
    public static final String SP_NAME = "storage_app_config";

    /**
     * 这是统一的文件名
     */
    public static String FILE_NAME = "storage_image_%d.%s";

    /**
     * 这是APP热更新的下载缓存目录
     */
    static String APP_HOT_UPDATE_FILE = "storage_app_hot_update.apk";

    /**
    * @author : xuxiwu
    * created: 2019/8/2 16:22
    * desc: 用户角色
    */
    public interface USER_ROLE{
        int MANAGER = 1;
        int PRICE = 2;
        int RESTAURANT = 3;
        int OUT_ADMIN = 4;
        int SP_ZXFZR = 5;
        int SP_ZGBLD = 6;
        int SP_CWSP = 7;
        int SP_BLD = 8;
    }

}
