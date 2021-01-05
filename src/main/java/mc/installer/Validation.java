package mc.installer;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author DerEingerostete
 * @since 1.0
 */
public class Validation {

    public static boolean existsMCVersion(String version) {
        if (!version.matches("[.0-9]*")
                && !version.equalsIgnoreCase("latest"))
            return false;
        try {
            String path = "https://hub.spigotmc.org/versions/%ver%.json";
            URL url = new URL(path.replace("%ver%", version).toLowerCase());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.disconnect();
            return connection.getResponseCode() == 200;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
    }

}
