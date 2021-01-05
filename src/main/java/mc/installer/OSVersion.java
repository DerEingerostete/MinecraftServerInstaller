package mc.installer;

/**
 * @author DerEingerostete
 * @since 1.0
 */
public class OSVersion {

    public static OS getOS() {
        String os = System.getProperty("os.name", "other");
        if (os == null) return OS.OTHER;
        os = os.toLowerCase();
        if (os.startsWith("windows")) return OS.WINDOWS;
        else if (os.contains("mac")) return OS.MACOS;
        else if (os.contains("linux")) return OS.LINUX;
        else if (os.contains("nix")) return OS.UNIX;
        else if (os.contains("nux")) return OS.UNIX;
        else if (os.contains("aix")) return OS.UNIX;
        else if (os.contains("sunos")) return OS.SOLARIS;
        else return OS.OTHER;
    }

    public enum OS {
        WINDOWS,
        MACOS,
        LINUX,
        UNIX,
        SOLARIS,
        OTHER
    }

}
