import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Utilities {

    public static String readStringFromLocalFile(String localResourcesPath) {
        return fileToString(stringPathToFile(localResourcesPath));
    }

    private static File stringPathToFile(String path) {
        String filePath = null;
        try {
            filePath = Paths.get(Utilities.class.getResource(path).toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return (filePath == null) ? null : new File(filePath);
    }

    private static String fileToString(File file) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath())))
        {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
