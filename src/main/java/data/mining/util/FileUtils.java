package data.mining.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;

public class FileUtils {

    public static List<String> getResourceContent(String name) throws IOException {
        String filePath = FileUtils.class.getClassLoader().getResource(name).getFile();
        File file = new File(filePath);

        return Files.readAllLines(file.toPath());
    }

}
