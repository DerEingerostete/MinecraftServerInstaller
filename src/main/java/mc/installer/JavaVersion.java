package mc.installer;

/**
 * @author DerEingerostete
 * @since 1.0
 */
public class JavaVersion {

    public static String getVersion() {
        return System.getProperty("java.version");
    }

    public static boolean isBelowJava8() {
        String version = getVersion();
        if (version.contains("_"))
            version = version.substring(0, version.indexOf("_"));
        String[] versionSplit = version.split("\\.");

        try {
            if (version.startsWith("1.") && versionSplit.length > 1) {
                return Integer.parseInt(versionSplit[1]) < 8;
            } else {
                if (versionSplit.length > 0) {
                    return Integer.parseInt(versionSplit[0]) < 1;
                } else throw new IllegalStateException("Unknown version");
            }
        } catch (NumberFormatException exception) {
            throw new IllegalStateException("Unknown version");
        }
    }

}
