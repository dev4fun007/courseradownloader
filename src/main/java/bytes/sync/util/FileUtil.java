package bytes.sync.util;

import java.io.File;

public class FileUtil {

    private static final String BASE_PATH = "Coursera";

    public static void createCourseFolder(String courseName) {
        String path = BASE_PATH + File.separator + courseName;
        createFolder(sanitizePath(path));
    }

    public static void createWeekFolder(String courseFolder, String week) {
        String path = BASE_PATH + File.separator + courseFolder + File.separator + week;
        createFolder(sanitizePath(path));
    }

    public static String createModuleFolder(String courseFolder, String week, String moduleName) {
        String path = BASE_PATH + File.separator + courseFolder + File.separator + week + File.separator + moduleName;
        createFolder(sanitizePath(path));
        return path;
    }

    public static String createLessonFolder(String courseFolder, String week, String moduleName, String lessonName) {
        String path = BASE_PATH + File.separator + courseFolder + File.separator + week + File.separator + moduleName + File.separator + lessonName;
        createFolder(sanitizePath(path));
        return path;
    }

    public static String createLessonFolder(String courseFolder, String week, String lessonName) {
        String path = BASE_PATH + File.separator + courseFolder + File.separator + week + File.separator + lessonName;
        createFolder(sanitizePath(path));
        return path;
    }

    private static String sanitizePath(String path) {
        path = path.replaceAll("[:;?\"<>|]", "");
        return path;
    }

    private static void createFolder(String path) {
        File dir = new File(path);
        if(dir.mkdirs()) {
            System.out.println("Folder created at path: " + dir.getAbsolutePath());
        } else {
            System.out.println("Failed to create directory: " + dir.getAbsolutePath());
        }
    }

}
