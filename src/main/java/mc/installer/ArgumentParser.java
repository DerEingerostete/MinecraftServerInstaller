package mc.installer;

import org.apache.commons.cli.*;

import java.io.File;

/**
 * @author DerEingerostete
 * @since 1.0
 */
public class ArgumentParser {
    private static boolean eula;
    private static String version;
    private static boolean noLog;
    private static boolean noQuestions;
    private static File customDir;
    private static File propertiesFile;
    private static boolean fast;

    public static void checkArgs(String[] args) {
        Options options = new Options();
        options.addOption("installOption", true, "Sets the installation option directly");
        options.addOption("mcVersion", true, "Sets the minecraft version directly");
        options.addOption("agreeMcEula", true, "Accepts the Minecraft EULA directly");
        options.addOption("silent", false, "Deactivates the output of all messages");
        options.addOption("fullError", false, "In the event of an error, prints the StackTrace.");
        options.addOption("silentErr", false, "Deactivates the output of any error logs");
        options.addOption("noQuestions", false, "Deactivates all the question messages");
        options.addOption("noLog", false, "Deactivates the log file");
        options.addOption("newestVersion", false, "Sets the minecraft version to the newest");
        options.addOption("customDir", true, "Sets the output directory");
        options.addOption("version", false, "Prints the version of MinecraftServerInstaller");
        options.addOption("isLatest", false, "Checks for MinecraftServerInstaller updates");
        options.addOption("presetProperties", true, "Sets the used server.properties file");
        options.addOption("fast", false, "Fast installation of the latest minecraft version");
        options.addOption("exitId", true, "Returns the name of the exit by its id");
        options.addOption("help", false, "Prints all the available arguments");

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine line = parser.parse(options, args);

            if (line.hasOption("help")) {
                try {
                    HelpFormatter formatter = new HelpFormatter();
                    formatter.printHelp("MCServerInstaller.jar",
                            "\nThe following arguments can be used:",
                            options, "\nDeveloped by DerEingerostete", true);
                } catch (Throwable throwable) {
                    ExceptionExit.exitDefMsg("sending help message", throwable);
                }
            }

            if (line.hasOption("silent")) Printer.setSilent(true);
            if (line.hasOption("silentErr")) Printer.setSilentErr(true);
            if (line.hasOption("noLog")) Printer.setNoLog(true);
            if (line.hasOption("fullError")) Printer.setFullStackTrace(true);

            if (line.hasOption("isLatest")) {
                try {
                    double local = Updater.getLocalVersion();
                    if (Updater.isLatest())
                        Printer.print("This version (" + local + ") is the latest");
                    else {
                        double latest = Updater.getLatestVersion();
                        Printer.print("This version (" + local + ") is " +
                                "not the latest (" + latest + ")");
                        Printer.print("You can find the latest version on: " +
                                "https://github.com/DerEingerostete/MinecraftServerInstaller");
                    }
                    System.exit(ExitReason.PROGRAM_REACHED_END.getId());
                } catch (Throwable throwable) {
                    ExceptionExit.exitDefMsg("checking version", throwable);
                }
            }

            if (line.hasOption("version")) {
                try {
                    double version = Updater.getLocalVersion();
                    Printer.print("MinecraftServerInstaller version " + version);
                    System.exit(ExitReason.PROGRAM_REACHED_END.getId());
                } catch (Throwable throwable) {
                    ExceptionExit.exitDefMsg("getting the version of MinecraftServerInstaller", throwable);
                }
            }

            if (line.hasOption("exitId")) {
                String value = line.getOptionValue("exitId");
                try {
                    Integer id = Integer.parseInt(value);
                    ExitReason reason = ExitReason.fromCode(id);
                    if (reason == null) Printer.print("This exitId does not have a reason");
                    else Printer.print("ExitId " + id + " has the reason: " + reason.name());
                } catch (NumberFormatException exception) {
                    Printer.print("This input is not supported");
                    Printer.printStackTrace(exception);
                }
                System.exit(ExitReason.PROGRAM_REACHED_END.getId());
            }

            if (line.hasOption("agreeMcEula")) {
                try {
                    String value = line.getOptionValue("eula", "false");
                    eula = Boolean.parseBoolean(value);
                    if (!eula) {
                        Printer.printErr("You have to accept to the Minecraft EULA" +
                                " to use the installer");
                        Printer.printErr("To directly accept the Minecraft EULA " +
                                "use the argument '-agree-mc-eula true'");
                        System.exit(ExitReason.NOT_ACCEPTED_EULA.getId());
                    }
                } catch (Throwable throwable) {
                    ExceptionExit.exitDefMsg("getting eula agreement", throwable);
                }
            }

            if (line.hasOption("mcVersion")) {
                try {
                    String value = line.getOptionValue("mcVersion");
                    if (Validation.existsMCVersion(value)) version = value;
                    else {
                        Printer.printErr("Could not find version " + value
                                + " does it exist? Try another version or use 'latest'");
                        Printer.printErr("Make sure you specify the " +
                                "version exactly (instead of 1.16, 1.16.4)");
                        System.exit(ExitReason.MC_VERSION_NOT_FOUND.getId());
                    }
                } catch (Throwable throwable) {
                    ExceptionExit.exitDefMsg("setting minecraft version", throwable);
                }
            }

            if (line.hasOption("customDir")) {
                try {
                    String value = line.getOptionValue("customDir");
                    File directory = new File(value);
                    if (directory.exists()) {
                        if (!FilePermissions.hasAccess(directory)) {
                            Printer.printErr("Java doesn't have access to this directory");
                            System.exit(ExitReason.NO_PERMISSIONS.getId());
                        }
                        if (!directory.isDirectory()) {
                            Printer.printErr("CustomDir must be a directory");
                            System.exit(ExitReason.CUSTOM_DIRECTORY_IS_A_FILE.getId());
                        }
                    } else directory.mkdir();
                } catch (Throwable throwable) {
                    ExceptionExit.exitDefMsg("setting custom directory", throwable);
                }
            }

            if (line.hasOption("newestVersion")) {
                try {
                    if (version == null) version = "latest";
                    else {
                        Printer.printErr("Minecraft Version is already set");
                        System.exit(ExitReason.MC_VERSION_ALREADY_SET.getId());
                    }
                } catch (Throwable throwable) {
                    ExceptionExit.exitDefMsg("setting minecraft version", throwable);
                }
            }

            Installer.install();
        } catch (Exception exception) {
            Printer.printErr("Invalid arguments: " + exception.getMessage());
            Printer.printStackTrace(exception);
            System.exit(ExitReason.UNKNOWN_EXCEPTION.getId());
        }
    }

    public static boolean hasAcceptedEula() {
        return eula;
    }

    public static String getVersion() {
        return version;
    }

    public static boolean isNoLog() {
        return noLog;
    }

    public static boolean isNoQuestions() {
        return noQuestions;
    }

    public static File getCustomDir() {
        return customDir;
    }

    public static File getPropertiesFile() {
        return propertiesFile;
    }

    public static boolean isFast() {
        return fast;
    }

}
