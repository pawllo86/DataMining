package data.mining.processor;

import data.mining.bean.WeatherDataBean;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WeatherProcessor extends Processor<WeatherDataBean> {

    public static final String DATA_FILE_NAME = "weather.dat";

    public String findSmallestTemperatureSpread() {
        if (getData() != null && !getData().isEmpty()) {
            Optional<WeatherDataBean> bean = getData().stream().min(Comparator.comparing(WeatherDataBean::getSpreadTemperature));

            if (bean.isPresent()) {
                return "Day: " + bean.get().getDay() + " spread: " + bean.get().getSpreadTemperature();
            }
        }
        return "No data!";
    }

    @Override
    protected List<String> filterLines(List<String> lines) {
        List<String> filteredLines = super.filterLines(lines);

        if (filteredLines != null && !filteredLines.isEmpty()) {
            filteredLines.remove(0);
            filteredLines.remove(filteredLines.size() - 1);
        }
        return filteredLines;
    }

    @Override
    protected WeatherDataBean createDataBean(String[] lineWords) {
        WeatherDataBean bean = new WeatherDataBean();
        bean.setDay(parseStringValue(lineWords[0]));
        bean.setMaxTemperature(parseStringValue(lineWords[1]));
        bean.setMinTemperature(parseStringValue(lineWords[2]));

        return bean;
    }

    @Override
    protected String getFileName() {
        return DATA_FILE_NAME;
    }

}
