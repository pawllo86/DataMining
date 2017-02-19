package data.mining.processor;

import data.mining.bean.WeatherDataBean;
import data.mining.util.FileUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WeatherProcessor extends Processor<WeatherDataBean> {

    private static final String DATA_FILE_NAME = "weather.dat";

    public void showSmallestTemperatureSpread() {
        List<WeatherDataBean> sortedWeatherDataBeans = getData().stream()
                .sorted(Comparator.comparing(WeatherDataBean::getSpreadTemperature).reversed()).collect(Collectors.toList());

        WeatherDataBean bean = sortedWeatherDataBeans.get(0);
        if (bean != null) {
            System.out.println("The Smallest Temperature Spread:");
            System.out.println("Day: " + bean.getDay() + " spread: " + bean.getSpreadTemperature());
        } else {
            System.out.println("No data!");
        }
    }

    @Override
    protected List<WeatherDataBean> createData() {
        try {
            List<String> data = FileUtils.getResourceContent(DATA_FILE_NAME);
            data = data.stream().filter(line -> !line.isEmpty()).collect(Collectors.toList());
            data.remove(0);
            data.remove(data.size() - 1);

            return buildWeatherDataBean(data);
        } catch (IOException e) {
            logger.severe(e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<WeatherDataBean> buildWeatherDataBean(Collection<String> weatherData) {
        return weatherData.stream().map(dayMeasurement -> createWeatherDataBean(Pattern.compile("\\s+")
                .split(dayMeasurement.trim()))).collect(Collectors.toList());
    }

    private WeatherDataBean createWeatherDataBean(String[] data) {
        WeatherDataBean bean = new WeatherDataBean();
        bean.setDay(parseStringValue(data[0]));
        bean.setMaxTemperature(parseStringValue(data[1]));
        bean.setMinTemperature(parseStringValue(data[2]));

        return bean;
    }

}
