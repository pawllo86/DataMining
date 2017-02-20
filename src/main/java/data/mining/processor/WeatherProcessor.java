package data.mining.processor;

import data.mining.bean.WeatherDataBean;

import java.util.Comparator;
import java.util.List;
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
    protected List<String> filterLines(List<String> lines) {
        List<String> filteredLines = super.filterLines(lines);
        filteredLines.remove(0);
        filteredLines.remove(filteredLines.size() - 1);

        return filteredLines;
    }

    @Override
    protected String getFileName() {
        return DATA_FILE_NAME;
    }

    @Override
    protected WeatherDataBean createDataBean(String[] lineWords) {
        WeatherDataBean bean = new WeatherDataBean();
        bean.setDay(parseStringValue(lineWords[0]));
        bean.setMaxTemperature(parseStringValue(lineWords[1]));
        bean.setMinTemperature(parseStringValue(lineWords[2]));

        return bean;
    }

}
