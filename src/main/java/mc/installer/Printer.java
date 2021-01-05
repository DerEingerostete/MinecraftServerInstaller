package mc.installer;

/**
 * @author DerEingerostete
 * @since 1.0
 */
public class Printer {
    private static boolean silent = false;
    private static boolean silentErr = false;
    private static boolean silentQuestions = false;
    private static boolean fullStackTrace = false;

    private static boolean noLog = false;
    private static final Logger logger = new Logger("log-", "");

    public static void setFullStackTrace(boolean bool) {
        fullStackTrace = bool;
    }

    public static void setNoLog(boolean bool) {
        noLog = bool;
    }

    public static void setSilentQuestions(boolean bool) {
        silentQuestions = bool;
    }

    public static void setSilent(boolean bool) {
        silent = bool;
    }

    public static void setSilentErr(boolean bool) {
        silentErr = bool;
    }

    public static void printQ(Object object) {
        if (!silent && !silentQuestions)
            System.out.println(object);
    }

    public static void printErr(Object object) {
        if (!silentErr) System.err.println(object);
    }

    public static void print(Object object) {
        if (!silent) System.out.println(object);
    }

    public static void printStackTrace(Throwable throwable) {
        if (!silentErr && fullStackTrace) throwable.printStackTrace();
    }

    public static void log(Object object) {
        if (!noLog) logger.println(object);
    }

    public static void closeLogger() {
        logger.close();
    }

}
