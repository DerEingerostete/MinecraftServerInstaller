package mc.installer;

import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author DerEingerostete
 * @since 1.0
 */
public class Logger {
    private PrintStream stream;

    public Logger(String prefix, String suffix) {
        String pattern = "MM-dd-yyyy";
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String formatted = format.format(date);

        File tempFile = new File(prefix + formatted + suffix);
        int i = 0;
        while (tempFile.exists()) {
            i++;
            tempFile = new File(prefix
                    + formatted + "-" + i + suffix);
        }
        File file = tempFile;

        try {
            stream = new PrintStream(file, StandardCharsets.UTF_8.toString());
        } catch (Exception exception) {
            Printer.printErr("Could not initialize Logger");
            Printer.printStackTrace(exception);
            System.exit(ExitReason.UNKNOWN_EXCEPTION.getId());
        }
    }

    private String getFormattedDate() {
        String pattern = "HH-mm-ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = new Date();
        return "[" + format.format(date) + "]";
    }

    public void println(Object object) {
        stream.println(getFormattedDate() + object);
        stream.flush();
    }

    public void close() {
        stream.close();
    }

}
