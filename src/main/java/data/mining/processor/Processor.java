package data.mining.processor;

import data.mining.util.FileUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class Processor<T> {

    protected final Logger logger = Logger.getLogger(getClass().getName());

    protected List<T> data;

    protected List<T> getData() {
        if (data == null) {
            data = createData();
        }
        return data;
    }

    protected List<T> createData() {
        try {
            List<String> lines = filterLines(FileUtils.getResourceContent(getFileName()));

            return buildDataBeans(lines);
        } catch (IOException e) {
            logger.severe(e.getMessage());
            return Collections.emptyList();
        }
    }

    protected List<String> filterLines(List<String> lines) {
        if (lines != null) {
            return lines.stream().filter(line -> !line.isEmpty()).collect(Collectors.toList());
        }
        return null;
    }

    protected final List<T> buildDataBeans(Collection<String> dataLines) {
        return dataLines.stream().map(dataLine -> createDataBean(Pattern.compile("\\s+")
                .split(dataLine.trim()))).collect(Collectors.toList());
    }

    protected final Integer parseStringValue(String value) {
        if (value != null) {
            String number = value.replaceAll("\\D", "");
            try {
                return Integer.parseInt(number);
            } catch (NumberFormatException e) {
                logger.severe("Value: '" + value + "' can not be parsed to Integer! " + e.getMessage());
            }
        }
        return null;
    }

    protected abstract String getFileName();

    protected abstract T createDataBean(String[] lineWords);

}
