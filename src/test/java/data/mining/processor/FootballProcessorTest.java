package data.mining.processor;

import data.mining.util.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;


@RunWith(PowerMockRunner.class)
@PrepareForTest(FileUtils.class)
public class FootballProcessorTest {

    private FootballProcessor processor;

    @Before
    public void setUp() {
        processor = new FootballProcessor();
    }

    @Test
    public void testProcessorWithExistingResource() {
        String result = processor.findSmallestGoalsDifference();

        assertEquals("Club: Aston_Villa difference: 1", result);
    }

    @Test
    public void testProcessorWithNotExistingResource() throws Exception {
        PowerMockito.mockStatic(FileUtils.class);
        Mockito.when(FileUtils.getResourceContent(WeatherProcessor.DATA_FILE_NAME)).thenReturn(Collections.emptyList());

        String result = processor.findSmallestGoalsDifference();

        assertEquals("No data!", result);
    }

}
