import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Utilities {

    public static String readStringFromLocalFile(String localResourceFileName) {
        return fileToString(stringPathToFile(localResourceFileName));
    }

    public static void writeStringToLocalFile(String localResourceFileName, String fileData) {
        String dir = Paths.get("").toAbsolutePath().getParent().toString();
        String localPath = dir + "\\resources\\out\\" + localResourceFileName;
        
        try {
            FileWriter fileWriter = new FileWriter(localPath);
            fileWriter.write(fileData);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
