package data.mining.util;


import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FileUtilsTest {

    @Test
    public void findResourceWithNullNameContent() {
        List<String> lines = null;
        try {
            lines = FileUtils.getResourceContent(null);

            fail("Expected exception not occur!");
        } catch (NullPointerException e) {
            assertEquals(null, lines);
        } catch (IOException e) {
            fail("IOException not expected!");
        }
    }

    @Test
    public void findNotExistingResourceContent() {
        List<String> lines;
        try {
            lines = FileUtils.getResourceContent("not_existing.file");

            assertEquals(0, lines.size());
        } catch (IOException e) {
            fail("IOException not expected!");
        }
    }

    @Test
    public void findExistingResourceContent() {
        List<String> lines;
        try {
            lines = FileUtils.getResourceContent("football.dat");

            assertEquals(22, lines.size());
        } catch (IOException e) {
            fail("IOException not expected!");
        }
    }

}
