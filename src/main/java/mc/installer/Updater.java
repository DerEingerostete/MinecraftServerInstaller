package mc.installer;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author DerEingerostete
 * @since 1.0
 */
public class Updater {

    public static boolean isLatest() throws IOException {
        return getLocalVersion() == getLatestVersion();
    }

    public static double getLocalVersion() throws IOException {
        ClassLoader classLoader = Updater.class.getClassLoader();
        URL url = classLoader.getResource("version.txt");
        return loadFromURL(url);
    }

    public static double getLatestVersion() throws IOException {
        String path = "https://raw.githubusercontent.com/" +
                "DerEingerostete/MinecraftServerInstaller/" +
                "master/src/main/resources/version.txt";
        return loadFromURL(new URL(path));
    }

    private static double loadFromURL(URL url) throws IOException {
        InputStream stream = url.openStream();
        StringWriter writer = new StringWriter();
        Charset charset = StandardCharsets.UTF_8;
        IOUtils.copy(stream, writer, charset);
        String string = writer.toString();
        string = string.substring(string.indexOf(": ") + 2);
        return Double.parseDouble(string);
    }

}
