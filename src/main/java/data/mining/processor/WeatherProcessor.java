package data.mining.processor;

import data.mining.bean.WeatherDataBean;
import data.mining.util.FileUtils;
import data.mining.util.WeatherDataUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class WeatherProcessor {

    private static final String DATA_FILE_NAME = "weather.dat";

    private final Logger logger = Logger.getLogger(getClass().getName());

    private List<WeatherDataBean> weatherDataBeans;

    public void showSmallestTemperatureSpread() {
        List<WeatherDataBean> sortedWeatherDataBeans = getWeatherDataBeans().stream()
                .sorted(Comparator.comparing(WeatherDataBean::getSpreadTemperature).reversed()).collect(Collectors.toList());

        WeatherDataBean bean = sortedWeatherDataBeans.get(0);
        if (bean != null) {
            System.out.println("The Smallest Temperature Spread:");
            System.out.println("Day: " + bean.getDay() + " spread: " + bean.getSpreadTemperature());
        } else {
            System.out.println("No data!");
        }
    }

    private List<WeatherDataBean> getWeatherDataBeans() {
        if (weatherDataBeans == null) {
            try {
                List<String> data = FileUtils.getResourceContent(DATA_FILE_NAME);
                data = data.stream().filter(line -> !line.isEmpty()).collect(Collectors.toList());
                data.remove(0);
                data.remove(data.size() - 1);

                weatherDataBeans = WeatherDataUtils.buildWeatherDataBean(data);
            } catch (IOException e) {
                logger.severe(e.getMessage());
                weatherDataBeans = Collections.emptyList();
            }
        }
        return weatherDataBeans;
    }

}
