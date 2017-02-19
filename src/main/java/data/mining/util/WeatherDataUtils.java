package data.mining.util;

import data.mining.bean.WeatherDataBean;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WeatherDataUtils {

    private static final Logger LOGGER = Logger.getLogger(WeatherDataUtils.class.getName());

    public static List<WeatherDataBean> buildWeatherDataBean(Collection<String> weatherData) {
        return weatherData.stream().map(dayMeasurement -> createWeatherDataBean(Pattern.compile("\\s+").split(dayMeasurement.trim())))
                .collect(Collectors.toList());
    }

    private static WeatherDataBean createWeatherDataBean(String[] data) {
        WeatherDataBean bean = new WeatherDataBean();
        bean.setDay(parseStringValue(data[0]));
        bean.setMaxTemperature(parseStringValue(data[1]));
        bean.setMinTemperature(parseStringValue(data[2]));

        return bean;
    }

    private static Integer parseStringValue(String value) {
        String number = removeCharacters(value);
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            LOGGER.severe("Value: '" + value + "' can not be parsed to Integer! " + e.getMessage());

            return null;
        }
    }

    private static String removeCharacters(String measurement) {
        return measurement.replaceAll("\\D", "");
    }

}
