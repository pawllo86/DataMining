package data.mining.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

public class FileUtils {

    public static List<String> getResourceContent(String name) throws IOException {
        URL url = FileUtils.class.getClassLoader().getResource(name);

        if (url != null) {
            return Files.readAllLines(new File(url.getFile()).toPath());
        }
        return Collections.emptyList();
    }

}
