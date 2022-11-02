package cn.edu.bit.GSDB.utils;

import cn.edu.bit.GSDB.exception.BasicException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author wjk
 * @since 2022-10-20 11:33:50
 */
public class UploadUtils {

    private static final String FILE_TYPE_IMG = "img";

    private static final String FILE_TYPE_VIDEO = "video";

    private static final String CLASS_PATH_PREFIX;

    static {
        String CLASS_PATH_PREFIX1;
        try {
            Resource resource = new ClassPathResource("");
            CLASS_PATH_PREFIX1 = resource.getFile().getAbsolutePath()+"/";
        } catch (IOException e) {
            CLASS_PATH_PREFIX1 = "./";
        }
        CLASS_PATH_PREFIX = CLASS_PATH_PREFIX1;
    }


    /**
     * 图片上传，返回 classpath下的路径 如： /static/img/db1/organ/20211003_xxxx.jpg
     *
     * @param file
     * @param path
     * @return
     * @throws IOException
     */
    public static String uploadPicture(MultipartFile file, String path) {
        return upload(file, FILE_TYPE_IMG, path);
    }

    /**
     * 视频上传，返回 classpath下的路径 如： /static/video/db1/xxx/20211003_xxxx.mp4
     *
     * @param file
     * @param path
     * @return
     */
    public static String uploadVideo(MultipartFile file, String path) {
        return upload(file, FILE_TYPE_VIDEO, path);
    }


    /**
     * 文件上传
     *
     * @param file     文件
     * @param fileType 文件类型
     * @param path     文件路径 /xx/xx/
     * @return
     */
    private static String upload(MultipartFile file, String fileType, String path) {
        String fileName = file.getOriginalFilename();
        String pathPrefix, filePath;
        if (fileType.equals(FILE_TYPE_IMG)) {
            pathPrefix = "static/img";
        } else if (fileType.equals(FILE_TYPE_VIDEO)) {
            pathPrefix = "static/video";
        } else {
            pathPrefix = "static/other";
        }
        pathPrefix += path;
        try {
            File directory = new File(CLASS_PATH_PREFIX + pathPrefix);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            pathPrefix += CommonUtils.dateToStr() + "_";
            filePath = pathPrefix + fileName;
            File dest = new File(CLASS_PATH_PREFIX + filePath);
            if (dest.exists()) {
                filePath = pathPrefix + CommonUtils.timeToStr() + "_" + fileName;
                dest = new File(CLASS_PATH_PREFIX + filePath);
            }
            file.transferTo(dest.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BasicException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "文件上传失败！");
        }
        return "/" + filePath;
    }

    /**
     * 文件删除，
     *
     * @param path 如  /static/img/db1/organ/2021_10_12_ssss.jpg
     * @return
     * @throws IOException
     */
    public static void removeFile(String path) {
        try {
            String absPath = CLASS_PATH_PREFIX + path;
            File dest = new File(absPath);
            dest.delete();
        } catch (Exception e) {
            throw new BasicException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "文件删除失败！");
        }
    }
}
