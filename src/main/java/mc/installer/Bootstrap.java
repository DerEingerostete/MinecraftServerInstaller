package mc.installer;

/**
 * @author DerEingerostete
 * @since 1.0
 */
public class Bootstrap {

    public static void main(String[] args) {
        ExitReason.fill();

        OSVersion.OS os = OSVersion.getOS();
        if (os != OSVersion.OS.LINUX && os != OSVersion.OS.WINDOWS) {
            System.err.println("Unsupported OS detected (" + os.name() + "). MinecraftServerInstaller " +
                    "can only run on Windows or Linux (Ubuntu, Debian, etc).");
            System.exit(ExitReason.NO_OS_SUPPORT.getId());
        }

        try {
            if (JavaVersion.isBelowJava8()) {
                System.err.println("Outdated Java detected (" + JavaVersion.getVersion() + ")");
                System.err.println("MinecraftServerInstaller requires at least Java 8.");
                System.err.println("Please update Java and try again.");
                System.err.println("You may use java -version to double check your Java version.");
                System.exit(ExitReason.UNSUPPORTED_JAVA_VERSION.getId());
            }
        } catch (Throwable throwable) {
            System.err.println("\n *** WARNING *** ");
            System.err.println("=======[WARNING]=======");
            System.err.println("An unsupported java version was detected " +
                    "(" + JavaVersion.getVersion() + ").");
            System.err.println("MinecraftServerInstaller is developed to work with " +
                    "all version starting at Java 8 but was only tested on version Java 8!");
            System.err.println("Please make sure your version is Java 8 or higher.");
            System.err.println("You may use java -version to double check your Java version.");
            System.err.println("Note: If the newest version of Java doesn't work try Java 8.");
            System.err.println("=======[WARNING]=======");
            System.err.println(" *** WARNING *** \n");
        }

        long maxMemory = Runtime.getRuntime().maxMemory() >> 20;
        if (maxMemory < 448) {
            System.err.println("MinecraftServerInstaller requires at least 512 megabytes" +
                    " of memory to run (1024 megabytes recommended), but has only detected " + maxMemory + " megabytes");
            System.err.println("Please re-run MinecraftServerInstaller with " +
                    "manually specified memory, e.g: java -Xmx1024M -jar MCServerInstaller.jar ");
            System.exit( ExitReason.NOT_ENOUGH_RAM.getId());
        }

        ArgumentParser.checkArgs(args);
    }

}
