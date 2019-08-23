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
    public static final String SP_NAME = "people_pass_app_config";

    /**
     * 这是统一的文件名
     */
    public static String FILE_NAME = "people_pass_image_%d.%s";

    /**
    * @author : xuxiwu
    * created: 2019/8/2 16:22
    * desc: 用户角色
    */
    public interface USER_ROLE{
        int MANAGER = 1;
        int PRICE = 2;
        int RESTAURANT = 3;
    }

}
