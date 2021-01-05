package mc.installer;

import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @author DerEingerostete
 * @since 1.0
 */
public class FilePermissions {

    public static boolean hasFull(File file) {
        return file.canExecute() && file.canRead() && file.canWrite();
    }

    public static boolean isLocked(File file) {
        try {
            FileUtils.touch(file);
            return false;
        } catch (Exception exception) {
            return true;
        }
    }

    public static boolean hasAccess(File file) {
        return hasFull(file) && !isLocked(file);
    }

}
