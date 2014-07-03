package com.si.helperc.util;

import java.io.File;
import java.io.IOException;

/**
 * @description Android中的一些操作类
 * 
 *  1.SD卡检测 {@link #isHasSD()}　
 *  2.检测SD卡上文件是否存在 {@link #isFileExists(String)}
 *  3.在SD卡上创建文件 {@link #createFileOnSD(String, String)}
 *  4.删掉SD卡上文件 {@link #deleteFileOnSD(String)}
 * @author selfimpr
 * 
 * @date 2014-5-7 下午3:21:12
 * 
 */
public class AndroidUtil {
	/**
     * 检测是否存在SD卡，或者外部存储
     * @return 存在，返回true，不存在，返回false
     */
	
	public static final String  SD_ROOT_PATH =android.os.Environment.getExternalStorageDirectory() + File.separator ;
	
    public static boolean isHasSD(){
            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                    return true;
            else 
                    return false;
    }
    /**
     * 检测SD上某文件是否存在
     * 注意：需要首先判断是否存在SD卡，参见{@link #isHasSD()}
     * @param _filepath 文件路径
     * @return
     */
    public static boolean isFileExists(String _filepath){
            File file = new File(SD_ROOT_PATH + _filepath);
            if (file.exists())
                    return true;
            else 
                    return false;
    }
    
    /**
     * 在SD卡上创建文件
     * @param _filepath 文件名称
     * @param _folder 文件夹名称
     */
    public static void createFileOnSD(String _folder, String _filepath){
            File file = new File(SD_ROOT_PATH+ _folder + _filepath);
            File fileFolder = new File(SD_ROOT_PATH + _folder);
            
            if (!fileFolder.exists())
                    fileFolder.mkdirs();
            //这里不做文件是否存在的判断
            try {
                    file.createNewFile();
            } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
    }
    
    /**
     * 删除SD卡上文件
     * @param _filepath 文件路径
     */
    public static void deleteFileOnSD(String _filepath){
            File file = new File(SD_ROOT_PATH + _filepath);
            //不需要判断文件是否存在
            file.delete();
            
    }
}
