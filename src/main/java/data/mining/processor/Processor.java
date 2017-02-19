package data.mining.processor;

import java.util.List;
import java.util.logging.Logger;

public abstract class Processor<T> {

    protected final Logger logger = Logger.getLogger(getClass().getName());

    protected List<T> data;

    protected List<T> getData() {
        if (data == null) {
            data = createData();
        }
        return data;
    }

    protected Integer parseStringValue(String value) {
        String number = value.replaceAll("\\D", "");
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            logger.severe("Value: '" + value + "' can not be parsed to Integer! " + e.getMessage());

            return null;
        }
    }

    protected abstract List<T> createData();

}
