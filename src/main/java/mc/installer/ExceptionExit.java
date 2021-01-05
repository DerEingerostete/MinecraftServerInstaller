package mc.installer;

/**
 * @author DerEingerostete
 * @since 1.0
 */
public class ExceptionExit {

    public static void exitDefMsg(String message, Throwable throwable) {
        Printer.printErr("An unexpected error occurred while " + message);
        Printer.printStackTrace(throwable);
        System.exit(ExitReason.UNKNOWN_EXCEPTION.getId());
    }

}
