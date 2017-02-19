package data.mining.processor;

import data.mining.bean.FootballDataBean;
import data.mining.bean.WeatherDataBean;
import data.mining.util.FileUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FootballProcessor extends  Processor<FootballDataBean> {

    private static final String DATA_FILE_NAME = "football.dat";

    public void showSmallestGoalsDifference() {
        List<FootballDataBean> sortedFootbalDataBeans = getData().stream()
                .sorted(Comparator.comparing(FootballDataBean::getGoalsDifference).reversed()).collect(Collectors.toList());

        FootballDataBean bean = sortedFootbalDataBeans.get(0);
        if (bean != null) {
            System.out.println("The Goals Difference:");
            System.out.println("Club: " + bean.getClub() + " difference: " + bean.getGoalsDifference());
        } else {
            System.out.println("No data!");
        }
    }

    @Override
    protected List<FootballDataBean> createData() {
        try {
            List<String> data = FileUtils.getResourceContent(DATA_FILE_NAME);
            data = data.stream().filter(line -> !line.isEmpty()).collect(Collectors.toList());
            data.remove(0);
            data.remove(data.size() - 4);

            return buildFootballDataBean(data);
        } catch (IOException e) {
            logger.severe(e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<FootballDataBean> buildFootballDataBean(Collection<String> footballData) {
        return footballData.stream().map(dayMeasurement -> createFootballDataBean(Pattern.compile("\\s+")
                .split(dayMeasurement.trim()))).collect(Collectors.toList());
    }

    private FootballDataBean createFootballDataBean(String[] data) {
        FootballDataBean bean = new FootballDataBean();
        bean.setClub(data[1]);
        bean.setGoalsScored(parseStringValue(data[6]));
        bean.setGoalsLost(parseStringValue(data[8]));

        return bean;
    }

}
